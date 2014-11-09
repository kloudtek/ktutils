/*
 * Copyright (c) 2014 Kloudtek Ltd
 */

package com.kloudtek.util.dao;

/**
 * Thrown when an error accessing the backend storage (due to communication problem or backend system error)
 */
public class DataAccessException extends DAOException {
    public DataAccessException() {
    }

    public DataAccessException(String message) {
        super(message);
    }

    public DataAccessException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataAccessException(Throwable cause) {
        super(cause);
    }
}
