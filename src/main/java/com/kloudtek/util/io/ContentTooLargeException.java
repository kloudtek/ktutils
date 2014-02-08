/*
 * Copyright (c) 2014 Kloudtek Ltd
 */

package com.kloudtek.util.io;

/**
 * Exception thrown when provided content is too large
 */
public class ContentTooLargeException extends Exception {
    public ContentTooLargeException() {
    }

    public ContentTooLargeException(String message) {
        super(message);
    }

    public ContentTooLargeException(String message, Throwable cause) {
        super(message, cause);
    }

    public ContentTooLargeException(Throwable cause) {
        super(cause);
    }
}
