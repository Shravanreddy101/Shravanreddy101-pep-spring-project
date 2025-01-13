package com.example.exception;

public class GeneralError extends RuntimeException{
    public GeneralError(String message){
        super(message);
    }
}
