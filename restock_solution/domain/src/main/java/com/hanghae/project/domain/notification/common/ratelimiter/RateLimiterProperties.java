package com.hanghae.project.domain.notification.common.ratelimiter;

import jakarta.validation.constraints.NotNull;

import java.time.Duration;

public record RateLimiterProperties(
    long bucket,
    @NotNull Duration duration
) {
}
