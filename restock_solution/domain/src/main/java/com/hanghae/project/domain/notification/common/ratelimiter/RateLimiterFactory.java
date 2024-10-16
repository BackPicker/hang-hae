package com.hanghae.project.domain.notification.common.ratelimiter;

import jakarta.validation.constraints.NotNull;

public interface RateLimiterFactory {

    RateLimiter create(@NotNull RateLimiterProperties properties);
}
