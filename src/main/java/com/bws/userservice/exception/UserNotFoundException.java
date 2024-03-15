package com.bws.userservice.exception;

import lombok.Getter;

public class UserNotFoundException extends Exception{

    @Getter
    private final String message;

    public UserNotFoundException(){
        super();
        this.message = null;
    }
    public UserNotFoundException(String message){
        super();
        this.message = message;
    }
}
