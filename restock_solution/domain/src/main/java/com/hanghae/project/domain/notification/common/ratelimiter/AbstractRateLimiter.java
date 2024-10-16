package com.hanghae.project.domain.notification.common.ratelimiter;

import java.time.Duration;

abstract class AbstractRateLimiter implements RateLimiter {

    long bucket;

    Duration duration;

    AbstractRateLimiter(long bucket, Duration duration) {
        this.bucket = bucket;
        this.duration = duration;
    }
}
