package com.nosto.nosto_currency_converter.ExchangeRate;

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
import com.nosto.nosto_currency_converter.ExchangeRate.usecase.ConvertCurrencyUsecase;

@Validated
@RestController
@RequestMapping("/api/v1/convert")
public class ExchangeRateController extends ControllerHelpers {
    private final ConvertCurrencyUsecase convertCurrencyUsecase;

    public ExchangeRateController(ConvertCurrencyUsecase convertCurrencyUsecase) {
        this.convertCurrencyUsecase = convertCurrencyUsecase;
    }

    @GetMapping("/{sourceCurrency}/{targetCurrency}")
    public Object convertCurrency(
            @isSupportedCurrency @PathVariable("sourceCurrency") String sourceCurrency,
            @isSupportedCurrency @PathVariable("targetCurrency") String targetCurrency,
            @isNumeric @RequestParam("amount") String amount) {

        ServiceResult<Double> serviceResult = this.convertCurrencyUsecase.execute(sourceCurrency,
                targetCurrency, Double.parseDouble(amount));
        return this.getResourceByIdResponseHandler(serviceResult);
    }

}
