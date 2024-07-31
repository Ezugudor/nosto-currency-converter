package com.nosto.nosto_currency_converter;

import com.nosto.nosto_currency_converter.Common.BaseResponse;
import com.nosto.nosto_currency_converter.Common.ServiceResult;
import com.nosto.nosto_currency_converter.Common.ServiceResultBuilder;
import com.nosto.nosto_currency_converter.ExchangeRate.ExchangeRateController;
import com.nosto.nosto_currency_converter.ExchangeRate.usecase.ConvertCurrencyUsecase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class ExchangeRateControllerTest {

    @Mock
    private ConvertCurrencyUsecase convertCurrencyUsecase;

    @InjectMocks
    private ExchangeRateController exchangeRateController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testConvertCurrencySuccess() {
        String sourceCurrency = "USD";
        String targetCurrency = "EUR";
        String amount = "100";
        Double convertedAmount = 85.0;

        ServiceResult<Double> serviceResult = new ServiceResultBuilder<Double>()
                .ok(convertedAmount)
                .build();
        when(convertCurrencyUsecase.execute(anyString(), anyString(), anyDouble())).thenReturn(serviceResult);

        Object response = exchangeRateController.convertCurrency(sourceCurrency, targetCurrency, amount);

        if (response instanceof BaseResponse) {
            BaseResponse<Double> baseResponse = (BaseResponse<Double>) response;
            assertEquals(HttpStatus.OK.value(), baseResponse.getStatus());
            assertEquals(convertedAmount, baseResponse.getData());
        } else {
            throw new AssertionError("Response is not of type BaseResponse");
        }
    }

    @Test
    void testConvertCurrencyNotFound() {
        String sourceCurrency = "USD";
        String targetCurrency = "EUR";
        String amount = "100";

        ServiceResult<Double> serviceResult = new ServiceResultBuilder<Double>()
                .notFound("Currency not found")
                .build();
        when(convertCurrencyUsecase.execute(anyString(), anyString(), anyDouble())).thenReturn(serviceResult);

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> exchangeRateController.convertCurrency(sourceCurrency, targetCurrency, amount));

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("Currency not found", exception.getReason());
    }

    @Test
    void testConvertCurrencyInternalError() {
        String sourceCurrency = "USD";
        String targetCurrency = "EUR";
        String amount = "100";

        ServiceResult<Double> serviceResult = new ServiceResultBuilder<Double>()
                .repoError("Internal server error")
                .build();
        when(convertCurrencyUsecase.execute(anyString(), anyString(),
                anyDouble())).thenReturn(serviceResult);

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> exchangeRateController.convertCurrency(sourceCurrency, targetCurrency,
                        amount));
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getStatusCode());
        assertEquals("Internal server error", exception.getReason());
    }
}
