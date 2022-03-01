package com.gusscarros.core.client.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseBody
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ExceptionBadRequest extends RuntimeException {

    public ExceptionBadRequest(String message) {
        super(message);
    }
}
