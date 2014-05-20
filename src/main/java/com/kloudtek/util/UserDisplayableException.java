/*
 * Copyright (c) 2014 Kloudtek Ltd
 */

package com.kloudtek.util;

/**
 * Generic {@link UserDisplayable} runtime exception.
 */
@UserDisplayable
public class UserDisplayableException extends RuntimeException {
    private String errorTitle;
    private String errorCode;

    public UserDisplayableException() {
    }

    public UserDisplayableException(String message) {
        super(message);
    }

    public UserDisplayableException(String message, String errorTitle) {
        super(message);
        this.errorTitle = errorTitle;
    }

    public UserDisplayableException(String message, Throwable cause, String errorTitle, String errorCode) {
        super(message, cause);
        this.errorTitle = errorTitle;
        this.errorCode = errorCode;
    }

    public UserDisplayableException(Throwable cause, String errorCode) {
        super(cause);
        this.errorCode = errorCode;
    }

    public UserDisplayableException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserDisplayableException(Throwable cause) {
        super(cause);
    }


    /**
     * Return specified error code.
     *
     * @return Error Code or null is none is available.
     */
    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorTitle() {
        return errorTitle;
    }
}
