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
    private String suspicionVideoPath01;
    private String suspicionVideoPath02;
    private String highlightVideoPath;
    private LocalDateTime recordedAt;
    private String crimeType;
    private Long criminalStatus;

    public static ReadAllCrimeVideoResponse of(CrimeVideo crimeVideo) {

        String[] pathSegment01 = crimeVideo.getSuspicionVideoPath01().split("\\\\");
        String fileName = pathSegment01[pathSegment01.length - 1];

        return ReadAllCrimeVideoResponse.builder()
                .no(crimeVideo.getNo())
                .suspicionVideoPath01(crimeVideo.getSuspicionVideoPath01())
                .suspicionVideoPath02(crimeVideo.getSuspicionVideoPath02())
                .highlightVideoPath(crimeVideo.getHighlightVideoPath())
                .recordedAt(crimeVideo.getRecordedAt())
                .crimeType(crimeVideo.getCrimeType())
                .criminalStatus(crimeVideo.getCriminalStatus())
                .build();
    }
}
