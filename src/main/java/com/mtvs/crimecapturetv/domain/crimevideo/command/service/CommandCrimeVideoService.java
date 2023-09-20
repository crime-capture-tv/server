package com.mtvs.crimecapturetv.domain.crimevideo.command.service;

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
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.mail.MessagingException;
import java.io.File;
import java.net.URI;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class CommandCrimeVideoService {

    private final CommandCrimeVideoRepository crimeVideoRepository;
    private final CommandStoreRepository storeRepository;
    private final EmailService emailService;


    @Transactional
    public CreateCrimeVideoResponse createCrimeVideo(Long storeNo, CreateCrimeVideoRequest request) throws MessagingException {

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
        URI uri = UriComponentsBuilder
                //.fromUriString("http://192.168.0.14:8000/")
                .fromUriString("http://192.168.0.62:8000/classification")
                .path("")
                .queryParam("suspicionVideoPath01", suspicionVideoPath01)
                .queryParam("suspicionVideoPath02", suspicionVideoPath02)
                .queryParam("stayStartTime", request.getStayStartTime())
                .queryParam("stayEndTime", request.getStayEndTime())
                .build()
                .toUri();

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<AiCrimeAnalyzeResponse> result = restTemplate.getForEntity(uri, AiCrimeAnalyzeResponse.class);


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

        CrimeVideoDTO crimeVideoDTO = CrimeVideoDTO.builder()
                .suspicionVideoPath01(suspicionVideoPath01)
                .suspicionVideoPath02(suspicionVideoPath02)
                .highlightVideoPath(result.getBody().getHighlightVideoPath())
                .crimeType(result.getBody().getCrimeType())
                .recordedAt(recordedAt)
                .criminalStatus(0L)
                .store(store)
                .build();


        CrimeVideo crimeVideo = crimeVideoRepository.save(CrimeVideo.toCrimeVideo(crimeVideoDTO));
        log.info("🤖 crimeVideo storeNo : {}", crimeVideo.getStore().getStoreNo());

        String userEmail = store.getUser().getEmail();
        String highlightVideoPath = crimeVideo.getHighlightVideoPath();

        emailService.sendEmailWithAttachment(userEmail, highlightVideoPath);
        log.info("🤖 하이라이트 영상이 발송되었습니다.");

        return CreateCrimeVideoResponse.builder()
                .crimeType(crimeVideo.getCrimeType())
                .suspicionVideoPath01(crimeVideo.getSuspicionVideoPath01())
                .suspicionVideoPath02(crimeVideo.getSuspicionVideoPath02())
                .highlightVideoPath(crimeVideo.getHighlightVideoPath())
                .build();
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
