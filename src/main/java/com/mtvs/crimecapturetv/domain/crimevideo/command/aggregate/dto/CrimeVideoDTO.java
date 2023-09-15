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

    private LocalDateTime recordedAt;
    private String suspicionVideoPath;
    private String highlightVideoPath;
    private String crimeType;
    private Store store;

}
