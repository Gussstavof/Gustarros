package com.gusscarros.core.client.validation;

import com.gusscarros.core.client.dto.ClientPostDto;
import com.gusscarros.core.client.service.AgeService;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = AgeService.class)
public @interface AgeValidation {
    String message() default "Age invalid";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};


    String value() default "";
}
