package com.nosto.nosto_currency_converter.ExchangeRate;

import java.util.List;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nosto.nosto_currency_converter.Common.ControllerHelpers;
import com.nosto.nosto_currency_converter.Common.ServiceResult;
import com.nosto.nosto_currency_converter.Common.validation.custom_validators.Numeric.isNumeric;
import com.nosto.nosto_currency_converter.Common.validation.custom_validators.SupportedCurrency.isSupportedCurrency;
import com.nosto.nosto_currency_converter.ExchangeRate.infrastructure.external.swop.Currency;
import com.nosto.nosto_currency_converter.ExchangeRate.usecase.ConvertCurrencyUsecase;
import com.nosto.nosto_currency_converter.ExchangeRate.usecase.GetSupportedCurrenciesUsecase;

@Validated
@RestController
@RequestMapping("/api/v1")
public class ExchangeRateController extends ControllerHelpers {
    private final ConvertCurrencyUsecase convertCurrencyUsecase;
    private final GetSupportedCurrenciesUsecase getSupportedCurrenciesUsecase;

    public ExchangeRateController(ConvertCurrencyUsecase convertCurrencyUsecase,
            GetSupportedCurrenciesUsecase getSupportedCurrenciesUsecase) {
        this.convertCurrencyUsecase = convertCurrencyUsecase;
        this.getSupportedCurrenciesUsecase = getSupportedCurrenciesUsecase;

    }

    @GetMapping("/convert/{sourceCurrency}/{targetCurrency}")
    public Object convertCurrency(
            @isSupportedCurrency @PathVariable("sourceCurrency") String sourceCurrency,
            @isSupportedCurrency @PathVariable("targetCurrency") String targetCurrency,
            @isNumeric @RequestParam("amount") String amount) {

        ServiceResult<Double> serviceResult = this.convertCurrencyUsecase.execute(sourceCurrency,
                targetCurrency, Double.parseDouble(amount));
        return this.getResourceByIdResponseHandler(serviceResult);
    }

    @GetMapping("/currencies")
    public Object getSupportedCurrencies() {
        ServiceResult<List<Currency>> serviceResult = this.getSupportedCurrenciesUsecase.execute();
        return this.getResourcesResponseHandler(serviceResult);
    }

}
