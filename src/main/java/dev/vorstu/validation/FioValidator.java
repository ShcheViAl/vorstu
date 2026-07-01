package dev.vorstu.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class FioValidator implements ConstraintValidator<ContainsMinTwoWords, String> {
    @Override
    public boolean isValid(String string, ConstraintValidatorContext constraintValidatorContext) {
        if (string == null || string.isBlank()) {
            return true;
        }
        String[] words = string.trim().split("\\s+");
        return words.length>=2;
    }
}
