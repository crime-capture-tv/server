package com.mtvs.crimecapturetv.domain.crimevideo.command.aggregate.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Builder
public class DeleteCrimeVideoResponse {

    private Long videoNo;

    public DeleteCrimeVideoResponse(Long videoNo) {
        this.videoNo = videoNo;
    }
}
