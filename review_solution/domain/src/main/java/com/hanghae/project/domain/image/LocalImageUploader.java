package com.hanghae.project.domain.image;

import jakarta.annotation.PostConstruct;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Component
@Profile("!prod")
public class LocalImageUploader implements ImageUploader {

    private static final String LOCAL_STORAGE_PREFIX = "/opt/local/storage/";

    private static final Logger log = LoggerFactory.getLogger(LocalImageUploader.class.getSimpleName());

    @PostConstruct
    public void init() {
        mkdirIfAbsent();
    }

    @NotNull
    @Override
    public String upload(@NotNull FileInfo fileInfo) throws IOException {
        String imageUrl = LOCAL_STORAGE_PREFIX + fileInfo.name;
        InputStream inputStream = new ByteArrayInputStream(fileInfo.data);
        Files.copy(inputStream, Paths.get(imageUrl), StandardCopyOption.REPLACE_EXISTING);

        return imageUrl;
    }

    private void mkdirIfAbsent() {
        Path path = Paths.get(LOCAL_STORAGE_PREFIX);
        if (Files.notExists(path)) {
            try {
                Files.createDirectories(path); // 중간에 없는 경로는 자동 생성
            } catch (IOException e) {
                log.error("failed to create file directories. directory: {}", LOCAL_STORAGE_PREFIX, e);
//                throw new RuntimeException(e);
            }
        }
    }
}
