package com.seojs.ptmanager.exception;

public class AdminNotFoundEx extends RuntimeException{
    public AdminNotFoundEx() {
        super();
    }

    public AdminNotFoundEx(String message) {
        super(message);
    }

    public AdminNotFoundEx(String message, Throwable cause) {
        super(message, cause);
    }

    public AdminNotFoundEx(Throwable cause) {
        super(cause);
    }

    protected AdminNotFoundEx(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
