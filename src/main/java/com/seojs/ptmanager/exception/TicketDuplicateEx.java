package com.seojs.ptmanager.exception;

public class TicketDuplicateEx extends RuntimeException{
    public TicketDuplicateEx() {
        super();
    }

    public TicketDuplicateEx(String message) {
        super(message);
    }

    public TicketDuplicateEx(String message, Throwable cause) {
        super(message, cause);
    }

    public TicketDuplicateEx(Throwable cause) {
        super(cause);
    }

    protected TicketDuplicateEx(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
