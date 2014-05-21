/*
 * Copyright (c) 2014 Kloudtek Ltd
 */

package com.kloudtek.util;

/**
 * This exception indicates that an application state (data in memory or some other kind of storage backend like a
 * database) is in an unexpected and invalid state
 */
public class InvalidStateException extends RuntimeException {
    public InvalidStateException() {
    }

    public InvalidStateException(String detailMessage) {
        super(detailMessage);
    }

    public InvalidStateException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public InvalidStateException(Throwable throwable) {
        super(throwable);
    }
}
