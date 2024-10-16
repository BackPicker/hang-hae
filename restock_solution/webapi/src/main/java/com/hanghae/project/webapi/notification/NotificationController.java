package com.hanghae.project.webapi.notification;

import com.hanghae.project.application.notification.NotificationApplicationService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NotificationController {

    private final NotificationApplicationService notificationApplicationService;

    public NotificationController(NotificationApplicationService notificationApplicationService) {
        this.notificationApplicationService = notificationApplicationService;
    }

    @PostMapping("/products/{productId}/notifications/re-stock")
    public void sendByRestock(@PathVariable long productId) throws InterruptedException {
        notificationApplicationService.sendByRestock(productId);
    }

    @PostMapping("/admin/products/{productId}/notifications/re-stock")
    public void sendByManual(@PathVariable long productId) throws InterruptedException {
        notificationApplicationService.sendByManual(productId);
    }
}
