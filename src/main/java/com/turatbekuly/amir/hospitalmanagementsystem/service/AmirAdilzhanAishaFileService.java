package com.turatbekuly.amir.hospitalmanagementsystem.service;

import com.turatbekuly.amir.hospitalmanagementsystem.dto.AmirAdilzhanAishaFileDownloadDto;
import com.turatbekuly.amir.hospitalmanagementsystem.dto.AmirAdilzhanAishaFileDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface AmirAdilzhanAishaFileService {

    AmirAdilzhanAishaFileDto uploadFile(MultipartFile file);

    AmirAdilzhanAishaFileDownloadDto downloadFile(Long id);

    AmirAdilzhanAishaFileDto getFileMetadata(Long id);

    List<AmirAdilzhanAishaFileDto> getAllFiles();
}
