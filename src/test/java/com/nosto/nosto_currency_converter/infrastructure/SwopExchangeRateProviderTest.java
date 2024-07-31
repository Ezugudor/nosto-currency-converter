package com.nosto.nosto_currency_converter.infrastructure;

import com.nosto.nosto_currency_converter.Common.ServiceResult;
import com.nosto.nosto_currency_converter.ExchangeRate.infrastructure.external.swop.SwopExchangeRateProvider;
import com.nosto.nosto_currency_converter.ExchangeRate.infrastructure.external.swop.dto.ConvertionResponseDTO;
import io.github.cdimascio.dotenv.Dotenv;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.MessageSource;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class SwopExchangeRateProviderTest {

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private Dotenv dotenv;

    @Mock
    private MessageSource messageSource;

    @InjectMocks
    private SwopExchangeRateProvider swopExchangeRateProvider;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        when(dotenv.get("SWOP_BASE_ENDPOINT")).thenReturn("https://api.swop.com");
        when(dotenv.get("SWOP_API_KEY")).thenReturn("test-api-key");
    }

    @Test
    public void itShouldConvertCurrencyWithSuccessResponseFromSwop() {
        String sourceCurrency = "USD";
        String targetCurrency = "EUR";
        Double amount = 100.0;

        ConvertionResponseDTO responseDTO = new ConvertionResponseDTO();
        responseDTO.setQuoteAmount(0.85);

        ResponseEntity<ConvertionResponseDTO> responseEntity = new ResponseEntity<>(responseDTO, HttpStatus.OK);

        when(restTemplate.exchange(
                anyString(),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                eq(new ParameterizedTypeReference<ConvertionResponseDTO>() {
                }))).thenReturn(responseEntity);

        ServiceResult<Double> result = swopExchangeRateProvider.convertCurrency(sourceCurrency, targetCurrency, amount);

        assertTrue(result.isOk());
        assertEquals(85, result.getResultObject());
    }

    @Test
    public void testConvertCurrency_Failure() {
        String sourceCurrency = "USD";
        String targetCurrency = "EUR";
        Double amount = 100.0;

        when(restTemplate.exchange(
                anyString(),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                eq(new ParameterizedTypeReference<ConvertionResponseDTO>() {
                })))
                .thenThrow(new RuntimeException("API failure"));

        when(messageSource.getMessage(anyString(), any(),
                any(Locale.class))).thenReturn("Error message");

        ServiceResult<Double> result = swopExchangeRateProvider.convertCurrency(sourceCurrency, targetCurrency,
                amount);

        assertFalse(result.isOk());
        assertNull(result.getResultObject());
    }
}
