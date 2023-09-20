package com.mtvs.crimecapturetv.domain.crimevideo.command.aggregate.entity;

import com.mtvs.crimecapturetv.domain.crimevideo.command.aggregate.dto.CrimeVideoDTO;
import com.mtvs.crimecapturetv.global.common.entity.BaseEntity;
import com.mtvs.crimecapturetv.store.command.aggregate.entity.Store;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "Crime_Video_TB")
public class CrimeVideo extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "crime_video_no")
    private long no;

    @Column(name = "suspicion_video_path_01")
    private String suspicionVideoPath01;

    @Column(name = "suspicion_video_path_02")
    private String suspicionVideoPath02;

    @Column(name = "recorded_at")
    private LocalDateTime recordedAt;

    @Column(name = "crime_type")
    private String crimeType;

    @Column(name = "highlight_video_path")
    private String highlightVideoPath;

    @Column(name = "criminal_Status")
    @ColumnDefault("0")
    private Long criminalStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_no")
    private Store store;

    public CrimeVideo(long no, String suspicionVideoPath01, String suspicionVideoPath02, LocalDateTime recordedAt, String crimeType, String highlightVideoPath, Long criminalStatus, Store store) {
        this.no = no;
        this.suspicionVideoPath01 = suspicionVideoPath01;
        this.suspicionVideoPath02 = suspicionVideoPath02;
        this.recordedAt = recordedAt;
        this.crimeType = crimeType;
        this.highlightVideoPath = highlightVideoPath;
        this.criminalStatus = criminalStatus;
        this.store = store;
    }

    public static CrimeVideo toCrimeVideo(CrimeVideoDTO dto) {
        return CrimeVideo.builder()
                .suspicionVideoPath01(dto.getSuspicionVideoPath01())
                .suspicionVideoPath02(dto.getSuspicionVideoPath02())
                .highlightVideoPath(dto.getHighlightVideoPath())
                .crimeType(dto.getCrimeType())
                .store(dto.getStore())
                .build();
    }

    // 유저에 의해 crimeStatus 상태 변경
    public void updateCriminalStatus(Long criminalStatus) {
        this.criminalStatus = criminalStatus;
    }
}
