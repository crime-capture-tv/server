package com.mtvs.crimecapturetv.domain.crimevideo.command.aggregate.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class UpdateCriminalStatusRequest {

    private Long criminalStatus;
}
