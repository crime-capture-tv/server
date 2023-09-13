package com.mtvs.crimecapturetv.user.command.repository;

import com.mtvs.crimecapturetv.user.command.aggregate.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommandUserRepository extends JpaRepository<User, Long> {

    boolean existsById(String id);
    Optional<User> findById(String id);
}
