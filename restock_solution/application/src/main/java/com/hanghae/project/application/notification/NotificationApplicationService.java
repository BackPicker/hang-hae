package com.hanghae.project.application.notification;

import com.hanghae.project.domain.notification.SendNotificationService;
import com.hanghae.project.domain.notification.common.exception.RateLimitedException;
import com.hanghae.project.domain.notification.common.ratelimiter.RateLimiter;
import com.hanghae.project.domain.notification.common.ratelimiter.RateLimiterFactory;
import com.hanghae.project.domain.notification.common.ratelimiter.RateLimiterProperties;
import com.hanghae.project.domain.notification.product.ProductNotificationHistory;
import com.hanghae.project.domain.notification.product.ProductNotificationHistoryService;
import com.hanghae.project.domain.notification.user.ProductUserNotification;
import com.hanghae.project.domain.notification.user.ProductUserNotificationService;
import com.hanghae.project.domain.product.Product;
import com.hanghae.project.domain.product.ProductService;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class NotificationApplicationService {

    private static final Logger log = LoggerFactory.getLogger(NotificationApplicationService.class.getSimpleName());
    private static final int REQUEST_SIZE = 10;

    private final ProductService productService;
    private final ProductNotificationHistoryService productNotificationHistoryService;
    private final ProductUserNotificationService productUserNotificationService;
    private final SendNotificationService sendNotificationService;

    private final RateLimiter rateLimiter;
    // 1초에 500 개의 요청을 처리할 수 있음
    private static final RateLimiterProperties properties = new RateLimiterProperties(500, Duration.ofSeconds(1L));

    public NotificationApplicationService(
        RateLimiterFactory rateLimiterFactory,
        ProductService productService,
        ProductNotificationHistoryService productNotificationHistoryService,
        ProductUserNotificationService productUserNotificationService,
        SendNotificationService sendNotificationService
    ) {
        this.productService = productService;
        this.productNotificationHistoryService = productNotificationHistoryService;
        this.productUserNotificationService = productUserNotificationService;
        this.sendNotificationService = sendNotificationService;

        rateLimiter = rateLimiterFactory.create(properties);
    }

    // 의도적으로 하나의 트랜잭션을 사용하지 않음.
    public void sendByRestock(long productId) throws InterruptedException {
        // 상품의 재입고 횟수를 1 증가.
        Product product = getProduct(productId).stock();
        productService.save(product);

        ProductNotificationHistory history = ProductNotificationHistory.inProgress(productId, product.restockRound());
        productNotificationHistoryService.save(history);

        send(productId);
    }

    // Manual 요청에서는
    public void sendByManual(long productId) throws InterruptedException {
        ProductNotificationHistory history = productNotificationHistoryService.findLatestHistory(productId);
        // 한번도 restock 에 의해 notification 발송이 된 적이 없는 경우, 예외를 방출
        if (history == null) throw new IllegalArgumentException("no history for manual trigger");
        if (!history.isManualTriggerEnabled()) throw new IllegalArgumentException("manual trigger is not enabled");

        send(productId);
    }

    // 해당 상품의 재입고 알림을 설정한 유저들에게 notification 을 전송합니다.
    private void send(long productId) throws InterruptedException {
        Product product;

        List<ProductUserNotification> userNotifications;
        @Nullable Long lastSentUserId = null;
        @NotNull String cursor = String.valueOf(Long.MIN_VALUE);
        do {
            product = getProduct(productId);

            userNotifications = productUserNotificationService.getProductUserNotifications(productId, cursor, REQUEST_SIZE);
            if (product.isSoldOut()) {
                ProductUserNotification userNotification = null;
                if (!userNotifications.isEmpty()) {
                    userNotification = userNotifications.get(userNotifications.size() - 1);
                }
                handleSoldOut(product, userNotification);
            }

            for (ProductUserNotification userNotification : userNotifications) {
                // bucket을 획득할 때까지 무한 루프를 돌립니다.
                while (true) {
                    try {
                        if (rateLimiter.tryAcquire()) {
                            // bucket을 획득한 경우에만 전송을 합니다.
                            sendNotificationService.send(productId, userNotification.userId(), product.restockRound());
                            break;
                        }
                    } catch (RateLimitedException e) {
                        // rate limit에 걸렸기 때문에, retryAfterMillis 만큼 쉬었다가 다시 요청을 보냅니다.
                        try {
                            Thread.sleep(e.retryAfterMillis);
                        } catch (InterruptedException ex) {
                            handleOnError(product, userNotification);
                            return;
                        }
                    } catch (Exception e) {
                        handleOnError(product, userNotification);
                        return;
                    }
                }
            }

            // userNotifications 가 비어있지 않다면, cursor 를 업데이트 한다.
            if (!userNotifications.isEmpty()) {
                ProductUserNotification lastUserNotification = userNotifications.get(userNotifications.size() - 1);
                cursor = String.valueOf(lastUserNotification.id());
                lastSentUserId = lastUserNotification.userId();
            }

            // 완료 되었다는 의미
            if (userNotifications.size() < REQUEST_SIZE) {
                ProductNotificationHistory history = productNotificationHistoryService.findLatestHistory(productId);
                // 서버 동작 상 발생해서는 안 되는 케이스
                if (history == null) throw new IllegalStateException("history is not found.");
                ProductNotificationHistory completed = history.completed(lastSentUserId);
                productNotificationHistoryService.save(completed);
            }
        } while (userNotifications.size() == REQUEST_SIZE); // request_size 보다 작으면 cursor 가 다 돌았다는 것과 동일한 의미
    }

    @NotNull
    private Product getProduct(long productId) {
        Product product = productService.findById(productId);
        if (product == null) {
            log.error("no such product. productId: {}", productId);
            throw new NoSuchElementException("no such product");
        }

        return product;
    }

    private void handleOnError(@NotNull Product product, @NotNull ProductUserNotification userNotification) {
        ProductNotificationHistory history = productNotificationHistoryService.findLatestHistory(product.id());
        if (history == null) throw new IllegalStateException("history is not found.");

        ProductNotificationHistory onError = ProductNotificationHistory.canceledByError(history.id(), product.id(), product.restockRound(), userNotification.userId());
        productNotificationHistoryService.save(onError);
    }

    private void handleSoldOut(@NotNull Product product, @Nullable ProductUserNotification userNotification) {
        ProductNotificationHistory history = productNotificationHistoryService.findLatestHistory(product.id());
        if (history == null) throw new IllegalStateException("history is not found.");

        Long lastSentUserId = userNotification == null ? null : userNotification.userId();
        ProductNotificationHistory soldOut = ProductNotificationHistory.canceledBySoldOut(history.id(), product.id(), product.restockRound(), lastSentUserId);
        productNotificationHistoryService.save(soldOut);
    }
}
