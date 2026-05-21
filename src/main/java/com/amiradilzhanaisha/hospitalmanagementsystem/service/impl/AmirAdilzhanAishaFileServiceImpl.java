package com.amiradilzhanaisha.hospitalmanagementsystem.service.impl;

import com.amiradilzhanaisha.hospitalmanagementsystem.dto.AmirAdilzhanAishaFileDownloadDto;
import com.amiradilzhanaisha.hospitalmanagementsystem.dto.AmirAdilzhanAishaFileDto;
import com.amiradilzhanaisha.hospitalmanagementsystem.entity.AmirAdilzhanAishaFileResource;
import com.amiradilzhanaisha.hospitalmanagementsystem.exception.AmirAdilzhanAishaFileNotFoundException;
import com.amiradilzhanaisha.hospitalmanagementsystem.exception.AmirAdilzhanAishaFileStorageException;
import com.amiradilzhanaisha.hospitalmanagementsystem.repository.AmirAdilzhanAishaFileResourceRepository;
import com.amiradilzhanaisha.hospitalmanagementsystem.service.AmirAdilzhanAishaFileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

@Service
public class AmirAdilzhanAishaFileServiceImpl implements AmirAdilzhanAishaFileService {

    private static final Logger log = LoggerFactory.getLogger(AmirAdilzhanAishaFileServiceImpl.class);
    private final AmirAdilzhanAishaFileResourceRepository fileResourceRepository;
    private final Path storageLocation;

    public AmirAdilzhanAishaFileServiceImpl(
            AmirAdilzhanAishaFileResourceRepository fileResourceRepository,
            @Value("${app.file.storage.path}") String storagePath
    ) {
        this.fileResourceRepository = fileResourceRepository;
        this.storageLocation = Paths.get(storagePath).toAbsolutePath().normalize();
        initStorage();
    }

    @Override
    public AmirAdilzhanAishaFileDto uploadFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new AmirAdilzhanAishaFileStorageException("Нельзя загрузить пустой файл");
        }

        String originalFileName = StringUtils.hasText(file.getOriginalFilename())
                ? StringUtils.cleanPath(file.getOriginalFilename())
                : "uploaded-file";
        String fileExtension = extractExtension(originalFileName);
        String storedFileName = UUID.randomUUID() + fileExtension;
        Path targetLocation = storageLocation.resolve(storedFileName);

        try {
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException exception) {
            throw new AmirAdilzhanAishaFileStorageException("Не удалось сохранить файл", exception);
        }

        AmirAdilzhanAishaFileResource fileResource = new AmirAdilzhanAishaFileResource();
        fileResource.setOriginalFileName(originalFileName);
        fileResource.setStoredFileName(storedFileName);
        fileResource.setContentType(file.getContentType());
        fileResource.setSize(file.getSize());
        fileResource.setFilePath(targetLocation.toString());

        AmirAdilzhanAishaFileResource savedFile = fileResourceRepository.save(fileResource);
        log.info(
                "Uploaded file id={} originalName='{}' storedName='{}' size={} bytes",
                savedFile.getId(),
                savedFile.getOriginalFileName(),
                savedFile.getStoredFileName(),
                savedFile.getSize()
        );
        return toDto(savedFile);
    }

    @Override
    public AmirAdilzhanAishaFileDownloadDto downloadFile(Long id) {
        AmirAdilzhanAishaFileResource fileResource = fileResourceRepository.findById(id)
                .orElseThrow(() -> new AmirAdilzhanAishaFileNotFoundException(id));

        try {
            Path filePath = Paths.get(fileResource.getFilePath()).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if (!resource.exists() || !resource.isReadable()) {
                throw new AmirAdilzhanAishaFileNotFoundException(id);
            }

            log.info("Prepared file download id={} originalName='{}'", id, fileResource.getOriginalFileName());
            return new AmirAdilzhanAishaFileDownloadDto(
                    fileResource.getOriginalFileName(),
                    fileResource.getContentType(),
                    resource
            );
        } catch (MalformedURLException exception) {
            throw new AmirAdilzhanAishaFileStorageException("Не удалось подготовить файл к скачиванию", exception);
        }
    }

    @Override
    public AmirAdilzhanAishaFileDto getFileMetadata(Long id) {
        AmirAdilzhanAishaFileResource fileResource = fileResourceRepository.findById(id)
                .orElseThrow(() -> new AmirAdilzhanAishaFileNotFoundException(id));
        log.info("Fetched file metadata id={} originalName='{}'", id, fileResource.getOriginalFileName());
        return toDto(fileResource);
    }

    @Override
    public List<AmirAdilzhanAishaFileDto> getAllFiles() {
        List<AmirAdilzhanAishaFileDto> files = fileResourceRepository.findAll()
                .stream()
                .map(this::toDto)
                .toList();
        log.info("Fetched file list size={}", files.size());
        return files;
    }

    private void initStorage() {
        try {
            Files.createDirectories(storageLocation);
            log.info("Initialized file storage at {}", storageLocation);
        } catch (IOException exception) {
            throw new AmirAdilzhanAishaFileStorageException("Не удалось создать директорию для хранения файлов", exception);
        }
    }

    private String extractExtension(String fileName) {
        if (!StringUtils.hasText(fileName) || !fileName.contains(".")) {
            return "";
        }
        return fileName.substring(fileName.lastIndexOf('.'));
    }

    private AmirAdilzhanAishaFileDto toDto(AmirAdilzhanAishaFileResource fileResource) {
        return new AmirAdilzhanAishaFileDto(
                fileResource.getId(),
                fileResource.getOriginalFileName(),
                fileResource.getContentType(),
                fileResource.getSize(),
                fileResource.getUploadedAt(),
                "/api/files/" + fileResource.getId() + "/download"
        );
    }
}
