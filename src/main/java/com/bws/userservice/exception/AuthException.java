package com.bws.userservice.exception;

import lombok.Getter;

public class AuthException extends Exception{

    @Getter
    private String message;

    public AuthException(){
        super();
        this.message = null;
    }
    public AuthException(String message){
        super();
        this.message = message;
    }
}
