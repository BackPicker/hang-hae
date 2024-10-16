package com.hanghae.project.domain.notification.common.ratelimiter;

import com.google.common.util.concurrent.RateLimiter;
import com.hanghae.project.domain.notification.common.exception.RateLimitedException;

import java.time.Duration;

public class GuavaRateLimiter extends AbstractRateLimiter {

    private final RateLimiter rateLimiter;

    GuavaRateLimiter(
        long bucket,
        Duration duration
    ) {
        super(bucket, duration);
        rateLimiter = RateLimiter.create(bucket, duration);
    }

    @Override
    public boolean tryAcquire() throws RateLimitedException {
        return rateLimiter.tryAcquire();
    }
}
