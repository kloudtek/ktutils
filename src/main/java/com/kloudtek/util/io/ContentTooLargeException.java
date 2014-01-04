package com.kloudtek.util.io;

/**
 * Created by yannick on 03/01/2014.
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
