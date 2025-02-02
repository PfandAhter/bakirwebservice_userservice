package com.bws.userservice.exception;

import lombok.Getter;

@Getter
public class AuthFailedException extends Exception{

    private final String message;

    public AuthFailedException(){
        super();
        this.message = null;
    }
    public AuthFailedException(String message ){
        super();
        this.message = message;
    }

}
