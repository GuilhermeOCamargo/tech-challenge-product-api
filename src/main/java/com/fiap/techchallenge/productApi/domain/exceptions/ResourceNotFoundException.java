package com.fiap.techchallenge.productApi.domain.exceptions;

import org.springframework.http.HttpStatus;

public class ResourceNotFoundException extends BaseHttpException{
    public ResourceNotFoundException(String message) {
        super(HttpStatus.NO_CONTENT, message);
    }
}
