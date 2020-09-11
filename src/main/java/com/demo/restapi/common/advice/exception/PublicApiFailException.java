package com.demo.restapi.common.advice.exception;

public class PublicApiFailException extends RuntimeException {

    public PublicApiFailException(String msg, Throwable t) {
        super(msg, t);
    }

    public PublicApiFailException(String msg) {
        super(msg);
    }

    public PublicApiFailException() {
        super();
    }
}
