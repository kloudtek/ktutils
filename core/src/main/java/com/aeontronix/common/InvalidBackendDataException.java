package com.aeontronix.common;

/**
 * Thrown when invalid data was retrieved from a backend system
 */
public class InvalidBackendDataException extends Exception {
    public InvalidBackendDataException() {
    }

    public InvalidBackendDataException(String message) {
        super(message);
    }

    public InvalidBackendDataException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidBackendDataException(Throwable cause) {
        super(cause);
    }
}
