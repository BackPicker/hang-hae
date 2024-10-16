package com.hanghae.project.domain.common.lock;

import jakarta.validation.constraints.NotNull;

public interface LockProvider {

    @NotNull
    Lock lock(@NotNull String key);
}