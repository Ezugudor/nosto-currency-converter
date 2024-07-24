package com.nosto.nosto_currency_converter.Common.validation.custom_validators.Numeric;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import jakarta.validation.Payload;

import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;

import jakarta.validation.Constraint;

@Documented
@Constraint(validatedBy = NumericValidator.class)
@Target({ FIELD, PARAMETER })
@Retention(RUNTIME)
public @interface isNumeric {
    String message() default "{input_validation.failure.amount}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
