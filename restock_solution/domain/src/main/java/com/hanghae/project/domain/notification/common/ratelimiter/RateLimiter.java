package com.hanghae.project.domain.notification.common.ratelimiter;

import com.hanghae.project.domain.notification.common.exception.RateLimitedException;

public interface RateLimiter {

    boolean tryAcquire() throws RateLimitedException;
}
