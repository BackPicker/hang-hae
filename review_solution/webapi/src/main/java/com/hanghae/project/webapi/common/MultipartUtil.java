package com.hanghae.project.webapi.common;

import jakarta.annotation.Nullable;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;

public class MultipartUtil {
    private static final Set<String> ALLOWED_IMAGE_FORMATS = Set.of("jpg", "jpeg", "png", "gif");

    public static void validateImageFile(@Nullable MultipartFile file) {
        if (file == null || file.isEmpty()) return;
        String fileName = Objects.requireNonNull(file.getOriginalFilename());
        int dotIndex = fileName.lastIndexOf(".");
        if (dotIndex == -1) throw new IllegalArgumentException("Invalid file name");

        String extension = fileName.substring(dotIndex + 1).toLowerCase(Locale.ROOT);
        if (!ALLOWED_IMAGE_FORMATS.contains(extension)) throw new IllegalArgumentException("Invalid file extension");
    }

    public static byte[] toByteArray(MultipartFile file) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byteArrayOutputStream.writeBytes(file.getBytes());
        return byteArrayOutputStream.toByteArray();
    }
}
