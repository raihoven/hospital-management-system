package com.turatbekuly.amir.hospitalmanagementsystem.service.impl;

import com.turatbekuly.amir.hospitalmanagementsystem.dto.TuratbekulyAmirAsyncDashboardDto;
import com.turatbekuly.amir.hospitalmanagementsystem.dto.TuratbekulyAmirFileAuditDto;
import com.turatbekuly.amir.hospitalmanagementsystem.dto.TuratbekulyAmirPatientAnalyticsDto;
import com.turatbekuly.amir.hospitalmanagementsystem.dto.TuratbekulyAmirSystemSummaryDto;
import com.turatbekuly.amir.hospitalmanagementsystem.entity.TuratbekulyAmirPatient;
import com.turatbekuly.amir.hospitalmanagementsystem.entity.TuratbekulyAmirFileResource;
import com.turatbekuly.amir.hospitalmanagementsystem.entity.TuratbekulyAmirRole;
import com.turatbekuly.amir.hospitalmanagementsystem.entity.TuratbekulyAmirUser;
import com.turatbekuly.amir.hospitalmanagementsystem.repository.TuratbekulyAmirPatientRepository;
import com.turatbekuly.amir.hospitalmanagementsystem.repository.TuratbekulyAmirFileResourceRepository;
import com.turatbekuly.amir.hospitalmanagementsystem.repository.TuratbekulyAmirUserRepository;
import com.turatbekuly.amir.hospitalmanagementsystem.service.TuratbekulyAmirAsyncService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class TuratbekulyAmirAsyncServiceImpl implements TuratbekulyAmirAsyncService {

    private static final Logger log = LoggerFactory.getLogger(TuratbekulyAmirAsyncServiceImpl.class);
    private final TuratbekulyAmirPatientRepository patientRepository;
    private final TuratbekulyAmirUserRepository userRepository;
    private final TuratbekulyAmirFileResourceRepository fileResourceRepository;
    private final TuratbekulyAmirAsyncService asyncServiceProxy;
    private final String storagePath;

    public TuratbekulyAmirAsyncServiceImpl(
            TuratbekulyAmirPatientRepository patientRepository,
            TuratbekulyAmirUserRepository userRepository,
            TuratbekulyAmirFileResourceRepository fileResourceRepository,
            @Lazy TuratbekulyAmirAsyncService asyncServiceProxy,
            @Value("${app.file.storage.path}") String storagePath
    ) {
        this.patientRepository = patientRepository;
        this.userRepository = userRepository;
        this.fileResourceRepository = fileResourceRepository;
        this.asyncServiceProxy = asyncServiceProxy;
        this.storagePath = storagePath;
    }

    @Override
    @Async("turatbekulyAmirTaskExecutor")
    public CompletableFuture<TuratbekulyAmirPatientAnalyticsDto> generatePatientAnalytics() {
        log.info("Started async TuratbekulyAmirPatient analytics generation");
        List<TuratbekulyAmirPatient> patients = patientRepository.findAll();

        double averageAge = patients.stream()
                .mapToInt(TuratbekulyAmirPatient::getAge)
                .average()
                .orElse(0.0);

        long adultPatients = patients.stream()
                .filter(TuratbekulyAmirPatient -> TuratbekulyAmirPatient.getAge() >= 18)
                .count();

        long minorPatients = patients.stream()
                .filter(TuratbekulyAmirPatient -> TuratbekulyAmirPatient.getAge() < 18)
                .count();

        long patientsWithIllness = patients.stream()
                .filter(TuratbekulyAmirPatient -> StringUtils.hasText(TuratbekulyAmirPatient.getIllness()))
                .count();

        TuratbekulyAmirPatientAnalyticsDto analyticsDto = new TuratbekulyAmirPatientAnalyticsDto(
                patients.size(),
                averageAge,
                adultPatients,
                minorPatients,
                patientsWithIllness
        );

        log.info("Completed async TuratbekulyAmirPatient analytics generation for {} patients", patients.size());
        return CompletableFuture.completedFuture(analyticsDto);
    }

    @Override
    @Async("turatbekulyAmirTaskExecutor")
    public CompletableFuture<TuratbekulyAmirFileAuditDto> runFileStorageAudit() {
        log.info("Started async file storage audit");
        List<TuratbekulyAmirFileResource> files = fileResourceRepository.findAll();

        long availableFiles = files.stream()
                .filter(file -> Files.exists(Paths.get(file.getFilePath())))
                .count();

        long missingFiles = files.size() - availableFiles;

        long totalSizeBytes = files.stream()
                .map(TuratbekulyAmirFileResource::getSize)
                .filter(size -> size != null)
                .mapToLong(Long::longValue)
                .sum();

        TuratbekulyAmirFileAuditDto auditDto = new TuratbekulyAmirFileAuditDto(
                files.size(),
                availableFiles,
                missingFiles,
                totalSizeBytes,
                Path.of(storagePath).toAbsolutePath().normalize().toString()
        );

        log.info("Completed async file storage audit: totalFiles={} availableFiles={} missingFiles={}", files.size(), availableFiles, missingFiles);
        return CompletableFuture.completedFuture(auditDto);
    }

    @Override
    @Async("turatbekulyAmirTaskExecutor")
    public CompletableFuture<TuratbekulyAmirSystemSummaryDto> buildSystemSummary() {
        log.info("Started async system summary build");
        List<TuratbekulyAmirUser> users = userRepository.findAll();
        long totalAdmins = users.stream()
                .filter(user -> user.getRole() == TuratbekulyAmirRole.ROLE_ADMIN)
                .count();

        TuratbekulyAmirSystemSummaryDto summaryDto = new TuratbekulyAmirSystemSummaryDto(
                users.size(),
                totalAdmins,
                patientRepository.count(),
                fileResourceRepository.count(),
                LocalDateTime.now()
        );

        log.info("Completed async system summary build: users={} admins={} patients={} files={}", users.size(), totalAdmins, summaryDto.totalPatients(), summaryDto.totalFiles());
        return CompletableFuture.completedFuture(summaryDto);
    }

    @Override
    public CompletableFuture<TuratbekulyAmirAsyncDashboardDto> buildDashboardSummary() {
        log.info("Started async dashboard summary aggregation");
        CompletableFuture<TuratbekulyAmirPatientAnalyticsDto> patientAnalyticsFuture = asyncServiceProxy.generatePatientAnalytics();
        CompletableFuture<TuratbekulyAmirFileAuditDto> fileAuditFuture = asyncServiceProxy.runFileStorageAudit();
        CompletableFuture<TuratbekulyAmirSystemSummaryDto> systemSummaryFuture = asyncServiceProxy.buildSystemSummary();

        return CompletableFuture.allOf(patientAnalyticsFuture, fileAuditFuture, systemSummaryFuture)
                .thenApply(unused -> new TuratbekulyAmirAsyncDashboardDto(
                        patientAnalyticsFuture.join(),
                        fileAuditFuture.join(),
                        systemSummaryFuture.join(),
                        LocalDateTime.now()
                ))
                .whenComplete((result, throwable) -> {
                    if (throwable == null) {
                        log.info("Completed async dashboard summary aggregation");
                    } else {
                        log.error("Async dashboard summary aggregation failed", throwable);
                    }
                });
    }
}
