package com.mtvs.crimecapturetv.domain.crimevideo.command.aggregate.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class CreateCrimeVideoRequest {

    private String suspicionVideoPath01;
    private String suspicionVideoPath02;
    private String stayStartTime;
    private String stayEndTime;

}
