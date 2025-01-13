package com.example.exception;

public class DuplicateUsername extends RuntimeException {
    public DuplicateUsername(String message){
        super(message);
    }
}
