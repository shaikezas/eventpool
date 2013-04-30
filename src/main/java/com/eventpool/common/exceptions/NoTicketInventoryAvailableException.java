package com.eventpool.common.exceptions;

import static com.eventpool.common.exceptions.MessageFormatter.formatMessage;

public class NoTicketInventoryAvailableException extends RuntimeException {

    private String code;

    public NoTicketInventoryAvailableException(String code, Object... args) {
        super(formatMessage(code, args));
        this.code = code;
    }

    public NoTicketInventoryAvailableException(Throwable th, String code, Object... args) {
        super(formatMessage(code, args), th);
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
