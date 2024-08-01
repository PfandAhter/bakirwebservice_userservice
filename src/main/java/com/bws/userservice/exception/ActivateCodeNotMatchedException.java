package com.bws.userservice.exception;

import lombok.Getter;

public class ActivateCodeNotMatchedException extends Exception{

    @Getter
    private String message;

    public ActivateCodeNotMatchedException(){
        super();
        this.message = null;
    }
    public ActivateCodeNotMatchedException(String message){
        super();
        this.message = message;
    }
}
