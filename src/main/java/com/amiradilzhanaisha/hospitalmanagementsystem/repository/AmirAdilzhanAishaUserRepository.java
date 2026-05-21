package com.amiradilzhanaisha.hospitalmanagementsystem.repository;

import com.amiradilzhanaisha.hospitalmanagementsystem.entity.AmirAdilzhanAishaUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AmirAdilzhanAishaUserRepository extends JpaRepository<AmirAdilzhanAishaUser, Long> {

    Optional<AmirAdilzhanAishaUser> findByEmail(String email);

    boolean existsByEmail(String email);
}
