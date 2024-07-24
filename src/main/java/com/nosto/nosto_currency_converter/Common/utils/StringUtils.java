package com.nosto.nosto_currency_converter.Common.utils;

public class StringUtils {
    public static Boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

}
