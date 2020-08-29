/*
 * Copyright (c) 2014 Kloudtek Ltd
 */

package com.aeontronix.commons.io;

import java.io.IOException;

/**
 * Created by yannick on 03/01/2014.
 */
public class DataLenghtLimitException extends IOException {
    private static final long serialVersionUID = -4072065874880438208L;

    public DataLenghtLimitException() {
    }

    public DataLenghtLimitException(String message) {
        super(message);
    }

    public DataLenghtLimitException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataLenghtLimitException(Throwable cause) {
        super(cause);
    }
}
