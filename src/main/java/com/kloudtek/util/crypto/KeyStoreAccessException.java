package com.kloudtek.util.crypto;

/**
 * Created by yannick on 21/04/2014.
 */
public class KeyStoreAccessException extends Exception {
    public KeyStoreAccessException() {
    }

    public KeyStoreAccessException(String message) {
        super(message);
    }

    public KeyStoreAccessException(String message, Throwable cause) {
        super(message, cause);
    }

    public KeyStoreAccessException(Throwable cause) {
        super(cause);
    }
}
