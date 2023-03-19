package com.java.informaticsServices.crudExample.service.exception;

public class UserExistException extends RuntimeException{
    public UserExistException(Throwable cause) {
        super(cause);
    }

    public UserExistException(String message) {
        super(message);
    }

    public UserExistException(String message, Throwable cause) {
        super(message, cause);
    }
}
