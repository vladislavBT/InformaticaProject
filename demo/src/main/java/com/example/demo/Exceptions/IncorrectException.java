package com.example.demo.Exceptions;

public class IncorrectException extends RuntimeException{
    public IncorrectException() {
        super();
    }

    public IncorrectException(String message) {
        super(message);
    }
}
