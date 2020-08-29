/*
 * Copyright (c) 2014 Kloudtek Ltd
 */

package com.aeontronix.common.io;

import org.jetbrains.annotations.NotNull;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * InputStream that counts how many bytes have been read
 */
public class CountingInputStream extends FilterInputStream {
    private int count;

    public CountingInputStream(@NotNull InputStream wrapped) {
        super(wrapped);
    }

    @Override
    public int read() throws IOException {
        int val = super.read();
        if (val != -1) {
            count++;
        }
        return val;
    }

    @Override
    public int read(byte[] b) throws IOException {
        return inc(super.read(b));
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        return inc(super.read(b, off, len));
    }

    @Override
    public long skip(long n) throws IOException {
        long skipped = super.skip(n);
        if (skipped > 0) {
            count += skipped;
        }
        return skipped;
    }

    @Override
    public boolean markSupported() {
        return false;
    }

    public int getCount() {
        return count;
    }

    public void resetCount() {
        count = 0;
    }

    private int inc(int bytesRead) {
        if (bytesRead > 0) {
            count += bytesRead;
        }
        return bytesRead;
    }
}
