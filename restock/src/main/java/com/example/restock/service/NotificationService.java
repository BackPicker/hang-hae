package com.example.restock.service;

import com.example.restock.repository.ProductNotificationHistoryRepository;
import com.example.restock.repository.ProductRepository;
import com.example.restock.repository.ProductUserNotificationHistoryRepository;
import com.example.restock.repository.ProductUserNotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationService {

    private final ProductRepository                        productRepository;
    private final ProductNotificationHistoryRepository     productNotificationHistoryRepository;
    private final ProductUserNotificationRepository        productUserNotificationRepository;
    private final ProductUserNotificationHistoryRepository userNotificationHistoryRepository;

}
