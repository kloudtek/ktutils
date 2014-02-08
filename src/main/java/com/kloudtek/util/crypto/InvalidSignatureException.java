/*
 * Copyright (c) 2014 Kloudtek Ltd
 */

package com.kloudtek.util.crypto;

/**
 * Created by yannick on 03/01/2014.
 */
public class InvalidSignatureException extends Exception {
    public InvalidSignatureException() {
    }

    public InvalidSignatureException(String message) {
        super(message);
    }

    public InvalidSignatureException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidSignatureException(Throwable cause) {
        super(cause);
    }
}
