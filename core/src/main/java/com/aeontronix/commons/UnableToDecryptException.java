/*
 * Copyright (c) 2014 Kloudtek Ltd
 */

package com.aeontronix.commons;

/**
 * Thrown when failed to decrypt data using CryptoUtils functions
 */
public class UnableToDecryptException extends Exception {
    private static final long serialVersionUID = 8589768787330571654L;

    public UnableToDecryptException() {
    }

    public UnableToDecryptException(String message) {
        super(message);
    }

    public UnableToDecryptException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnableToDecryptException(Throwable cause) {
        super(cause);
    }
}
