package com.mtvs.crimecapturetv.domain.crimevideo.command.aggregate.dto;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AiCrimeAnalyzeResponse {

    private String highlightVideoPath;
    private String crimeType;

}
