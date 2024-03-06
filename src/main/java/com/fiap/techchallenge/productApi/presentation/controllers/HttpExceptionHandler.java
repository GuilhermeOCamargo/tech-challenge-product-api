package com.fiap.techchallenge.productApi.presentation.controllers;

import com.fiap.techchallenge.productApi.domain.exceptions.BaseHttpException;
import com.fiap.techchallenge.productApi.presentation.dtos.ErrorResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class HttpExceptionHandler {

    @ExceptionHandler(BaseHttpException.class)
    public ResponseEntity<ErrorResponseDto> handleHttpException(BaseHttpException e) {
        return ResponseEntity.status(e.getStatusCode()).body(new ErrorResponseDto(e.getMessage()));
    }

}
