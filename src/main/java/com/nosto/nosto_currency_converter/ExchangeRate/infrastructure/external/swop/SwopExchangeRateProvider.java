package com.nosto.nosto_currency_converter.ExchangeRate.infrastructure.external.swop;

import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.nosto.nosto_currency_converter.Common.BackendBaseService;
import com.nosto.nosto_currency_converter.Common.ServiceResult;
import com.nosto.nosto_currency_converter.ExchangeRate.contract.IExchange;
import com.nosto.nosto_currency_converter.ExchangeRate.infrastructure.external.swop.dto.ConvertionResponseDTO;

import io.github.cdimascio.dotenv.Dotenv;

@Service
public class SwopExchangeRateProvider extends BackendBaseService implements IExchange {

    private final Logger logger = LoggerFactory.getLogger(SwopExchangeRateProvider.class);
    private final RestTemplate restTemplate;
    private final Dotenv dotenv;
    private final String swopBaseUrl;
    private final String swopApiKey;
    private MessageSource messageSource;
    private Locale locale;

    public SwopExchangeRateProvider(RestTemplate restTemplate, Dotenv dotenv, MessageSource messageSource) {
        this.restTemplate = restTemplate;
        this.dotenv = dotenv;
        this.messageSource = messageSource;

        this.swopBaseUrl = this.dotenv.get("SWOP_BASE_ENDPOINT");
        this.swopApiKey = this.dotenv.get("SWOP_API_KEY");
        this.locale = LocaleContextHolder.getLocale();

    }

    public ServiceResult<Double> convertCurrency(String sourceCurrency, String targetCurrency, Double amount) {
        return this.resultWrapper(result -> {

            String url = this.swopBaseUrl + "/rates/" + sourceCurrency + "/" + targetCurrency;

            /**
             * TODO: Extract setup of http client to a separate function.
             * So we don't repeat this across different network functions
             * such as this one i.e convertCurrency(), getSupportedCurrencies() and more to
             * come !
             */

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "ApiKey " + this.swopApiKey);
            HttpEntity<String> entity = new HttpEntity<>(headers);
            try {
                ResponseEntity<ConvertionResponseDTO> response = restTemplate.exchange(url, HttpMethod.GET, entity,
                        new ParameterizedTypeReference<ConvertionResponseDTO>() {
                        });
                Double convertedAmount = response.getBody().getQuote() * amount;
                result.ok(convertedAmount);
            } catch (Exception e) {

                String message = messageSource.getMessage("response.swop.get_exchange_rate_quote_failure", null,
                        this.locale);
                logger.error(message);
                logger.error(e.getMessage());
            }

        });
    }

    @Cacheable(cacheNames = "supportedCurrencies")
    public ServiceResult<List<Currency>> getSupportedCurrencies() {
        return this.resultWrapper(result -> {

            String url = this.swopBaseUrl + "/currencies";
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "ApiKey " + this.swopApiKey);
            HttpEntity<String> entity = new HttpEntity<>(headers);
            try {
                ResponseEntity<List<Currency>> response = restTemplate.exchange(url, HttpMethod.GET, entity,
                        new ParameterizedTypeReference<List<Currency>>() {
                        });
                result.ok(response.getBody());
            } catch (Exception e) {
                String message = messageSource.getMessage("response.swop.get_supported_currency_failure", null,
                        this.locale);
                logger.error(message);
            }

        });
    }
}
