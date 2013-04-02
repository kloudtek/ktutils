/*
 * Copyright (c) Kloudtek Ltd 2013.
 */

package com.kloudtek.util;

/**
 * Generic {@link UserDisplayable} runtime exception.
 */
@UserDisplayable
public class UserDisplayableException extends RuntimeException {
    private String errorCode;

    public UserDisplayableException() {
    }

    public UserDisplayableException(String message) {
        super(message);
    }

    public UserDisplayableException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public UserDisplayableException(String message, Throwable cause, String errorCode) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public UserDisplayableException(Throwable cause, String errorCode) {
        super(cause);
        this.errorCode = errorCode;
    }

    public UserDisplayableException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, String errorCode) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.errorCode = errorCode;
    }

    public UserDisplayableException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserDisplayableException(Throwable cause) {
        super(cause);
    }

    public UserDisplayableException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    /**
     * Return specified error code.
     *
     * @return Error Code or null is none is available.
     */
    public String getErrorCode() {
        return errorCode;
    }
}
