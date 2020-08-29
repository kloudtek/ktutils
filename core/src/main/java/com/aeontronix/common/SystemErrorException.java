/*
 * Copyright (c) 2014 Kloudtek Ltd
 */

package com.aeontronix.common;

/**
 * Generic exception used to hide the real reason for the error to the end user.
 */
@UserDisplayable
public class SystemErrorException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public SystemErrorException() {
    }

    public SystemErrorException(String message) {
        super(message);
    }
}
