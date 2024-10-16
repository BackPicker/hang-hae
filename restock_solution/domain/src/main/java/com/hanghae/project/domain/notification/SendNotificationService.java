package com.hanghae.project.domain.notification;

public interface SendNotificationService {

    void send(long productId, long userId, long restockRound);
}
