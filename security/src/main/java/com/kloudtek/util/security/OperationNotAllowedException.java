/*
 * Copyright (c) 2014 Kloudtek Ltd
 */

package com.kloudtek.util.security;

/**
 * Thrown when attempting to execute an operation that isn't allowed
 */
public class OperationNotAllowedException extends Exception {
    public OperationNotAllowedException() {
    }

    public OperationNotAllowedException(String message) {
        super(message);
    }

    public OperationNotAllowedException(String message, Throwable cause) {
        super(message, cause);
    }

    public OperationNotAllowedException(Throwable cause) {
        super(cause);
    }
}
