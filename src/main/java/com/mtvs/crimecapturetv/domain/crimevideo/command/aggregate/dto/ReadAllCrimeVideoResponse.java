package com.mtvs.crimecapturetv.domain.crimevideo.command.aggregate.dto;

import com.mtvs.crimecapturetv.domain.crimevideo.command.aggregate.entity.CrimeVideo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@Getter
public class ReadAllCrimeVideoResponse {

    private Long no;
    private String fileName;
    private String suspicionVideoPath;
    private LocalDateTime recordedAt;
    private String crimeType;
    private Long criminalStatus;

    public static ReadAllCrimeVideoResponse of(CrimeVideo crimeVideo) {

        String[] pathSegment = crimeVideo.getSuspicionVideoPath().split("\\\\");
        String fileName = pathSegment[pathSegment.length - 1];

        return ReadAllCrimeVideoResponse.builder()
                .no(crimeVideo.getNo())
                .fileName(fileName)
                .suspicionVideoPath(crimeVideo.getSuspicionVideoPath())
                .recordedAt(crimeVideo.getRecordedAt())
                .crimeType(crimeVideo.getCrimeType())
                .criminalStatus(crimeVideo.getCriminalStatus())
                .build();
    }
}
