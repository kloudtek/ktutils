/*
 * Copyright (c) 2014 Kloudtek Ltd
 */

package com.aeontronix.commons;

/**
 * Exception used to wrap unexpected exceptions that shouldn't have occurred.
 */
public class UnexpectedException extends RuntimeException {
    private static final long serialVersionUID = 3849867336388232277L;

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
