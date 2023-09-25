package com.mtvs.crimecapturetv.domain.crimevideo.command.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mtvs.crimecapturetv.domain.crimevideo.command.aggregate.dto.*;
import com.mtvs.crimecapturetv.domain.crimevideo.command.aggregate.entity.CrimeVideo;
import com.mtvs.crimecapturetv.domain.crimevideo.command.repository.CommandCrimeVideoRepository;
import com.mtvs.crimecapturetv.exception.AppException;
import com.mtvs.crimecapturetv.exception.ErrorCode;
import com.mtvs.crimecapturetv.store.command.aggregate.entity.Store;
import com.mtvs.crimecapturetv.store.command.repository.CommandStoreRepository;
import com.mtvs.crimecapturetv.user.command.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import javax.mail.MessagingException;
import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class CommandCrimeVideoService {

    private final CommandCrimeVideoRepository crimeVideoRepository;
    private final CommandStoreRepository storeRepository;
    private final EmailService emailService;


    @Transactional
    public CreateCrimeVideoResponse createCrimeVideo(Long storeNo, CreateCrimeVideoRequest request) throws MessagingException, JsonProcessingException {

        // 매장 No으로 매장 조회 없으면 STORE_NOT_FOUNDED 에러 발생
        Store store = validateStoreByNo(storeNo);
        log.info("🤖 찾은 storeNo = {}", store.getStoreNo());

        String suspicionVideoPath01 = request.getSuspicionVideoPath01();
        String suspicionVideoPath02 = request.getSuspicionVideoPath02();

        log.info("🤖 suspicionVideoPath01 : {} \n 🤖 suspicionVideoPath02 : {}", suspicionVideoPath01, suspicionVideoPath02);

        validateFileByPath(suspicionVideoPath01);
        log.info("🤖 01번 CCTV 영상파일이 존재합니다.");
        validateFileByPath(suspicionVideoPath02);
        log.info("🤖 02번 CCTV 영상파일이 존재합니다.");

        //ai 서버로 분석요청
        WebClient webClient = WebClient.create();

        //요청 url
        String url = "http://192.168.0.62:8000/classification";

        //Jackson Objectmapper 생성
        ObjectMapper objectMapper = new ObjectMapper();

        // 데이터를 Map으로 구성
        Map<String, Object> data = new HashMap<>();
        data.put("suspicionVideoPath01", suspicionVideoPath01);
        data.put("suspicionVideoPath02", suspicionVideoPath02);
        data.put("stayStartTime", request.getStayStartTime());
        data.put("stayEndTime", request.getStayEndTime());

        //Map을 JSON 문자열로 변환
        String jsonRequest = objectMapper.writeValueAsString(data);
        log.info("🤖 jsonRequest : {}", jsonRequest);

        // POST 요청 보내기 (동기 호출)
        AiCrimeAnalyzeResponse result = webClient
                .post()
                .uri(url)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(jsonRequest))
                .retrieve()
                .bodyToMono(AiCrimeAnalyzeResponse.class)
                .block(); // 동기 호출

        log.info("🤖 result : {}", result.getCrimeType());


        // 보내진 사람있는 영상이 의심이 아니면 저장된 파일 삭제
        if (result.getCrimeType().equals("normal")) {

            // 영상 삭제
            File suspicionVideo01 = new File(suspicionVideoPath01);
            File suspicionVideo02 = new File(suspicionVideoPath02);

            suspicionVideo01.delete();
            suspicionVideo02.delete();

            return CreateCrimeVideoResponse.builder()
                    .crimeType("범죄가 아닙니다.")
                    .suspicionVideoPath01("suspicionVideo01 삭제됨")
                    .suspicionVideoPath02("suspicionVideo02 삭제됨")
                    .highlightVideoPath("highlightVideo 없음")
                    .build();
        } else {

            String[] pathSegmentForFileName = request.getSuspicionVideoPath01().split("\\\\");
            String fileName = pathSegmentForFileName[pathSegmentForFileName.length - 1];
            log.info("🤖 파일명 : {}", fileName);

            String[] fileNameSegmentForRecordedAt = fileName.split("_");
            String recordedAtPy = fileNameSegmentForRecordedAt[1];
            log.info("🤖 recordedAtPy : {}", recordedAtPy);

            //Formatter 설정
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss");
            // DateTimeFormatter를 사용하여 문자열을 LocalDateTime으로 변환
            LocalDateTime recordedAt = LocalDateTime.parse(recordedAtPy, formatter);
            log.info("localdatetime recordedAt : {}", recordedAt);

            String highlightVideoPath = result.getHighlightVideoPath();
            log.info("🤖 highlightVideoPath : {}", highlightVideoPath);

            // DB에 저장
            CrimeVideoDTO crimeVideoDTO = CrimeVideoDTO.builder()
                    .suspicionVideoPath01(suspicionVideoPath01)
                    .suspicionVideoPath02(suspicionVideoPath02)
                    .highlightVideoPath(highlightVideoPath)
                    .crimeType(result.getCrimeType())
                    .recordedAt(recordedAt)
                    .criminalStatus(0L)
                    .store(store)
                    .build();

            CrimeVideo crimeVideo = crimeVideoRepository.save(CrimeVideo.toCrimeVideo(crimeVideoDTO));
            log.info("🤖 crimeVideo : {}", crimeVideo);

            //이메일 발송
            String userEmail = store.getUser().getEmail();
            emailService.sendEmailWithAttachment(userEmail, highlightVideoPath);
            log.info("🤖 하이라이트 영상이 발송되었습니다.");

            return CreateCrimeVideoResponse.builder()
                    .crimeType(crimeVideo.getCrimeType())
                    .suspicionVideoPath01(crimeVideo.getSuspicionVideoPath01())
                    .suspicionVideoPath02(crimeVideo.getSuspicionVideoPath02())
                    .highlightVideoPath(crimeVideo.getHighlightVideoPath())
                    .build();
        }
    }

    @Transactional
    public UpdateCriminalStatusResponse updateCriminalStatus(Long videoNo, UpdateCriminalStatusRequest request) {

        // 해당 영상이 존재하는지 확인
        CrimeVideo crimeVideo = validateCrimeVideoByNo(videoNo);

        log.info("🤖 request crimeStatus = {}", request.getCriminalStatus());
        crimeVideo.updateCriminalStatus(request.getCriminalStatus());
        log.info("🤖 crimeStatus = {}", crimeVideo.getCriminalStatus());

        UpdateCriminalStatusResponse response = UpdateCriminalStatusResponse.of(crimeVideo);

        return response;
    }

    @Transactional
    public DeleteCrimeVideoResponse deleteCrimeVideo(Long videoNo) {
        // 해당 영상이 있는지 확인
        CrimeVideo crimeVideo = validateCrimeVideoByNo(videoNo);
        String suspicionVideoPath01 = crimeVideo.getSuspicionVideoPath01();
        String suspicionVideoPath02 = crimeVideo.getSuspicionVideoPath02();
        String highlightVideoPath = crimeVideo.getHighlightVideoPath();

        validateFileByPath(highlightVideoPath);
        log.info("🤖 해당 경로에 파일이 존재합니다. 경로 : {}", highlightVideoPath);
        validateFileByPath(suspicionVideoPath01);
        log.info("🤖 해당 경로에 파일이 존재합니다. 경로 : {}", suspicionVideoPath01);
        validateFileByPath(suspicionVideoPath02);
        log.info("🤖 해당 경로에 파일이 존재합니다. 경로 : {}", suspicionVideoPath02);

        crimeVideoRepository.delete(crimeVideo);


        File highlightVideo = new File(highlightVideoPath);
        File suspicionVideo01 = new File(suspicionVideoPath01);
        File suspicionVideo02 = new File(suspicionVideoPath02);

        highlightVideo.delete();
        suspicionVideo01.delete();
        suspicionVideo02.delete();

        return new DeleteCrimeVideoResponse(crimeVideo.getNo());
    }

    public Page<ReadAllCrimeVideoResponse> readCrimeVideoLists(Long criminalStatus, Long storeNo, Pageable pageable) {

        Store store = storeRepository.findById(storeNo)
                .orElseThrow(() -> new AppException(ErrorCode.STORE_NOT_FOUNDED));

        if (criminalStatus == 4) {
            return crimeVideoRepository.findAllByStore(store, pageable).map(ReadAllCrimeVideoResponse::of);
        } else {
            return crimeVideoRepository.findAllByStoreAndCriminalStatus(store, criminalStatus, pageable).map(ReadAllCrimeVideoResponse::of);
        }
    }

    public CrimeVideo getCrimeVideo(Long crimeVideoNo) {
        return crimeVideoRepository.findById(crimeVideoNo)
                .orElseThrow(() -> new AppException(ErrorCode.CRIME_VIDEO_NOT_FOUNDED));
    }


    // 파일 경로에서 파일명만 추출하는 로직
    public String getFileName(String filePath) {

        String[] pathSegment = filePath.split("\\\\");

        return pathSegment[pathSegment.length - 1];
    }

    private CrimeVideo validateCrimeVideoByNo(Long crimeVideoNo) {
        return crimeVideoRepository.findById(crimeVideoNo)
                .orElseThrow(() -> new AppException(ErrorCode.CRIME_VIDEO_NOT_FOUNDED));
    }

    private Store validateStoreByNo(Long storeNo) {
        return storeRepository.findById(storeNo)
                .orElseThrow(() -> new AppException(ErrorCode.STORE_NOT_FOUNDED));
    }

    private void validateFileByPath(String url) {
        File file = new File(url);
        if (!file.exists()) {
            throw new AppException(ErrorCode.FILE_NOT_FOUNDED);
        }
    }


}
