package com.bws.userservice.exception;


import lombok.Getter;

@Getter
public class CreateFailedException extends Exception{

    private final String message;

    public CreateFailedException(){
        super();
        this.message = null;
    }

    public CreateFailedException(String message ){
        super();
        this.message = message;
    }

}
