package com.seojs.ptmanager.exception;

public class TicketNotFoundEx extends RuntimeException{
    public TicketNotFoundEx() {
        super();
    }

    public TicketNotFoundEx(String message) {
        super(message);
    }

    public TicketNotFoundEx(String message, Throwable cause) {
        super(message, cause);
    }

    public TicketNotFoundEx(Throwable cause) {
        super(cause);
    }

    protected TicketNotFoundEx(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
