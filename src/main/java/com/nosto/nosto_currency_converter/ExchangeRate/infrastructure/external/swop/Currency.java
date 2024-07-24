package com.nosto.nosto_currency_converter.ExchangeRate.infrastructure.external.swop;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nosto.nosto_currency_converter.ExchangeRate.contract.ICurrency;

public class Currency implements ICurrency {
    private String code;

    @JsonProperty("numeric_code")
    private String numeric_code;

    @JsonProperty("decimal_digits")
    private int decimal_digits;

    private String name;

    private boolean active;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNumericCode() {
        return numeric_code;
    }

    public void setNumericCode(String numeric_code) {
        this.numeric_code = numeric_code;
    }

    public int getDecimalDigits() {
        return decimal_digits;
    }

    public void setDecimalDigits(int decimal_digits) {
        this.decimal_digits = decimal_digits;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
