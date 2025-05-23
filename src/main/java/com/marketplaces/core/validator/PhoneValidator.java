package com.marketplaces.core.validator;

import com.marketplaces.core.validator.constraints.Phone;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PhoneValidator implements ConstraintValidator<Phone, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        return (value == null || value.isEmpty()) || value.matches("\\d{12}");
    }
}
