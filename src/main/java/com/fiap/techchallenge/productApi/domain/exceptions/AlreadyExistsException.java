package com.fiap.techchallenge.productApi.domain.exceptions;

public class AlreadyExistsException extends RuntimeException{

    public AlreadyExistsException(String message) {
        super(message);
    }
}