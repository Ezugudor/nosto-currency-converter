package com.nosto.nosto_currency_converter.Common.validation.custom_validators.SupportedCurrency;

import java.util.List;
import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import com.nosto.nosto_currency_converter.Common.ServiceResult;
import com.nosto.nosto_currency_converter.ExchangeRate.contract.IExchange;
import com.nosto.nosto_currency_converter.ExchangeRate.infrastructure.external.swop.Currency;

import io.github.cdimascio.dotenv.Dotenv;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

@Component
public class SupportedCurrencyValidator implements ConstraintValidator<isSupportedCurrency, Object> {

    private final IExchange exchange;
    private MessageSource messageSource;

    public SupportedCurrencyValidator(List<Currency> currencyList, Dotenv dotenv, IExchange exchange,
            MessageSource messageSource) {
        this.exchange = exchange;
        this.messageSource = messageSource;

    }

    @Override
    public boolean isValid(Object input, ConstraintValidatorContext context) {
        ServiceResult<List<Currency>> currencyListSR = this.exchange.getSupportedCurrencies();
        Boolean isSupported = currencyListSR.getResultObject().stream()
                .anyMatch(currency -> currency.getCode().equalsIgnoreCase((String) input));

        if (!isSupported && context != null) {

            Locale locale = LocaleContextHolder.getLocale();
            String message = messageSource.getMessage("input_validation.failure.currency", null, locale);

            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(message)
                    .addConstraintViolation();
        }

        return isSupported;

    }

}
