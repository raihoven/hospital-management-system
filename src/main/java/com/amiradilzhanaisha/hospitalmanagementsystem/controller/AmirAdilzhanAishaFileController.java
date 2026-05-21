package com.amiradilzhanaisha.hospitalmanagementsystem.controller;

import com.amiradilzhanaisha.hospitalmanagementsystem.dto.AmirAdilzhanAishaFileDownloadDto;
import com.amiradilzhanaisha.hospitalmanagementsystem.dto.AmirAdilzhanAishaFileDto;
import com.amiradilzhanaisha.hospitalmanagementsystem.service.AmirAdilzhanAishaFileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/files")
@CrossOrigin(origins = "*")
@Tag(name = "Files", description = "File upload, listing, metadata and download endpoints")
@SecurityRequirement(name = "bearerAuth")
public class AmirAdilzhanAishaFileController {

    private final AmirAdilzhanAishaFileService fileService;

    public AmirAdilzhanAishaFileController(AmirAdilzhanAishaFileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Upload file", description = "Uploads a file and stores metadata in PostgreSQL. Requires ADMIN role")
    public ResponseEntity<AmirAdilzhanAishaFileDto> uploadFile(@RequestPart("file") MultipartFile file) {
        return ResponseEntity.ok(fileService.uploadFile(file));
    }

    @GetMapping
    @Operation(summary = "Get all files", description = "Returns metadata for all uploaded files")
    public List<AmirAdilzhanAishaFileDto> getAllFiles() {
        return fileService.getAllFiles();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get file metadata", description = "Returns metadata for a single file")
    public ResponseEntity<AmirAdilzhanAishaFileDto> getFileMetadata(@PathVariable Long id) {
        return ResponseEntity.ok(fileService.getFileMetadata(id));
    }

    @GetMapping("/{id}/download")
    @Operation(summary = "Download file", description = "Downloads file content by identifier")
    public ResponseEntity<org.springframework.core.io.Resource> downloadFile(@PathVariable Long id) {
        AmirAdilzhanAishaFileDownloadDto fileDownload = fileService.downloadFile(id);
        String contentType = fileDownload.contentType() != null
                ? fileDownload.contentType()
                : MediaType.APPLICATION_OCTET_STREAM_VALUE;

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, ContentDisposition.attachment()
                        .filename(fileDownload.originalFileName())
                        .build()
                        .toString())
                .body(fileDownload.resource());
    }
}
