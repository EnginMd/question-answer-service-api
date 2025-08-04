package com.project.questapp.exception;

public class AuthenticationFailedException extends RuntimeException {

    public AuthenticationFailedException(){
        super();
    }

    public AuthenticationFailedException(String message){
        super(message);
    }
}
