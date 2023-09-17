package com.mtvs.crimecapturetv.store.command.repository;

import com.mtvs.crimecapturetv.store.command.aggregate.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommandStoreRepository extends JpaRepository<Store, Long> {

    Optional<Store> findByStoreNo(Long storeNo);

    void deleteByStoreNo(Long storeNo);
}
