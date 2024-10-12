package com.example.restock.domain;

public enum NotificationStatus {
    IN_PROGRESS, // 발송 중
    CANCELED_BY_SOLD_OUT, // 품절로 인한 중단
    CANCELED_BY_ERROR, // 오류로 인한 중단
    COMPLETED // 완료
}