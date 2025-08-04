package com.project.questapp.exception;

public class LikeNotFoundException extends RuntimeException{

    public LikeNotFoundException(){
        super();
    }

    public LikeNotFoundException(String message){
        super(message);
    }
}
