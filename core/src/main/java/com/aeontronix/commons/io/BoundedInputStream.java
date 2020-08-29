/*
 * Copyright (c) 2014 Kloudtek Ltd
 */

package com.aeontronix.commons.io;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * This stream puts a limit on how much data can be read from a wrapped stream.
 */
public class BoundedInputStream extends FilterInputStream {
    private final long maxLen;
    private final boolean failOnTooMuchData;
    private long count = 0;
    private long mark;

    /**
     * Stream constructor
     *
     * @param in                Wrapped stream
     * @param maxLen            Maximum data that can be read from wrapped stream (must be greater than 0).
     * @param failOnTooMuchData If set to true, will throw a {@link DataLenghtLimitException} if it's possible read more data
     *                          than the maxLen
     */
    public BoundedInputStream(InputStream in, long maxLen, boolean failOnTooMuchData) {
        super(in);
        if (maxLen <= 0) {
            throw new IllegalArgumentException("maxLen smaller than 1");
        }
        this.maxLen = maxLen;
        this.failOnTooMuchData = failOnTooMuchData;
    }

    @Override
    public int read() throws IOException {
        if (!failOnTooMuchData && count >= maxLen) {
            return -1;
        }
        int res = super.read();
        if (res != -1) {
            count++;
            if (failOnTooMuchData && count > maxLen) {
                throw new DataLenghtLimitException();
            }
        }
        return res;
    }

    @Override
    public int read(byte[] b) throws IOException {
        return read(b, 0, b.length);
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        if (!failOnTooMuchData && count >= maxLen) {
            return -1;
        }
        int dataRead = super.read(b, off, failOnTooMuchData ? len : (int) Math.min(len, maxLen - count));
        if (dataRead > 0 && count + dataRead > maxLen && failOnTooMuchData) {
            throw new DataLenghtLimitException();
        }
        return dataRead;
    }

    @Override
    public long skip(long n) throws IOException {
        if (!failOnTooMuchData && count >= maxLen) {
            return -1;
        }
        long skip = super.skip(failOnTooMuchData ? n : (int) Math.min(n, maxLen - count));
        if (skip > 0 && count + skip > maxLen && failOnTooMuchData) {
            throw new DataLenghtLimitException();
        }
        return skip;
    }

    @Override
    public synchronized void mark(int readlimit) {
        super.mark(readlimit);
        mark = count;
    }

    @Override
    public synchronized void reset() throws IOException {
        super.reset();
        count = mark;
    }

    @Override
    public boolean markSupported() {
        return super.markSupported();
    }
}
