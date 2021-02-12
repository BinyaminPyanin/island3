package com.upgrade.island3.exception;

/**
 * ErrorCode
 *
 * @author Binyamin Pyanin
 * @since 20210212
 */
public enum ErrorCode implements BaseErrorCode {

    EMPTY_PROPERTIES_FILE(1, "unknown");

    private final String messageKey;
    private final int code;

    ErrorCode(int code, String messageKey) {
        this.code = code;
        this.messageKey = messageKey;
    }

    @Override
    public String getMessageKey() {
        return messageKey;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getName() {
        return toString();
    }

    @Override
    public String getResourceName() {
        return "messages";
    }
}