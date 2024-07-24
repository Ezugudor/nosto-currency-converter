package com.nosto.nosto_currency_converter.Common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class BackendBaseService {
    private final Logger logger = LoggerFactory.getLogger(BackendBaseService.class);

    public <T> ServiceResult<T> resultWrapper(QueryFunc<T> query) {
        ServiceResultBuilder<T> resultBuilder = new ServiceResultBuilder<>();
        try {
            query.apply(resultBuilder);
        } catch (Exception e) {
            onError(e, resultBuilder);
        }
        return resultBuilder.build();
    }

    public void logError(Object error) {
        if (error != null) {
            logger.error("Unexpected error occurred: {}", error);
        }
    }

    public void onError(Throwable reason, ServiceResultBuilder<?> result) {
        logError(reason);
        result.repoError(reason.getMessage());

    }

    @FunctionalInterface
    public interface QueryFunc<T> {
        void apply(ServiceResultBuilder<T> resultBuilder) throws Exception;
    }
}
