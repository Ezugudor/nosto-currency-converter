package com.nosto.nosto_currency_converter.validators;

import com.nosto.nosto_currency_converter.Common.ServiceResult;
import com.nosto.nosto_currency_converter.Common.validation.custom_validators.SupportedCurrency.SupportedCurrencyValidator;
import com.nosto.nosto_currency_converter.ExchangeRate.contract.IExchange;
import com.nosto.nosto_currency_converter.ExchangeRate.infrastructure.external.swop.Currency;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.ConstraintValidatorContext.ConstraintViolationBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.MessageSource;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class SupportedCurrencyValidatorTest {

    @Mock
    private IExchange exchange;

    @Mock
    private MessageSource messageSource;

    @InjectMocks
    private SupportedCurrencyValidator validator;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private Currency createCurrency(String code, String name, String numericCode, int decimalDigits, boolean active) {
        Currency currency = new Currency();
        currency.setCode(code);
        currency.setName(name);
        currency.setNumericCode(numericCode);
        currency.setDecimalDigits(decimalDigits);
        currency.setActive(active);
        return currency;
    }

    Currency usd = createCurrency("USD", "United States Dollar", "840", 2, true);
    Currency eur = createCurrency("EUR", "Euro", "978", 2, true);
    Currency gbp = createCurrency("GBP", "British Pound", "826", 2, true);

    @Test
    void testValidCurrency() {

        List<Currency> supportedCurrencies = Arrays.asList(usd, eur, gbp);

        ServiceResult<List<Currency>> serviceResult = new ServiceResult<>();
        serviceResult.setResultObject(supportedCurrencies);
        when(exchange.getSupportedCurrencies()).thenReturn(serviceResult);

        boolean isValid = validator.isValid("USD", null);
        assertTrue(isValid);

        isValid = validator.isValid("EUR", null);
        assertTrue(isValid);

        isValid = validator.isValid("GBP", null);
        assertTrue(isValid);
    }

    @Test
    void testInvalidCurrency() {
        List<Currency> supportedCurrencies = Arrays.asList(usd, eur, gbp);
        ServiceResult<List<Currency>> serviceResult = new ServiceResult<>();
        serviceResult.setResultObject(supportedCurrencies);
        when(exchange.getSupportedCurrencies()).thenReturn(serviceResult);

        ConstraintValidatorContext context = mock(ConstraintValidatorContext.class);
        ConstraintViolationBuilder builder = mock(ConstraintViolationBuilder.class);

        when(context.buildConstraintViolationWithTemplate(any(String.class))).thenReturn(builder);

        boolean isValid = validator.isValid("XXX", null);
        assertFalse(isValid);
    }

    @Test
    void testNullCurrency() {
        List<Currency> supportedCurrencies = Arrays.asList(usd, eur, gbp);

        ServiceResult<List<Currency>> serviceResult = new ServiceResult<>();
        serviceResult.setResultObject(supportedCurrencies);
        when(exchange.getSupportedCurrencies()).thenReturn(serviceResult);

        boolean isValid = validator.isValid(null, null);
        assertFalse(isValid);
    }

    @Test
    void testEmptyCurrency() {
        List<Currency> supportedCurrencies = Arrays.asList(usd, eur, gbp);

        ServiceResult<List<Currency>> serviceResult = new ServiceResult<>();
        serviceResult.setResultObject(supportedCurrencies);
        when(exchange.getSupportedCurrencies()).thenReturn(serviceResult);

        boolean isValid = validator.isValid("", null);
        assertFalse(isValid);
    }
}
