package com.amiradilzhanaisha.hospitalmanagementsystem.controller;

import com.amiradilzhanaisha.hospitalmanagementsystem.dto.AmirAdilzhanAishaAsyncDashboardDto;
import com.amiradilzhanaisha.hospitalmanagementsystem.dto.AmirAdilzhanAishaFileAuditDto;
import com.amiradilzhanaisha.hospitalmanagementsystem.dto.AmirAdilzhanAishaPatientAnalyticsDto;
import com.amiradilzhanaisha.hospitalmanagementsystem.dto.AmirAdilzhanAishaSystemSummaryDto;
import com.amiradilzhanaisha.hospitalmanagementsystem.service.AmirAdilzhanAishaAsyncService;
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
public class AmirAdilzhanAishaAsyncController {

    private final AmirAdilzhanAishaAsyncService asyncService;

    public AmirAdilzhanAishaAsyncController(AmirAdilzhanAishaAsyncService asyncService) {
        this.asyncService = asyncService;
    }

    @GetMapping("/patient-analytics")
    @Operation(summary = "Generate AmirAdilzhanAishaPatient analytics", description = "Runs asynchronous AmirAdilzhanAishaPatient analytics. Requires ADMIN role")
    public CompletableFuture<ResponseEntity<AmirAdilzhanAishaPatientAnalyticsDto>> getPatientAnalytics() {
        return asyncService.generatePatientAnalytics()
                .thenApply(ResponseEntity::ok);
    }

    @GetMapping("/file-audit")
    @Operation(summary = "Run file storage audit", description = "Runs asynchronous storage audit. Requires ADMIN role")
    public CompletableFuture<ResponseEntity<AmirAdilzhanAishaFileAuditDto>> getFileAudit() {
        return asyncService.runFileStorageAudit()
                .thenApply(ResponseEntity::ok);
    }

    @GetMapping("/system-summary")
    @Operation(summary = "Build system summary", description = "Builds asynchronous system summary. Requires ADMIN role")
    public CompletableFuture<ResponseEntity<AmirAdilzhanAishaSystemSummaryDto>> getSystemSummary() {
        return asyncService.buildSystemSummary()
                .thenApply(ResponseEntity::ok);
    }

    @GetMapping("/dashboard-summary")
    @Operation(summary = "Build dashboard summary", description = "Runs three asynchronous processes in parallel and aggregates the result. Requires ADMIN role")
    public CompletableFuture<ResponseEntity<AmirAdilzhanAishaAsyncDashboardDto>> getDashboardSummary() {
        return asyncService.buildDashboardSummary()
                .thenApply(ResponseEntity::ok);
    }
}
