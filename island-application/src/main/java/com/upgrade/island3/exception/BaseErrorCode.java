package com.upgrade.island3.exception;

/**
 * BaseErrorCode
 *
 * @author Binyamin Pyanin
 * @since 20210212
 */
public interface BaseErrorCode {

    String getMessageKey();

    int getCode();

    String getName();

    String getResourceName();

}
