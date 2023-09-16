package com.mtvs.crimecapturetv.domain.crimevideo.command.service;

import com.mtvs.crimecapturetv.domain.crimevideo.command.aggregate.dto.*;
import com.mtvs.crimecapturetv.domain.crimevideo.command.aggregate.entity.CrimeVideo;
import com.mtvs.crimecapturetv.domain.crimevideo.command.repository.CommandCrimeVideoRepository;
import com.mtvs.crimecapturetv.exception.AppException;
import com.mtvs.crimecapturetv.exception.ErrorCode;
import com.mtvs.crimecapturetv.store.command.aggregate.entity.Store;
import com.mtvs.crimecapturetv.store.command.repository.CommandStoreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.File;
import java.net.URI;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class CommandCrimeVideoService {

    private final CommandCrimeVideoRepository crimeVideoRepository;
    private final CommandStoreRepository storeRepository;


    @Transactional
    public CreateCrimeVideoResponse createCrimeVideo(Long storeNo, CreateCrimeVideoRequest request) {

        // 매장 No으로 매장 조회 없으면 STORE_NOT_FOUNDED 에러 발생
        Store store = validateStoreByNo(storeNo);
        log.info("🤖 찾은 storeNo = {}", store.getStoreNo());

        String suspicionVideoPath = request.getSuspicionVideoPath();
        LocalDateTime recordedAt = request.getRecordedAt();
        log.info("🤖 suspicionVideoPath : {}", suspicionVideoPath);

        validateFileByPath(suspicionVideoPath);

        URI uri = UriComponentsBuilder
                .fromUriString("http://192.168.0.14:8000/")
                .path("")
                .queryParam("suspicionVideoPath", suspicionVideoPath)
                .build()
                .toUri();

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<AiCrimeAnalyzeResponse> result = restTemplate.getForEntity(uri, AiCrimeAnalyzeResponse.class);

        CrimeVideoDTO crimeVideoDTO = CrimeVideoDTO.builder()
                .suspicionVideoPath(result.getBody().getSuspicionVideoPath())
                .highlightVideoPath(result.getBody().getHighlightVideoPath())
                .crimeType(result.getBody().getCrimeType())
                .recordedAt(recordedAt)
                .store(store)
                .build();

        CrimeVideo crimeVideo = crimeVideoRepository.save(CrimeVideo.toCrimeVideo(crimeVideoDTO));
        log.info("🤖 crimeVideo storeNo : {}", crimeVideo.getStore().getStoreNo());

        return CreateCrimeVideoResponse.builder()
                .crimeType(crimeVideo.getCrimeType())
                .suspicionVideoPath(crimeVideo.getSuspicionVideoPath())
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
        String suspicionVideoPath = crimeVideo.getSuspicionVideoPath();
        String highlightVideoPath = crimeVideo.getHighlightVideoPath();

        validateFileByPath(highlightVideoPath);
        validateFileByPath(suspicionVideoPath);
        log.info("🤖 해당 경로에 파일이 존재합니다. 경로 : {}", suspicionVideoPath);
        log.info("🤖 해당 경로에 파일이 존재합니다. 경로 : {}", highlightVideoPath);

        crimeVideoRepository.delete(crimeVideo);

        File file = new File(suspicionVideoPath);

        if(file.delete()){
            log.info("🤖 파일 삭제 성공");
        } else {
            throw new AppException(ErrorCode.FILE_DELETE_FAILED);
        }

        return new DeleteCrimeVideoResponse(crimeVideo.getNo());
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
