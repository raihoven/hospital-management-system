package com.turatbekuly.amir.hospitalmanagementsystem.service;

import com.turatbekuly.amir.hospitalmanagementsystem.dto.TuratbekulyAmirAsyncDashboardDto;
import com.turatbekuly.amir.hospitalmanagementsystem.dto.TuratbekulyAmirFileAuditDto;
import com.turatbekuly.amir.hospitalmanagementsystem.dto.TuratbekulyAmirPatientAnalyticsDto;
import com.turatbekuly.amir.hospitalmanagementsystem.dto.TuratbekulyAmirSystemSummaryDto;

import java.util.concurrent.CompletableFuture;

public interface TuratbekulyAmirAsyncService {

    CompletableFuture<TuratbekulyAmirPatientAnalyticsDto> generatePatientAnalytics();

    CompletableFuture<TuratbekulyAmirFileAuditDto> runFileStorageAudit();

    CompletableFuture<TuratbekulyAmirSystemSummaryDto> buildSystemSummary();

    CompletableFuture<TuratbekulyAmirAsyncDashboardDto> buildDashboardSummary();
}
