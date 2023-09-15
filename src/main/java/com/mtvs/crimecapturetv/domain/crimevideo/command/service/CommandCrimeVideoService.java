package com.mtvs.crimecapturetv.domain.crimevideo.command.service;

import com.mtvs.crimecapturetv.domain.crimevideo.command.aggregate.dto.*;
import com.mtvs.crimecapturetv.domain.crimevideo.command.aggregate.entity.CrimeVideo;
import com.mtvs.crimecapturetv.domain.crimevideo.command.repository.CommandCrimeVideoRepository;
import com.mtvs.crimecapturetv.exception.AppException;
import com.mtvs.crimecapturetv.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class CommandCrimeVideoService {

    private final CommandCrimeVideoRepository crimeVideoRepository;


    @Transactional
    public CreateCrimeVideoResponse createCrimeVideo(CreateCrimeVideoRequest request) {

        String suspicionVideoPath = request.getSuspicionVideoPath();
        LocalDateTime recordedAt = request.getRecordedAt();

        URI uri = UriComponentsBuilder
                .fromUriString("http://192.168.0.9:8000/")
                .path("")
                .queryParam("suspicionVideoPath", suspicionVideoPath)
                .build()
                .toUri();

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<AiCrimeAnalyzeResponse> result = restTemplate.getForEntity(uri, AiCrimeAnalyzeResponse.class);
        log.info("response \nSuspicionVideoPath : {} \nHighLightVideoPath : {} \nCrimeType : {}", result.getBody().getSuspicionVideoPath(), result.getBody().getHighlightVideoPath(), result.getBody().getCrimeType());

        CrimeVideoDTO crimeVideoDTO = CrimeVideoDTO.builder()
                .suspicionVideoPath(result.getBody().getSuspicionVideoPath())
                .highlightVideoPath(result.getBody().getHighlightVideoPath())
                .crimeType(result.getBody().getCrimeType())
                .recordedAt(recordedAt)
                .build();

        CrimeVideo crimeVideo = crimeVideoRepository.save(CrimeVideo.toCrimeVideo(crimeVideoDTO));

        return CreateCrimeVideoResponse.builder()
                .crimeType(crimeVideo.getCrimeType())
                .suspicionVideoPath(crimeVideo.getSuspicionVideoPath())
                .highlightVideoPath(crimeVideo.getHighlightVideoPath())
                .build();
    }

    @Transactional
    public UpdateCriminalStatusResponse updateCriminalStatus(Long videoNo, UpdateCriminalStatusRequest request) {

        // 해당 영상이 존재하는지 확인
        CrimeVideo crimeVideo = crimeVideoRepository.findById(videoNo)
                .orElseThrow(() -> new AppException(ErrorCode.CRIME_VIDEO_NOT_FOUNDED));

        log.info("🤖 request crimeStatus = {}", request.getCriminalStatus());
        crimeVideo.updateCriminalStatus(request.getCriminalStatus());
        log.info("🤖 crimeStatus = {}", crimeVideo.getCriminalStatus());


        UpdateCriminalStatusResponse response = UpdateCriminalStatusResponse.of(crimeVideo);

        return response;
    }
}
