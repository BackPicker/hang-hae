package com.hanghae.project.domain.common.lock.multithread;

import com.hanghae.project.domain.common.lock.Lock;
import jakarta.validation.constraints.NotNull;

public class MultiThreadLock extends Lock {

    public MultiThreadLock(@NotNull String key) {
        super(key);
    }

    @Override
    protected void unLock() {
        MultiThreadLockHolder.unlock(getKey());
    }
}
