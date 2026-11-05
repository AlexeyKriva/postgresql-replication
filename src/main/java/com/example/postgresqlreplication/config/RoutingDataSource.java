package com.example.postgresqlreplication.config;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.Nullable;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import static com.example.postgresqlreplication.util.AppConstants.READ_LOOKUP_KEY;
import static com.example.postgresqlreplication.util.AppConstants.WRITE_LOOKUP_KEY;

@Slf4j
public class RoutingDataSource extends AbstractRoutingDataSource {
    public Object getCurrentMode() {
        return TransactionSynchronizationManager.isCurrentTransactionReadOnly()
                ? READ_LOOKUP_KEY
                : WRITE_LOOKUP_KEY;
    }

    @Override
    protected @Nullable Object determineCurrentLookupKey() {
        Object lookupKey = getCurrentMode();

        log.warn(
                "DATASOURCE ROUTING: thread={}, timestamp={}, lookupKey={}, springReadOnly={}",
                Thread.currentThread().getName(),
                System.nanoTime(),
                lookupKey,
                TransactionSynchronizationManager.isCurrentTransactionReadOnly()
        );

        return lookupKey;
    }
}
