package com.example.restock.product.aop;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class RetryAspect {

    @Pointcut("@annotation(retryable)")
    public void retryableMethod(Retryable retryable) {
    }

    @Around("retryableMethod(retryable)")
    public Object retry(ProceedingJoinPoint joinPoint, Retryable retryable) throws Throwable {
        int       attempts      = 0;
        int       maxAttempts   = retryable.value();
        Throwable lastException = null;

        while (attempts < maxAttempts) {
            try {
                return joinPoint.proceed(); // 메소드 실행
            } catch (Throwable e) {
                lastException = e;
                attempts++;
                if (attempts >= maxAttempts) {
                    throw lastException; // 최대 재시도 횟수 초과 시 예외 던짐
                }
            }
        }
        throw lastException; // 안전하게 마지막 예외를 던짐
    }
}
