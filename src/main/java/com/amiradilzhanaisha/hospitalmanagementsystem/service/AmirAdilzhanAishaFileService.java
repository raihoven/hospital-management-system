package com.amiradilzhanaisha.hospitalmanagementsystem.service;

import com.amiradilzhanaisha.hospitalmanagementsystem.dto.AmirAdilzhanAishaFileDownloadDto;
import com.amiradilzhanaisha.hospitalmanagementsystem.dto.AmirAdilzhanAishaFileDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface AmirAdilzhanAishaFileService {

    AmirAdilzhanAishaFileDto uploadFile(MultipartFile file);

    AmirAdilzhanAishaFileDownloadDto downloadFile(Long id);

    AmirAdilzhanAishaFileDto getFileMetadata(Long id);

    List<AmirAdilzhanAishaFileDto> getAllFiles();
}
