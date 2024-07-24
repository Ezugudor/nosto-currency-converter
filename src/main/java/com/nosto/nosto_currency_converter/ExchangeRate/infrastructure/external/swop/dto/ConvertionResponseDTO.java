package com.nosto.nosto_currency_converter.ExchangeRate.infrastructure.external.swop.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)

public class ConvertionResponseDTO {

    @JsonProperty("base_currency")
    private String baseCurrency;

    @JsonProperty("quote_currency")
    private String quoteCurrency;

    private Double quote;

    private String date;

    public ConvertionResponseDTO() {
    }

    public ConvertionResponseDTO(String baseCurrency, String quoteCurrency, Double quote,
            String date) {
        this.baseCurrency = baseCurrency;
        this.quoteCurrency = quoteCurrency;
        this.quote = quote;
        this.date = date;
    }

    public String getBaseCurrency() {
        return baseCurrency;
    }

    public void setBaseCurrency(String baseCurrency) {
        this.baseCurrency = baseCurrency;
    }

    public String getQuoteCurrency() {
        return quoteCurrency;
    }

    public void setQuoteCurrency(String quoteCurrency) {
        this.quoteCurrency = quoteCurrency;
    }

    public Double getQuote() {
        return quote;
    }

    public void setQuoteAmount(Double quote) {
        this.quote = quote;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
