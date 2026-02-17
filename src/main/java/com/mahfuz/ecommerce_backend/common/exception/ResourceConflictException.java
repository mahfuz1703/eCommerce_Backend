package com.mahfuz.ecommerce_backend.common.exception;

public class ResourceConflictException extends RuntimeException {
    public ResourceConflictException(String message){
        super(message);
    }
}
