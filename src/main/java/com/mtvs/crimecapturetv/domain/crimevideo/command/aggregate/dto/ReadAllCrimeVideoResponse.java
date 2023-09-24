package com.mtvs.crimecapturetv.domain.crimevideo.command.aggregate.dto;

import com.mtvs.crimecapturetv.domain.crimevideo.command.aggregate.entity.CrimeVideo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Builder
@AllArgsConstructor
@Getter
public class ReadAllCrimeVideoResponse {

    private Long no;
    private String suspicionVideoPath01;
    private String suspicionVideoPath02;
    private String highlightVideoPath;
    private String recordedAt;
    private String crimeType;
    private Long criminalStatus;

    public static ReadAllCrimeVideoResponse of(CrimeVideo crimeVideo) {

        String formDateTime = getDateForm(crimeVideo.getRecordedAt());
        String suspicionVideoName01 = getFileName(crimeVideo.getSuspicionVideoPath01());
        String suspicionVideoName02 = getFileName(crimeVideo.getSuspicionVideoPath02());
        String highlightVideoName = getFileName(crimeVideo.getHighlightVideoPath());

        return ReadAllCrimeVideoResponse.builder()
                .no(crimeVideo.getNo())
                .suspicionVideoPath01(suspicionVideoName01)
                .suspicionVideoPath02(suspicionVideoName02)
                .highlightVideoPath(highlightVideoName)
                .recordedAt(formDateTime)
                .crimeType(crimeVideo.getCrimeType())
                .criminalStatus(crimeVideo.getCriminalStatus())
                .build();
    }

    public static String getFileName(String path) {

        String[] pathSegment01 = path.split("\\\\");

        return pathSegment01[pathSegment01.length - 1];
    }

    public static String getDateForm(LocalDateTime dateTime) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        return dateTime.format(formatter);
    }
}
