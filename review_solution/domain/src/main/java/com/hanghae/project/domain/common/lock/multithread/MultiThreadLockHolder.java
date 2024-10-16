package com.hanghae.project.domain.common.lock.multithread;

import jakarta.validation.constraints.NotNull;

import java.util.concurrent.ConcurrentHashMap;

public class MultiThreadLockHolder {

    private static final ConcurrentHashMap<String, MultiThreadLock> locks = new ConcurrentHashMap<>();

    public static MultiThreadLock acquire(@NotNull String key) {
        if (locks.containsKey(key)) {
            return null;
        }

        MultiThreadLock lock = new MultiThreadLock(key);
        locks.put(key, lock);
        return lock;
    }

    public static void unlock(@NotNull String key) {
        locks.remove(key);
    }
}
