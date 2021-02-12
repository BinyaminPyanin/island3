package com.upgrade.island3.exception;

/**
 * IslandApplicationException
 *
 * @author Binyamin Pyanin
 * @since 20210212
 */

public class IslandApplicationException extends RuntimeException {
    private final BaseErrorCode errorCode;

    public IslandApplicationException(String message, BaseErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public BaseErrorCode getErrorCode() {
        return errorCode;
    }
}
