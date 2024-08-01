package com.bws.userservice.exception;

import lombok.Getter;

public class AccessDeniedException extends Exception{

    @Getter
    private final String message;

    public AccessDeniedException( ){
        super();
        this.message = null;
    }
    public AccessDeniedException(String message){
        super();
        this.message = message;
    }
}

