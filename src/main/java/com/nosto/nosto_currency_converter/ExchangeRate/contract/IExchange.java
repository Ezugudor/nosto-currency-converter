package com.nosto.nosto_currency_converter.ExchangeRate.contract;

import java.util.List;

import com.nosto.nosto_currency_converter.Common.ServiceResult;
import com.nosto.nosto_currency_converter.ExchangeRate.infrastructure.external.swop.Currency;

public interface IExchange {
    public ServiceResult<Double> convertCurrency(String sourceCurrency, String targetCurrency, Double amount);

    public ServiceResult<List<Currency>> getSupportedCurrencies();

}
