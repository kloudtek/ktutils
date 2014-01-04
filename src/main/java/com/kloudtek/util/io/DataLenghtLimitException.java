package com.kloudtek.util.io;

import java.io.IOException;

/**
 * Created by yannick on 03/01/2014.
 */
public class DataLenghtLimitException extends IOException {
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
