package com.mtvs.crimecapturetv.domain.crimevideo.command.aggregate.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
public class CreateCrimeVideoResponse {

    private String crimeType;
    private String suspicionVideoPath01;
    private String suspicionVideoPath02;
    private String highlightVideoPath;

}
