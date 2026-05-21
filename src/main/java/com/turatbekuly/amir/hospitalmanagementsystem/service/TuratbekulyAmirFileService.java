package com.turatbekuly.amir.hospitalmanagementsystem.service;

import com.turatbekuly.amir.hospitalmanagementsystem.dto.TuratbekulyAmirFileDownloadDto;
import com.turatbekuly.amir.hospitalmanagementsystem.dto.TuratbekulyAmirFileDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface TuratbekulyAmirFileService {

    TuratbekulyAmirFileDto uploadFile(MultipartFile file);

    TuratbekulyAmirFileDownloadDto downloadFile(Long id);

    TuratbekulyAmirFileDto getFileMetadata(Long id);

    List<TuratbekulyAmirFileDto> getAllFiles();
}
