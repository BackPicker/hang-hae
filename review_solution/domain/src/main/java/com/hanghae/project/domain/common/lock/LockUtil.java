package com.hanghae.project.domain.common.lock;

import jakarta.validation.constraints.NotNull;

public class LockUtil {

    public static void withLock(
        @NotNull String key,
        LockProvider lockProvider,
        LockCallback function
    ) {
        Lock lock = lockProvider.lock(key);
        try {
            function.execute();
        } finally {
            lock.unLock();
        }
    }

    @FunctionalInterface
    public interface LockCallback {
        void execute();
    }
}
