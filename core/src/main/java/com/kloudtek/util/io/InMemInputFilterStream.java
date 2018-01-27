package com.kloudtek.util.io;

import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayInputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * This filter stream will load all the data in memory to allow transformations to be applied on the full data set, before making it available to the caller
 */
public abstract class InMemInputFilterStream extends FilterInputStream {
    private ByteArrayInputStream buf;

    public InMemInputFilterStream(InputStream in) {
        super(in);
    }

    @Override
    public int read() throws IOException {
        return load().read();
    }

    @Override
    public int read(@NotNull byte[] b) throws IOException {
        return load().read(b);
    }

    @Override
    public int read(@NotNull byte[] b, int off, int len) throws IOException {
        return load().read(b, off, len);
    }

    @Override
    public long skip(long n) throws IOException {
        return load().skip(n);
    }

    @Override
    public int available() throws IOException {
        return load().available();
    }

    @Override
    public void close() throws IOException {
        load().close();
    }

    @Override
    public synchronized void mark(int readlimit) {
        try {
            load().mark(readlimit);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(),e);
        }
    }

    @Override
    public synchronized void reset() throws IOException {
        load().reset();
    }

    @Override
    public boolean markSupported() {
        return true;
    }

    private ByteArrayInputStream load() throws IOException {
        if( buf == null ) {
            byte[] data = IOUtils.toByteArray(in);
            buf = new ByteArrayInputStream(transform(data));
        }
        return buf;
    }

    protected abstract byte[] transform(byte[] data);
}
