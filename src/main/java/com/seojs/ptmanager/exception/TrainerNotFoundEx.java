package com.seojs.ptmanager.exception;

public class TrainerNotFoundEx extends RuntimeException{
    public TrainerNotFoundEx() {
        super();
    }

    public TrainerNotFoundEx(String message) {
        super(message);
    }

    public TrainerNotFoundEx(String message, Throwable cause) {
        super(message, cause);
    }

    public TrainerNotFoundEx(Throwable cause) {
        super(cause);
    }

    protected TrainerNotFoundEx(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
