package com.example.demo.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;


@Documented
@Constraint(validatedBy = UUIDCheckerValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface UuidCheckerConstraint {
    String message() default "UUID is not correct form.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}