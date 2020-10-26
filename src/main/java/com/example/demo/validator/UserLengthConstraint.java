package com.example.demo.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UserLengthValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface UserLengthConstraint {
    String message() default "Length should be between 1 and 10";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
