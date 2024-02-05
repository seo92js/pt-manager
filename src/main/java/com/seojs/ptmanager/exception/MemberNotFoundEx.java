package com.seojs.ptmanager.exception;

public class MemberNotFoundEx extends RuntimeException{
    public MemberNotFoundEx() {
        super();
    }

    public MemberNotFoundEx(String message) {
        super(message);
    }

    public MemberNotFoundEx(String message, Throwable cause) {
        super(message, cause);
    }

    public MemberNotFoundEx(Throwable cause) {
        super(cause);
    }

    protected MemberNotFoundEx(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
