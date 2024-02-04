package com.seojs.ptmanager.exception;

public class TrainerDuplicateEx extends RuntimeException{
    public TrainerDuplicateEx() {
        super();
    }

    public TrainerDuplicateEx(String message) {
        super(message);
    }

    public TrainerDuplicateEx(String message, Throwable cause) {
        super(message, cause);
    }

    public TrainerDuplicateEx(Throwable cause) {
        super(cause);
    }

    protected TrainerDuplicateEx(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
