package com.bws.userservice.exception;

import lombok.Getter;

public class AuthFailedException extends Exception{

    @Getter
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
