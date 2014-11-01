/*
 * Copyright (c) 2014 Kloudtek Ltd
 */

package com.kloudtek.util.dao;

/**
 * Thrown when an error accessing the backend storage (due to communication problem or backend system error)
 */
public class DataAccessFailedException extends DAOException {
    public DataAccessFailedException() {
    }

    public DataAccessFailedException(String message) {
        super(message);
    }

    public DataAccessFailedException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataAccessFailedException(Throwable cause) {
        super(cause);
    }
}
