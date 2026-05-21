package com.turatbekuly.amir.hospitalmanagementsystem.repository;

import com.turatbekuly.amir.hospitalmanagementsystem.entity.TuratbekulyAmirUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TuratbekulyAmirUserRepository extends JpaRepository<TuratbekulyAmirUser, Long> {

    Optional<TuratbekulyAmirUser> findByEmail(String email);

    boolean existsByEmail(String email);
}
