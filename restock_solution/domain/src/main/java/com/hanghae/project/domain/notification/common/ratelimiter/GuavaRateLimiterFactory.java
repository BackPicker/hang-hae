package com.hanghae.project.domain.notification.common.ratelimiter;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("!prod")
@Component
public class GuavaRateLimiterFactory implements RateLimiterFactory {

    @Override
    public RateLimiter create(RateLimiterProperties properties) {
        return new GuavaRateLimiter(properties.bucket(), properties.duration());
    }
}
