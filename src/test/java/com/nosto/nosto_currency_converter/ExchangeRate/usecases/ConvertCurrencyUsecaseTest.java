package com.nosto.nosto_currency_converter.ExchangeRate.usecases;

import com.nosto.nosto_currency_converter.Common.ServiceResult;
import com.nosto.nosto_currency_converter.Common.ServiceResultBuilder;
import com.nosto.nosto_currency_converter.Common.ResultKey;
import com.nosto.nosto_currency_converter.ExchangeRate.contract.IExchange;
import com.nosto.nosto_currency_converter.ExchangeRate.usecase.ConvertCurrencyUsecase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ConvertCurrencyUsecaseTest {

    @Mock
    private IExchange exchange;

    @Mock
    private MessageSource messageSource;

    @Spy
    @InjectMocks
    private ConvertCurrencyUsecase convertCurrencyUsecase;

    @BeforeEach
    public void setUp() {
        convertCurrencyUsecase = new ConvertCurrencyUsecase(exchange, messageSource);
    }

    @Test
    public void itShouldExecuteSuccessfullyWithResultData() {
        // Arrange
        ServiceResult<Double> serviceResult = new ServiceResultBuilder<Double>().ok(100.0).build();
        when(exchange.convertCurrency(anyString(), anyString(), anyDouble())).thenReturn(serviceResult);

        // Act
        ServiceResult<Double> result = convertCurrencyUsecase.execute("USD", "EUR", 100.0);

        // Assert
        assertEquals(ResultKey.OK, result.getKey());
        assertEquals(100.0, result.getResultObject());
    }

    @Test
    public void itShouldExecuteWithFailureResponse() {
        // Arrange
        ServiceResult<Double> serviceResult = new ServiceResultBuilder<Double>().notFound("error").build();
        when(exchange.convertCurrency(anyString(), anyString(),
                anyDouble())).thenReturn(serviceResult);

        // Act
        ServiceResult<Double> result = convertCurrencyUsecase.execute("USD", "EUR",
                100.0);

        // Assert
        assertEquals(ResultKey.NOT_FOUND, result.getKey());
    }
}
