package com.example.demo.Exceptions;

public class EmptyException extends RuntimeException{
    public EmptyException() {
        super();
    }

    public EmptyException(String message) {
        super(message);
    }
}
