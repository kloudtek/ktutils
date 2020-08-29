/*
 * Copyright (c) 2014 Kloudtek Ltd
 */

package com.aeontronix.common.dao;

/**
 * Created by yannick on 01/11/2014.
 */
public class DataNotFoundException extends DAOException {
    public DataNotFoundException() {
    }

    public DataNotFoundException(String message) {
        super(message);
    }

    public DataNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataNotFoundException(Throwable cause) {
        super(cause);
    }
}
