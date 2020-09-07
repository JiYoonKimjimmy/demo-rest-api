package com.demo.restapi.common.advice.exception;

public class PwdAuthFailException extends RuntimeException {

    public PwdAuthFailException(String message, Throwable t) {
        super(message, t);
    }

    public PwdAuthFailException(String message) {
        super(message);
    }

    public PwdAuthFailException() {
        super();
    }
}
