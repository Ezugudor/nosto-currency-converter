package com.nosto.nosto_currency_converter.ExchangeRate.usecase;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.nosto.nosto_currency_converter.Common.BackendBaseService;
import com.nosto.nosto_currency_converter.Common.ServiceResult;
import com.nosto.nosto_currency_converter.ExchangeRate.contract.IExchange;

@Service
public class ConvertCurrencyUsecase extends BackendBaseService {

    private final IExchange exchange;
    private final Logger logger = LoggerFactory.getLogger(ConvertCurrencyUsecase.class);

    public ConvertCurrencyUsecase(IExchange exchange) {
        this.exchange = exchange;

    }

    public ServiceResult<Double> execute(String sourceCurrency, String targetCurrency, Double amount) {
        return this.resultWrapper(result -> {
            ServiceResult<Double> sr = this.exchange.convertCurrency(sourceCurrency, targetCurrency, amount);
            if (sr.isOk()) {
                result.ok(sr.getResultObject());
                this.logger.info(sr.getResultObject().toString());
            } else {
                result.notFound("error");
                this.logger.info("error");
            }

        });
    }
}
