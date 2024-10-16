package com.hanghae.project.domain.image;

import jakarta.validation.constraints.NotNull;

import java.io.IOException;

public interface ImageUploader {
    
    @NotNull
    String upload(@NotNull FileInfo fileInfo) throws IOException;
}
