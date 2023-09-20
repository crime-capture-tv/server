package com.mtvs.crimecapturetv.user.command.repository;

import com.mtvs.crimecapturetv.user.command.aggregate.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommandUserRepository extends JpaRepository<User, Long> {

    Optional<User> findById(String id);
    Optional<User> findByEmail(String email);
    boolean existsById(String id);
    boolean existsByEmail(String email);
    boolean existsByIdAndEmail(String id, String email);
}
