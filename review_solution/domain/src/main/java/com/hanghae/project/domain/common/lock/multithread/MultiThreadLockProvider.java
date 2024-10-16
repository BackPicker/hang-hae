package com.hanghae.project.domain.common.lock.multithread;

import com.hanghae.project.domain.common.lock.LockProvider;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

// production 이 아니면 thread 간 경합을 막기 위한 lock 을 생성한다.
@Profile("!prod")
@Component
public class MultiThreadLockProvider implements LockProvider {

    private static final Logger log = LoggerFactory.getLogger(MultiThreadLockProvider.class.getName());

    private static final long acquireTimeoutMillis = 10_000L;

    @NotNull
    @Override
    public MultiThreadLock lock(String key) {
        MultiThreadLock lock = acquire(key);
        if (lock == null) {
            log.error("Failed to acquire lock for key: {}", key);
            // TODO: LockAcquiredFailedException 으로 수정 필요
            throw new RuntimeException("Failed to acquire lock for key: " + key);
        }

        return lock;
    }

    @Nullable
    private MultiThreadLock acquire(@NotNull String key) {
        long startTime = System.currentTimeMillis();
        long endTime = startTime + acquireTimeoutMillis;

        MultiThreadLock lock;

        do  {
            lock = MultiThreadLockHolder.acquire(key);
        } while (lock == null && System.currentTimeMillis() < endTime);

        return lock;
    }
}
