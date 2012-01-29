/*
 * Copyright (c) 2011. KloudTek Ltd
 */

package com.kloudtek.util;

@UserDisplayable
public class UserDisplayableException extends RuntimeException {
    public UserDisplayableException() {
    }

    public UserDisplayableException(String message) {
        super(message);
    }

    public UserDisplayableException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserDisplayableException(Throwable cause) {
        super(cause);
    }
}
