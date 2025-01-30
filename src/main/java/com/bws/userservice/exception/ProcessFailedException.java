package com.bws.userservice.exception;

import lombok.Getter;

public class ProcessFailedException extends Exception{

    @Getter
    private final String message;

    public ProcessFailedException(){
        super();
        this.message = null;
    }

    public ProcessFailedException(String message){
        super();
        this.message = message;
    }
}
