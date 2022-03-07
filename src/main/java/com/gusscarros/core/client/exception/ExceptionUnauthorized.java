package com.gusscarros.core.client.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseBody
@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class ExceptionUnauthorized extends RuntimeException {

    public ExceptionUnauthorized(String message) {
        super(message);
    }
}
