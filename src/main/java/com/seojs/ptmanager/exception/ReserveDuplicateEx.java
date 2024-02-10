package com.seojs.ptmanager.exception;

public class ReserveDuplicateEx extends RuntimeException{
    public ReserveDuplicateEx() {
        super();
    }

    public ReserveDuplicateEx(String message) {
        super(message);
    }

    public ReserveDuplicateEx(String message, Throwable cause) {
        super(message, cause);
    }

    public ReserveDuplicateEx(Throwable cause) {
        super(cause);
    }

    protected ReserveDuplicateEx(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
