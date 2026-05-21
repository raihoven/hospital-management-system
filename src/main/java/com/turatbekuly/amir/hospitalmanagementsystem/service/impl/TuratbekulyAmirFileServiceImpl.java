package com.turatbekuly.amir.hospitalmanagementsystem.service.impl;

import com.turatbekuly.amir.hospitalmanagementsystem.dto.TuratbekulyAmirFileDownloadDto;
import com.turatbekuly.amir.hospitalmanagementsystem.dto.TuratbekulyAmirFileDto;
import com.turatbekuly.amir.hospitalmanagementsystem.entity.TuratbekulyAmirFileResource;
import com.turatbekuly.amir.hospitalmanagementsystem.exception.TuratbekulyAmirFileNotFoundException;
import com.turatbekuly.amir.hospitalmanagementsystem.exception.TuratbekulyAmirFileStorageException;
import com.turatbekuly.amir.hospitalmanagementsystem.repository.TuratbekulyAmirFileResourceRepository;
import com.turatbekuly.amir.hospitalmanagementsystem.service.TuratbekulyAmirFileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
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
public class TuratbekulyAmirFileServiceImpl implements TuratbekulyAmirFileService {

    private final TuratbekulyAmirFileResourceRepository fileResourceRepository;
    private final Path storageLocation;

    public TuratbekulyAmirFileServiceImpl(
            TuratbekulyAmirFileResourceRepository fileResourceRepository,
            @Value("${app.file.storage.path}") String storagePath
    ) {
        this.fileResourceRepository = fileResourceRepository;
        this.storageLocation = Paths.get(storagePath).toAbsolutePath().normalize();
        initStorage();
    }

    @Override
    public TuratbekulyAmirFileDto uploadFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new TuratbekulyAmirFileStorageException("Нельзя загрузить пустой файл");
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
            throw new TuratbekulyAmirFileStorageException("Не удалось сохранить файл", exception);
        }

        TuratbekulyAmirFileResource fileResource = new TuratbekulyAmirFileResource();
        fileResource.setOriginalFileName(originalFileName);
        fileResource.setStoredFileName(storedFileName);
        fileResource.setContentType(file.getContentType());
        fileResource.setSize(file.getSize());
        fileResource.setFilePath(targetLocation.toString());

        TuratbekulyAmirFileResource savedFile = fileResourceRepository.save(fileResource);
        return toDto(savedFile);
    }

    @Override
    public TuratbekulyAmirFileDownloadDto downloadFile(Long id) {
        TuratbekulyAmirFileResource fileResource = fileResourceRepository.findById(id)
                .orElseThrow(() -> new TuratbekulyAmirFileNotFoundException(id));

        try {
            Path filePath = Paths.get(fileResource.getFilePath()).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if (!resource.exists() || !resource.isReadable()) {
                throw new TuratbekulyAmirFileNotFoundException(id);
            }

            return new TuratbekulyAmirFileDownloadDto(
                    fileResource.getOriginalFileName(),
                    fileResource.getContentType(),
                    resource
            );
        } catch (MalformedURLException exception) {
            throw new TuratbekulyAmirFileStorageException("Не удалось подготовить файл к скачиванию", exception);
        }
    }

    @Override
    public TuratbekulyAmirFileDto getFileMetadata(Long id) {
        TuratbekulyAmirFileResource fileResource = fileResourceRepository.findById(id)
                .orElseThrow(() -> new TuratbekulyAmirFileNotFoundException(id));
        return toDto(fileResource);
    }

    @Override
    public List<TuratbekulyAmirFileDto> getAllFiles() {
        return fileResourceRepository.findAll()
                .stream()
                .map(this::toDto)
                .toList();
    }

    private void initStorage() {
        try {
            Files.createDirectories(storageLocation);
        } catch (IOException exception) {
            throw new TuratbekulyAmirFileStorageException("Не удалось создать директорию для хранения файлов", exception);
        }
    }

    private String extractExtension(String fileName) {
        if (!StringUtils.hasText(fileName) || !fileName.contains(".")) {
            return "";
        }
        return fileName.substring(fileName.lastIndexOf('.'));
    }

    private TuratbekulyAmirFileDto toDto(TuratbekulyAmirFileResource fileResource) {
        return new TuratbekulyAmirFileDto(
                fileResource.getId(),
                fileResource.getOriginalFileName(),
                fileResource.getContentType(),
                fileResource.getSize(),
                fileResource.getUploadedAt(),
                "/api/files/" + fileResource.getId() + "/download"
        );
    }
}
