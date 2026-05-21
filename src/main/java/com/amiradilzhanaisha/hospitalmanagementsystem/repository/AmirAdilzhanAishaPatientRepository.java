package com.amiradilzhanaisha.hospitalmanagementsystem.repository;

import com.amiradilzhanaisha.hospitalmanagementsystem.entity.AmirAdilzhanAishaPatient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface AmirAdilzhanAishaPatientRepository extends JpaRepository<AmirAdilzhanAishaPatient, Long>, JpaSpecificationExecutor<AmirAdilzhanAishaPatient> {
}
