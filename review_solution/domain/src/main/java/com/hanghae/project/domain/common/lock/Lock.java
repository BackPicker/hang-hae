package com.hanghae.project.domain.common.lock;

import jakarta.validation.constraints.NotNull;

public abstract class Lock {

    @NotNull String key;

    protected Lock(@NotNull String key) {
        this.key = key;
    }

    protected abstract void unLock();

    @NotNull
    protected String getKey() {
        return key;
    }
}
