package com.mtvs.crimecapturetv.user.command.repository;

import com.mtvs.crimecapturetv.user.command.aggregate.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsById(String id);
}
