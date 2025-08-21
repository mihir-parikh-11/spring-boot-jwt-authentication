package com.auth.app.exception.handler;

import com.auth.app.exception.GlobalException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(GlobalException.class)
    public ResponseEntity<Map<String, Object>> handleGlobalException(GlobalException exception) {
        Map<String, Object> map = new HashMap<>();
        map.put("error_message", exception.getMessage());
        map.put("application_code", exception.getApplicationCode());
        map.put("http_status", exception.getHttpStatus());
        return new ResponseEntity<>(map, exception.getHttpStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleException(Exception exception) {
        Map<String, Object> map = new HashMap<>();
        map.put("error_message", exception.getMessage());
        map.put("http_status", HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
    }
}
