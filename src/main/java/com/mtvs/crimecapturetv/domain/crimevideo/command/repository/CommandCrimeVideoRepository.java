package com.mtvs.crimecapturetv.domain.crimevideo.command.repository;

import com.mtvs.crimecapturetv.domain.crimevideo.command.aggregate.entity.CrimeVideo;
import com.mtvs.crimecapturetv.store.command.aggregate.entity.Store;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommandCrimeVideoRepository extends JpaRepository<CrimeVideo, Long> {

    Page<CrimeVideo> findAllByStore(Store store, Pageable pageable);

    Page<CrimeVideo> findAllByStoreAndCriminalStatus(Store store, Long criminalStatus, Pageable pageable);

}
