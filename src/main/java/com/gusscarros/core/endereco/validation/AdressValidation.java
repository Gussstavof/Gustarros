package com.gusscarros.core.endereco.validation;

import com.gusscarros.core.client.service.AgeService;
import com.gusscarros.core.endereco.service.AdressService;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = AdressService.class)
public @interface AdressValidation {
    String message() default "CEP invalid";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String value() default "";
}
