package com.auth.app.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class GlobalException extends RuntimeException {

    private final int applicationCode;
    private final HttpStatus httpStatus;

    public GlobalException(String message, int applicationCode, HttpStatus httpStatus) {
        super(message);
        this.applicationCode = applicationCode;
        this.httpStatus = httpStatus;
    }
}
