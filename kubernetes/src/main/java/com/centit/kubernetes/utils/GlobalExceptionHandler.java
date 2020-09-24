package com.centit.kubernetes.utils;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


import io.kubernetes.client.openapi.ApiException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ApiException> ApiExceptionHandler(ApiException e){

        logger.error("API调用异常", e);

        ResponseEntity<ApiException> responseEntity = new ResponseEntity<>(e, HttpStatus.valueOf(e.getCode()));
        return responseEntity;
    }
}
