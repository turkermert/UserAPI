package com.example.demo.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.UUID;

public class UUIDCheckerValidator implements ConstraintValidator<UuidCheckerConstraint, UUID> {
    private static final String REGEX = "[0-9a-f]{8}-[0-9a-f]{4}-[4][0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}";

    public boolean isValid(UUID uuid, ConstraintValidatorContext context) {
        return uuid.toString().matches(REGEX);
    }
}
