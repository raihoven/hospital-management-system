package com.amiradilzhanaisha.hospitalmanagementsystem.service;

import com.amiradilzhanaisha.hospitalmanagementsystem.dto.AmirAdilzhanAishaAsyncDashboardDto;
import com.amiradilzhanaisha.hospitalmanagementsystem.dto.AmirAdilzhanAishaFileAuditDto;
import com.amiradilzhanaisha.hospitalmanagementsystem.dto.AmirAdilzhanAishaPatientAnalyticsDto;
import com.amiradilzhanaisha.hospitalmanagementsystem.dto.AmirAdilzhanAishaSystemSummaryDto;

import java.util.concurrent.CompletableFuture;

public interface AmirAdilzhanAishaAsyncService {

    CompletableFuture<AmirAdilzhanAishaPatientAnalyticsDto> generatePatientAnalytics();

    CompletableFuture<AmirAdilzhanAishaFileAuditDto> runFileStorageAudit();

    CompletableFuture<AmirAdilzhanAishaSystemSummaryDto> buildSystemSummary();

    CompletableFuture<AmirAdilzhanAishaAsyncDashboardDto> buildDashboardSummary();
}
