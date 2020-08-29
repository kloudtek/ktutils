package com.aeontronix.common;

public class CircularDependencyException extends Exception {
    public CircularDependencyException() {
    }

    public CircularDependencyException(String message) {
        super(message);
    }

    public CircularDependencyException(String message, Throwable cause) {
        super(message, cause);
    }

    public CircularDependencyException(Throwable cause) {
        super(cause);
    }

    public CircularDependencyException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
