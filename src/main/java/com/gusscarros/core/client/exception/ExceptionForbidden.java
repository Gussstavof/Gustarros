package com.gusscarros.core.client.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class ExceptionForbidden extends RuntimeException {

    public ExceptionForbidden(String message) {
        super(message);
    }
}
