/*
 * Copyright (c) 2013 KloudTek Ltd
 */

package com.kloudtek.util;

/**
 * Exception used to wrap unexpected exceptions that shouldn't have occurred.
 */
public class UnexpectedException extends RuntimeException {
    public UnexpectedException() {
    }

    public UnexpectedException(String message) {
        super(message);
    }

    public UnexpectedException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnexpectedException(Throwable cause) {
        super(cause);
    }
}
