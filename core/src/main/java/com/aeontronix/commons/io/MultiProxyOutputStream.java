/*
 * Copyright (c) 2014 Kloudtek Ltd
 */

package com.aeontronix.commons.io;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Proxies data to one or more stream (all streams receive the sam
 */
public class MultiProxyOutputStream extends OutputStream {
    private OutputStream[] out;

    public MultiProxyOutputStream(OutputStream... streams) {
        this.out = streams;
    }

    @Override
    public void write(int oneByte) throws IOException {
        for (int i = 0; i < out.length; i++) {
            out[i].write(oneByte);
        }
    }

    @Override
    public void write(byte[] buffer) throws IOException {
        for (int i = 0; i < out.length; i++) {
            out[i].write(buffer);
        }
    }

    @Override
    public void write(byte[] buffer, int offset, int count) throws IOException {
        for (int i = 0; i < out.length; i++) {
            out[i].write(buffer, offset, count);
        }
    }

    @Override
    public void flush() throws IOException {
        for (int i = 0; i < out.length; i++) {
            out[i].flush();
        }
    }

    @Override
    public void close() throws IOException {
        for (int i = 0; i < out.length; i++) {
            out[i].close();
        }
    }
}
