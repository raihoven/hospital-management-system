package com.turatbekuly.amir.hospitalmanagementsystem.service.impl;

import com.turatbekuly.amir.hospitalmanagementsystem.dto.AmirAdilzhanAishaAsyncDashboardDto;
import com.turatbekuly.amir.hospitalmanagementsystem.dto.AmirAdilzhanAishaFileAuditDto;
import com.turatbekuly.amir.hospitalmanagementsystem.dto.AmirAdilzhanAishaPatientAnalyticsDto;
import com.turatbekuly.amir.hospitalmanagementsystem.dto.AmirAdilzhanAishaSystemSummaryDto;
import com.turatbekuly.amir.hospitalmanagementsystem.entity.AmirAdilzhanAishaPatient;
import com.turatbekuly.amir.hospitalmanagementsystem.entity.AmirAdilzhanAishaFileResource;
import com.turatbekuly.amir.hospitalmanagementsystem.entity.AmirAdilzhanAishaRole;
import com.turatbekuly.amir.hospitalmanagementsystem.entity.AmirAdilzhanAishaUser;
import com.turatbekuly.amir.hospitalmanagementsystem.repository.AmirAdilzhanAishaPatientRepository;
import com.turatbekuly.amir.hospitalmanagementsystem.repository.AmirAdilzhanAishaFileResourceRepository;
import com.turatbekuly.amir.hospitalmanagementsystem.repository.AmirAdilzhanAishaUserRepository;
import com.turatbekuly.amir.hospitalmanagementsystem.service.AmirAdilzhanAishaAsyncService;
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
public class AmirAdilzhanAishaAsyncServiceImpl implements AmirAdilzhanAishaAsyncService {

    private static final Logger log = LoggerFactory.getLogger(AmirAdilzhanAishaAsyncServiceImpl.class);
    private final AmirAdilzhanAishaPatientRepository patientRepository;
    private final AmirAdilzhanAishaUserRepository userRepository;
    private final AmirAdilzhanAishaFileResourceRepository fileResourceRepository;
    private final AmirAdilzhanAishaAsyncService asyncServiceProxy;
    private final String storagePath;

    public AmirAdilzhanAishaAsyncServiceImpl(
            AmirAdilzhanAishaPatientRepository patientRepository,
            AmirAdilzhanAishaUserRepository userRepository,
            AmirAdilzhanAishaFileResourceRepository fileResourceRepository,
            @Lazy AmirAdilzhanAishaAsyncService asyncServiceProxy,
            @Value("${app.file.storage.path}") String storagePath
    ) {
        this.patientRepository = patientRepository;
        this.userRepository = userRepository;
        this.fileResourceRepository = fileResourceRepository;
        this.asyncServiceProxy = asyncServiceProxy;
        this.storagePath = storagePath;
    }

    @Override
    @Async("AmirAdilzhanAishaTaskExecutor")
    public CompletableFuture<AmirAdilzhanAishaPatientAnalyticsDto> generatePatientAnalytics() {
        log.info("Started async AmirAdilzhanAishaPatient analytics generation");
        List<AmirAdilzhanAishaPatient> patients = patientRepository.findAll();

        double averageAge = patients.stream()
                .mapToInt(AmirAdilzhanAishaPatient::getAge)
                .average()
                .orElse(0.0);

        long adultPatients = patients.stream()
                .filter(AmirAdilzhanAishaPatient -> AmirAdilzhanAishaPatient.getAge() >= 18)
                .count();

        long minorPatients = patients.stream()
                .filter(AmirAdilzhanAishaPatient -> AmirAdilzhanAishaPatient.getAge() < 18)
                .count();

        long patientsWithIllness = patients.stream()
                .filter(AmirAdilzhanAishaPatient -> StringUtils.hasText(AmirAdilzhanAishaPatient.getIllness()))
                .count();

        AmirAdilzhanAishaPatientAnalyticsDto analyticsDto = new AmirAdilzhanAishaPatientAnalyticsDto(
                patients.size(),
                averageAge,
                adultPatients,
                minorPatients,
                patientsWithIllness
        );

        log.info("Completed async AmirAdilzhanAishaPatient analytics generation for {} patients", patients.size());
        return CompletableFuture.completedFuture(analyticsDto);
    }

    @Override
    @Async("AmirAdilzhanAishaTaskExecutor")
    public CompletableFuture<AmirAdilzhanAishaFileAuditDto> runFileStorageAudit() {
        log.info("Started async file storage audit");
        List<AmirAdilzhanAishaFileResource> files = fileResourceRepository.findAll();

        long availableFiles = files.stream()
                .filter(file -> Files.exists(Paths.get(file.getFilePath())))
                .count();

        long missingFiles = files.size() - availableFiles;

        long totalSizeBytes = files.stream()
                .map(AmirAdilzhanAishaFileResource::getSize)
                .filter(size -> size != null)
                .mapToLong(Long::longValue)
                .sum();

        AmirAdilzhanAishaFileAuditDto auditDto = new AmirAdilzhanAishaFileAuditDto(
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
    @Async("AmirAdilzhanAishaTaskExecutor")
    public CompletableFuture<AmirAdilzhanAishaSystemSummaryDto> buildSystemSummary() {
        log.info("Started async system summary build");
        List<AmirAdilzhanAishaUser> users = userRepository.findAll();
        long totalAdmins = users.stream()
                .filter(user -> user.getRole() == AmirAdilzhanAishaRole.ROLE_ADMIN)
                .count();

        AmirAdilzhanAishaSystemSummaryDto summaryDto = new AmirAdilzhanAishaSystemSummaryDto(
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
    public CompletableFuture<AmirAdilzhanAishaAsyncDashboardDto> buildDashboardSummary() {
        log.info("Started async dashboard summary aggregation");
        CompletableFuture<AmirAdilzhanAishaPatientAnalyticsDto> patientAnalyticsFuture = asyncServiceProxy.generatePatientAnalytics();
        CompletableFuture<AmirAdilzhanAishaFileAuditDto> fileAuditFuture = asyncServiceProxy.runFileStorageAudit();
        CompletableFuture<AmirAdilzhanAishaSystemSummaryDto> systemSummaryFuture = asyncServiceProxy.buildSystemSummary();

        return CompletableFuture.allOf(patientAnalyticsFuture, fileAuditFuture, systemSummaryFuture)
                .thenApply(unused -> new AmirAdilzhanAishaAsyncDashboardDto(
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
