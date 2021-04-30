package com.centit.kubernetes.handler;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.JSON;
import io.kubernetes.client.openapi.models.V1Status;

@RestControllerAdvice
public class GlobalExceptionHandler {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<V1Status> ApiExceptionHandler(ApiException e){

        logger.error("Kubernetes API调用异常", e);

        V1Status v1Status = new JSON().setLenientOnJson(true).deserialize(e.getResponseBody(), V1Status.class);
        
        ResponseEntity<V1Status> responseEntity = new ResponseEntity<>(v1Status ,HttpStatus.valueOf(e.getCode()));
        return responseEntity;
    }
}
