/*
 * Copyright (c) 2014 Kloudtek Ltd
 */

package com.kloudtek.util.dao;

/**
 * Exception thrown when attempting to persist previous loaded data which has been since modified on the backend
 */
public class DataHasChangedException extends DAOException {
    public DataHasChangedException() {
    }

    public DataHasChangedException(String message) {
        super(message);
    }

    public DataHasChangedException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataHasChangedException(Throwable cause) {
        super(cause);
    }
}
