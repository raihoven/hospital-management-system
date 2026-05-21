package com.turatbekuly.amir.hospitalmanagementsystem.controller;

import com.turatbekuly.amir.hospitalmanagementsystem.dto.TuratbekulyAmirAsyncDashboardDto;
import com.turatbekuly.amir.hospitalmanagementsystem.dto.TuratbekulyAmirFileAuditDto;
import com.turatbekuly.amir.hospitalmanagementsystem.dto.TuratbekulyAmirPatientAnalyticsDto;
import com.turatbekuly.amir.hospitalmanagementsystem.dto.TuratbekulyAmirSystemSummaryDto;
import com.turatbekuly.amir.hospitalmanagementsystem.service.TuratbekulyAmirAsyncService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/async")
@CrossOrigin(origins = "*")
public class TuratbekulyAmirAsyncController {

    private final TuratbekulyAmirAsyncService asyncService;

    public TuratbekulyAmirAsyncController(TuratbekulyAmirAsyncService asyncService) {
        this.asyncService = asyncService;
    }

    @GetMapping("/patient-analytics")
    public CompletableFuture<ResponseEntity<TuratbekulyAmirPatientAnalyticsDto>> getPatientAnalytics() {
        return asyncService.generatePatientAnalytics()
                .thenApply(ResponseEntity::ok);
    }

    @GetMapping("/file-audit")
    public CompletableFuture<ResponseEntity<TuratbekulyAmirFileAuditDto>> getFileAudit() {
        return asyncService.runFileStorageAudit()
                .thenApply(ResponseEntity::ok);
    }

    @GetMapping("/system-summary")
    public CompletableFuture<ResponseEntity<TuratbekulyAmirSystemSummaryDto>> getSystemSummary() {
        return asyncService.buildSystemSummary()
                .thenApply(ResponseEntity::ok);
    }

    @GetMapping("/dashboard-summary")
    public CompletableFuture<ResponseEntity<TuratbekulyAmirAsyncDashboardDto>> getDashboardSummary() {
        return asyncService.buildDashboardSummary()
                .thenApply(ResponseEntity::ok);
    }
}
