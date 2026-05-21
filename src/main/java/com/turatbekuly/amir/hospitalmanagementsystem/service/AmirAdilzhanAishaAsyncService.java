package com.turatbekuly.amir.hospitalmanagementsystem.service;

import com.turatbekuly.amir.hospitalmanagementsystem.dto.AmirAdilzhanAishaAsyncDashboardDto;
import com.turatbekuly.amir.hospitalmanagementsystem.dto.AmirAdilzhanAishaFileAuditDto;
import com.turatbekuly.amir.hospitalmanagementsystem.dto.AmirAdilzhanAishaPatientAnalyticsDto;
import com.turatbekuly.amir.hospitalmanagementsystem.dto.AmirAdilzhanAishaSystemSummaryDto;

import java.util.concurrent.CompletableFuture;

public interface AmirAdilzhanAishaAsyncService {

    CompletableFuture<AmirAdilzhanAishaPatientAnalyticsDto> generatePatientAnalytics();

    CompletableFuture<AmirAdilzhanAishaFileAuditDto> runFileStorageAudit();

    CompletableFuture<AmirAdilzhanAishaSystemSummaryDto> buildSystemSummary();

    CompletableFuture<AmirAdilzhanAishaAsyncDashboardDto> buildDashboardSummary();
}
