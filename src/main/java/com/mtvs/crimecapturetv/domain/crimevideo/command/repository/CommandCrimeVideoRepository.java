package com.mtvs.crimecapturetv.domain.crimevideo.command.repository;

import com.mtvs.crimecapturetv.domain.crimevideo.command.aggregate.entity.CrimeVideo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommandCrimeVideoRepository extends JpaRepository<CrimeVideo, Long> {

}
