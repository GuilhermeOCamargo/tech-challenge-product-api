package com.fiap.techchallenge.productApi.domain.exceptions;

import org.springframework.http.HttpStatus;

public class DataInputException extends BaseHttpException{
    public DataInputException(String message) {
        super(HttpStatus.UNPROCESSABLE_ENTITY, message);
    }
}