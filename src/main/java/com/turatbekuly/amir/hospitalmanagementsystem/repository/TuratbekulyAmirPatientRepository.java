package com.turatbekuly.amir.hospitalmanagementsystem.repository;

import com.turatbekuly.amir.hospitalmanagementsystem.entity.TuratbekulyAmirPatient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface TuratbekulyAmirPatientRepository extends JpaRepository<TuratbekulyAmirPatient, Long>, JpaSpecificationExecutor<TuratbekulyAmirPatient> {
}
