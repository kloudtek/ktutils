/*
 * Copyright (c) 2014 Kloudtek Ltd
 */

package com.kloudtek.util.dao;

/**
 * Base exception for all other DAO exceptions
 */
public class DAOException extends Exception {
    public DAOException() {
    }

    public DAOException(String message) {
        super(message);
    }

    public DAOException(String message, Throwable cause) {
        super(message, cause);
    }

    public DAOException(Throwable cause) {
        super(cause);
    }
}
