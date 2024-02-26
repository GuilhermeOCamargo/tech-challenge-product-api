package com.fiap.techchallenge.productApi.domain.exceptions;

import org.springframework.http.HttpStatus;

public class ResourceAlreadyExists extends BaseHttpException{

    public ResourceAlreadyExists(String message) {
        super(HttpStatus.CONFLICT, message);
    }
}
