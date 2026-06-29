package com.hotel.backend.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.hotel.backend.constant.UploadFolder;
import com.hotel.backend.exception.ValidationException;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j(topic = "FILE-STORAGE")
public class FileStorageService {

    @Value("${app.upload.dir}")
    private String uploadDir;

    @Value("${app.upload.base-url}")
    private String baseUrl;

    // Các định dạng ảnh cho phép
    private static final List<String> ALLOWED_TYPES = List.of(
            "image/jpeg", "image/png", "image/webp"
    );

    public String store(MultipartFile file, UploadFolder subFolder) throws IOException {
        // Validate file
        if (file.isEmpty()) {
        throw new ValidationException("File không được để trống");
    }

    // Validate định dạng file
    if (!ALLOWED_TYPES.contains(file.getContentType())) {
        throw new ValidationException("Chỉ chấp nhận file JPG, PNG, WEBP");
    }

    // Validate kích thước (tối đa 5MB)
    if (file.getSize() > 5 * 1024 * 1024) {
        throw new ValidationException("Kích thước file tối đa 5MB");
    }

        // Tạo folder nếu chưa có
        Path folder = Paths.get(uploadDir, subFolder.getPath());
        Files.createDirectories(folder);

        // Đổi tên file tránh trùng
        String ext = StringUtils.getFilenameExtension(file.getOriginalFilename());
        String filename = UUID.randomUUID() + "." + ext;

        // Lưu file
        Path target = folder.resolve(filename);
        Files.copy(file.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);

        log.info("Stored file: {}/{}", subFolder.getPath(), filename);
        return baseUrl + "/" + subFolder.getPath() + "/" + filename;
    }
}
