package com.mtvs.crimecapturetv.domain.crimevideo.command.aggregate.dto;

import com.mtvs.crimecapturetv.domain.crimevideo.command.aggregate.entity.CrimeVideo;
import com.mtvs.crimecapturetv.store.command.aggregate.entity.Store;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class CrimeVideoDTO {

    private Long videoNo;
    private String suspicionVideoPath01;
    private String suspicionVideoPath02;
    private String highlightVideoPath;
    private String fileName;
    private Path videoPath;
    private LocalDateTime recordedAt;
    private String crimeType;
    private Long criminalStatus;
    private Store store;

    public static CrimeVideoDTO toDto(CrimeVideo crimeVideo) {

        // 경로를 백슬래시(\\) 또는 슬래시(/)로 분할
        String[] parts = crimeVideo.getHighlightVideoPath().split("[\\\\/]");

        // 분할된 문자열 중 마지막 요소가 파일 이름
        String fileName = parts[parts.length - 1];

        Path videoPath = FileSystems.getDefault().getPath(crimeVideo.getHighlightVideoPath());

        return CrimeVideoDTO.builder()
                .videoNo(crimeVideo.getNo())
                .suspicionVideoPath01(crimeVideo.getSuspicionVideoPath01())
                .suspicionVideoPath02(crimeVideo.getSuspicionVideoPath02())
                .highlightVideoPath(crimeVideo.getHighlightVideoPath())
                .fileName(fileName)
                .videoPath(videoPath)
                .recordedAt(crimeVideo.getRecordedAt())
                .crimeType(crimeVideo.getCrimeType())
                .criminalStatus(crimeVideo.getCriminalStatus())
                .build();
    }
}
