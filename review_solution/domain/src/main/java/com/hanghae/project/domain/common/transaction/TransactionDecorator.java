package com.hanghae.project.domain.common.transaction;

import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Component
public class TransactionDecorator {

    public void executeOnRollback(@NotNull Runnable runnable) {
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
            @Override
            public void afterCompletion(int status) {
                if (status == TransactionSynchronization.STATUS_ROLLED_BACK) {
                    runnable.run();
                }
            }
        });
    }
}
