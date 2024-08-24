package com.bws.userservice.exception;

import lombok.Getter;

public class NotMatchedException extends Exception{

    @Getter
    private final String message;

    public NotMatchedException() {
        super();
        this.message = null;
    }

    public NotMatchedException(String message) {
        super();
        this.message = message;
    }

}
