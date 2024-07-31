package com.nosto.nosto_currency_converter.ExchangeRate.usecase;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import com.nosto.nosto_currency_converter.Common.BackendBaseService;
import com.nosto.nosto_currency_converter.Common.ServiceResult;
import com.nosto.nosto_currency_converter.ExchangeRate.contract.IExchange;

@Service
public class ConvertCurrencyUsecase extends BackendBaseService {

    private final IExchange exchange;
    public Logger logger = LoggerFactory.getLogger(ConvertCurrencyUsecase.class);
    private MessageSource messageSource;
    private Locale locale;

    public ConvertCurrencyUsecase(IExchange exchange, MessageSource messageSource) {
        this.exchange = exchange;
        this.messageSource = messageSource;
        this.locale = LocaleContextHolder.getLocale();
    }

    public ServiceResult<Double> execute(String sourceCurrency, String targetCurrency, Double amount) {
        return this.resultWrapper(result -> {
            ServiceResult<Double> sr = this.exchange.convertCurrency(sourceCurrency, targetCurrency, amount);
            if (sr.isOk()) {
                result.ok(sr.getResultObject());
            } else {
                String message = messageSource.getMessage("response.usecase.convert_currency_failure", null,
                        this.locale);
                logger.error(message);
                result.notFound(message);
            }

        });
    }
}
