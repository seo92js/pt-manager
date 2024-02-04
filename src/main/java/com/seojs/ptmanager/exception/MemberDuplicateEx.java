package com.seojs.ptmanager.exception;

public class MemberDuplicateEx extends RuntimeException{
    public MemberDuplicateEx() {
        super();
    }

    public MemberDuplicateEx(String message) {
        super(message);
    }

    public MemberDuplicateEx(String message, Throwable cause) {
        super(message, cause);
    }

    public MemberDuplicateEx(Throwable cause) {
        super(cause);
    }

    protected MemberDuplicateEx(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
