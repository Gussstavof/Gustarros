package com.gusscarros.core.client.constraints;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = AgeValidation.class)
public @interface AgeValidator {
    String message() default "Age invalid";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};


    String value() default "";
}
