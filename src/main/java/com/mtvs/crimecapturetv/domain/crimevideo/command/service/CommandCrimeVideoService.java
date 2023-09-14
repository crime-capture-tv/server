package com.mtvs.crimecapturetv.domain.crimevideo.command.service;

import com.mtvs.crimecapturetv.domain.crimevideo.command.aggregate.dto.AiCrimeAnalyzeResponse;
import com.mtvs.crimecapturetv.domain.crimevideo.command.aggregate.dto.CreateCrimeVideoRequest;
import com.mtvs.crimecapturetv.domain.crimevideo.command.aggregate.dto.CreateCrimeVideoResponse;
import com.mtvs.crimecapturetv.domain.crimevideo.command.aggregate.dto.CrimeVideoDTO;
import com.mtvs.crimecapturetv.domain.crimevideo.command.aggregate.entity.CrimeVideo;
import com.mtvs.crimecapturetv.domain.crimevideo.command.repository.CommandCrimeVideoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommandCrimeVideoService {

    private final CommandCrimeVideoRepository crimeVideoRepository;

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

}
