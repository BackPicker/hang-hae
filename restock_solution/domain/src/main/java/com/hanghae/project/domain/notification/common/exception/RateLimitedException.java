package com.hanghae.project.domain.notification.common.exception;

public class RateLimitedException extends Exception {
    public final long retryAfterMillis;

    public RateLimitedException(long retryAfterMillis) {
        this.retryAfterMillis = retryAfterMillis;
    }
}
