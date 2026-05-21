package com.turatbekuly.amir.hospitalmanagementsystem.controller;

import com.turatbekuly.amir.hospitalmanagementsystem.dto.TuratbekulyAmirAsyncDashboardDto;
import com.turatbekuly.amir.hospitalmanagementsystem.dto.TuratbekulyAmirFileAuditDto;
import com.turatbekuly.amir.hospitalmanagementsystem.dto.TuratbekulyAmirPatientAnalyticsDto;
import com.turatbekuly.amir.hospitalmanagementsystem.dto.TuratbekulyAmirSystemSummaryDto;
import com.turatbekuly.amir.hospitalmanagementsystem.service.TuratbekulyAmirAsyncService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/async")
@CrossOrigin(origins = "*")
@Tag(name = "Async Analytics", description = "Asynchronous analytics and audit endpoints")
@SecurityRequirement(name = "bearerAuth")
public class TuratbekulyAmirAsyncController {

    private final TuratbekulyAmirAsyncService asyncService;

    public TuratbekulyAmirAsyncController(TuratbekulyAmirAsyncService asyncService) {
        this.asyncService = asyncService;
    }

    @GetMapping("/patient-analytics")
    @Operation(summary = "Generate patient analytics", description = "Runs asynchronous patient analytics. Requires ADMIN role")
    public CompletableFuture<ResponseEntity<TuratbekulyAmirPatientAnalyticsDto>> getPatientAnalytics() {
        return asyncService.generatePatientAnalytics()
                .thenApply(ResponseEntity::ok);
    }

    @GetMapping("/file-audit")
    @Operation(summary = "Run file storage audit", description = "Runs asynchronous storage audit. Requires ADMIN role")
    public CompletableFuture<ResponseEntity<TuratbekulyAmirFileAuditDto>> getFileAudit() {
        return asyncService.runFileStorageAudit()
                .thenApply(ResponseEntity::ok);
    }

    @GetMapping("/system-summary")
    @Operation(summary = "Build system summary", description = "Builds asynchronous system summary. Requires ADMIN role")
    public CompletableFuture<ResponseEntity<TuratbekulyAmirSystemSummaryDto>> getSystemSummary() {
        return asyncService.buildSystemSummary()
                .thenApply(ResponseEntity::ok);
    }

    @GetMapping("/dashboard-summary")
    @Operation(summary = "Build dashboard summary", description = "Runs three asynchronous processes in parallel and aggregates the result. Requires ADMIN role")
    public CompletableFuture<ResponseEntity<TuratbekulyAmirAsyncDashboardDto>> getDashboardSummary() {
        return asyncService.buildDashboardSummary()
                .thenApply(ResponseEntity::ok);
    }
}
