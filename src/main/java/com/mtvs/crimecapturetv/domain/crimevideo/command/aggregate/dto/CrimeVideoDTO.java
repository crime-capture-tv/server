package com.mtvs.crimecapturetv.domain.crimevideo.command.aggregate.dto;

import com.mtvs.crimecapturetv.store.command.aggregate.entity.Store;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class CrimeVideoDTO {

    private String suspicionVideoPath01;
    private String suspicionVideoPath02;
    private String highlightVideoPath;
    private LocalDateTime recordedAt;
    private String crimeType;
    private Long criminalStatus;
    private Store store;
}
