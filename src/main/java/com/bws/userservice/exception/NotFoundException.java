package com.bws.userservice.exception;

import lombok.Getter;

public class NotFoundException extends Exception {

    @Getter
    private final String message;

    public NotFoundException() {
        super();
        this.message = null;
    }

    public NotFoundException(String message) {
        super();
        this.message = message;
    }
}
