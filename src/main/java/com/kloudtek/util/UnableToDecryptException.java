/*
 * Copyright (c) 2014 Kloudtek Ltd
 */

package com.kloudtek.util;

/**
 * Thrown when failed to decrypt data using CryptoUtils functions
 */
public class UnableToDecryptException extends Exception {
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
