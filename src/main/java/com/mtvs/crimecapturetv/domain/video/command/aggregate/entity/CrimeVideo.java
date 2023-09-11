package com.mtvs.crimecapturetv.domain.video.command.aggregate.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "Crime_Video_TB")
public class CrimeVideo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "crime_video_no")
    private long no;

    @Column(name = "recorded_at")
    private LocalDateTime recordedAt;

    @Column(name = "suspicion_video_path")
    private String suspicionVideoPath;

    @Column(name = "highlight_video_path")
    private String highlightVideoPath;

    @Column(name = "crime_type")
    private String crimeType;

    @Column(name = "criminal_Status")
    private Long criminalStatus;

    public CrimeVideo(long no, LocalDateTime recordedAt, String suspicionVideoPath, String highlightVideoPath, String crimeType, Long criminalStatus) {
        this.no = no;
        this.recordedAt = recordedAt;
        this.suspicionVideoPath = suspicionVideoPath;
        this.highlightVideoPath = highlightVideoPath;
        this.crimeType = crimeType;
        this.criminalStatus = criminalStatus;
    }
}
