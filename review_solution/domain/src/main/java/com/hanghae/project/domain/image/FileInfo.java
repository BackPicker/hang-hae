package com.hanghae.project.domain.image;

import jakarta.validation.constraints.NotNull;

public class FileInfo {
    @NotNull String name;
    @NotNull byte[] data;

    public FileInfo(@NotNull String name, @NotNull byte[] data) {
        this.name = name;
        this.data = data;
    }
}
