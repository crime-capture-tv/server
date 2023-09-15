package com.mtvs.crimecapturetv.domain.crimevideo.command.aggregate.dto;

import com.mtvs.crimecapturetv.domain.crimevideo.command.aggregate.entity.CrimeVideo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class UpdateCriminalStatusResponse {
    private Long crimeVideoNo;
    private String suspicionVideoPath;
    private String highlightVideoPath;
    private Long criminalStatus;

    public static UpdateCriminalStatusResponse of(CrimeVideo crimeVideo) {
        return UpdateCriminalStatusResponse.builder()
                .crimeVideoNo(crimeVideo.getNo())
                .suspicionVideoPath(crimeVideo.getSuspicionVideoPath())
                .highlightVideoPath(crimeVideo.getHighlightVideoPath())
                .criminalStatus(crimeVideo.getCriminalStatus())
                .build();
    }
}
