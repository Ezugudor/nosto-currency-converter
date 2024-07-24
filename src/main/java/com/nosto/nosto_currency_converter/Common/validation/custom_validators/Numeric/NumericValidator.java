package com.nosto.nosto_currency_converter.Common.validation.custom_validators.Numeric;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import com.nosto.nosto_currency_converter.Common.utils.StringUtils;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NumericValidator implements ConstraintValidator<isNumeric, Object> {

    private MessageSource messageSource;

    public NumericValidator(MessageSource messageSource) {
        this.messageSource = messageSource;

    }

    @Override
    public boolean isValid(Object input, ConstraintValidatorContext context) {
        if (input == null)
            return handleError(context);
        if (input instanceof String)
            return !StringUtils.isNumeric((String) input) ? handleError(context) : true;
        return !(input instanceof Number) ? handleError(context) : true;
    }

    public Boolean handleError(ConstraintValidatorContext context)

    {
        if (context != null) {
            Locale locale = LocaleContextHolder.getLocale();
            String message = messageSource.getMessage("input_validation.failure.amount", null, locale);

            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(message)
                    .addConstraintViolation();
        }

        return false;
    }

}
