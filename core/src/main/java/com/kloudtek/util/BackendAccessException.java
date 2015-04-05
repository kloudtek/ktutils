/*
 * Copyright (c) 2015 Kloudtek Ltd
 */

package com.kloudtek.util;

/**
 * Exception to indicate that an unexpected error occured while accessing a backend system (communication or system error)
 */
public class BackendAccessException extends RuntimeException {
    private static final long serialVersionUID = -3391930958801134924L;

    public BackendAccessException() {
    }

    public BackendAccessException(String message) {
        super(message);
    }

    public BackendAccessException(String message, Throwable cause) {
        super(message, cause);
    }

    public BackendAccessException(Throwable cause) {
        super(cause);
    }
}
