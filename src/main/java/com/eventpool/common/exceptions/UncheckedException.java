package com.eventpool.common.exceptions;

import static com.eventpool.common.exceptions.MessageFormatter.formatMessage;

public class UncheckedException extends RuntimeException {

    private String code;

    public UncheckedException(String code, Object... args) {
        super(formatMessage(code, args));
        this.code = code;
    }

    public UncheckedException(Throwable th, String code, Object... args) {
        super(formatMessage(code, args), th);
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
