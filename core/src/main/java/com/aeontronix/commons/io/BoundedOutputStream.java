/*
 * Copyright (c) 2014 Kloudtek Ltd
 */

package com.aeontronix.commons.io;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * This stream puts a limit on how much data can be written through a wrapped stream.
 */
public class BoundedOutputStream extends FilterOutputStream {
    private final long maxLen;
    private final boolean failOnTooMuchData;
    private long count = 0;
    private long mark;

    /**
     * Stream constructor
     *
     * @param out               Wrapped stream
     * @param maxLen            Maximum data that can be read from wrapped stream (must be greater than 0).
     * @param failOnTooMuchData If set to true, will throw a {@link DataLenghtLimitException} if attempting to write more data
     *                          than the maxLen (otherwise it will just discard any extra data)
     */
    public BoundedOutputStream(OutputStream out, long maxLen, boolean failOnTooMuchData) {
        super(out);
        if (maxLen <= 0) {
            throw new IllegalArgumentException("maxLen smaller than 1");
        }
        this.maxLen = maxLen;
        this.failOnTooMuchData = failOnTooMuchData;
    }

    @Override
    public void write(int b) throws IOException {
        if (checkWithinLimits(1)) {
            super.write(b);
            count++;
        }
    }

    @Override
    public void write(byte[] b) throws IOException {
        if (checkWithinLimits(b.length)) {
            super.write(b);
            count += b.length;
        }
    }

    @Override
    public void write(byte[] b, int off, int len) throws IOException {
        if (checkWithinLimits(len)) {
            super.write(b, off, len);
            count += len;
        }
    }

    private boolean checkWithinLimits(int size) throws DataLenghtLimitException {
        if ((count + size) > maxLen) {
            if (failOnTooMuchData) {
                throw new DataLenghtLimitException();
            } else {
                return false;
            }
        }
        return true;
    }
}
