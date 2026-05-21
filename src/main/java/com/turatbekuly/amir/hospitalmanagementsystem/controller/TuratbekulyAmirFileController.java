package com.turatbekuly.amir.hospitalmanagementsystem.controller;

import com.turatbekuly.amir.hospitalmanagementsystem.dto.TuratbekulyAmirFileDownloadDto;
import com.turatbekuly.amir.hospitalmanagementsystem.dto.TuratbekulyAmirFileDto;
import com.turatbekuly.amir.hospitalmanagementsystem.service.TuratbekulyAmirFileService;
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
public class TuratbekulyAmirFileController {

    private final TuratbekulyAmirFileService fileService;

    public TuratbekulyAmirFileController(TuratbekulyAmirFileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<TuratbekulyAmirFileDto> uploadFile(@RequestPart("file") MultipartFile file) {
        return ResponseEntity.ok(fileService.uploadFile(file));
    }

    @GetMapping
    public List<TuratbekulyAmirFileDto> getAllFiles() {
        return fileService.getAllFiles();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TuratbekulyAmirFileDto> getFileMetadata(@PathVariable Long id) {
        return ResponseEntity.ok(fileService.getFileMetadata(id));
    }

    @GetMapping("/{id}/download")
    public ResponseEntity<org.springframework.core.io.Resource> downloadFile(@PathVariable Long id) {
        TuratbekulyAmirFileDownloadDto fileDownload = fileService.downloadFile(id);
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
