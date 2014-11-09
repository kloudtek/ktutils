/*
 * Copyright (c) 2014 Kloudtek Ltd
 */

package com.kloudtek.util.dao;

/**
 * Thrown when attempting to persist or read data that is invalid
 */
public class InvalidDataException extends DAOException {
    public InvalidDataException() {
    }

    public InvalidDataException(String message) {
        super(message);
    }

    public InvalidDataException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidDataException(Throwable cause) {
        super(cause);
    }
}
