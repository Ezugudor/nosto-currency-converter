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
import com.nosto.nosto_currency_converter.Common.validation.custom_validators.SupportedCurrency.SupportedCurrencyValidator;
import com.nosto.nosto_currency_converter.Common.validation.custom_validators.SupportedCurrency.isSupportedCurrency;
import com.nosto.nosto_currency_converter.ExchangeRate.usecase.ConvertCurrencyUsecase;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

@Validated
@RestController
@RequestMapping("/api/v1/convert")
public class ExchangeRateController extends ControllerHelpers {

    private final ConvertCurrencyUsecase convertCurrencyUsecase;
    private final Validator validator;
    private final SupportedCurrencyValidator supportedCurrencyValidator;

    public ExchangeRateController(ConvertCurrencyUsecase convertCurrencyUsecase,
            SupportedCurrencyValidator supportedCurrencyValidator) {
        this.convertCurrencyUsecase = convertCurrencyUsecase;
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        this.validator = factory.getValidator();
        this.supportedCurrencyValidator = supportedCurrencyValidator;
    }

    @GetMapping("/{sourceCurrency}/{targetCurrency}")
    Object convertCurrency(
            @isSupportedCurrency @PathVariable("sourceCurrency") String sourceCurrency,
            @isSupportedCurrency @PathVariable("targetCurrency") String targetCurrency,
            @isNumeric @RequestParam("amount") String amount) {

        ServiceResult<Double> serviceResult = this.convertCurrencyUsecase.execute(sourceCurrency,
                targetCurrency, Double.parseDouble(amount));
        return this.getResourceByIdResponseHandler(serviceResult);
    }

}
