/*
 * Copyright (c) 2014 Kloudtek Ltd
 */

package com.aeontronix.commons.io;

import java.io.ByteArrayOutputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * This stream is used to wrap another stream, and make a copy of all data read
 */
public class RecordingInputStream extends FilterInputStream {
    private ByteArrayOutputStream data = new ByteArrayOutputStream();
    private boolean recording;

    public RecordingInputStream(InputStream inputStream) {
        this(inputStream, true);
    }

    public RecordingInputStream(InputStream inputStream, boolean recording) {
        super(inputStream);
        this.recording = recording;
    }

    @Override
    public int read() throws IOException {
        int i = super.read();
        if (recording && i != -1) {
            data.write(i);
        }
        return i;
    }

    @Override
    public int read(byte[] b) throws IOException {
        int i = super.read(b);
        if (recording && i != -1) {
            data.write(b, 0, i);
        }
        return i;
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        int i = super.read(b, off, len);
        if (recording && i != -1) {
            data.write(b, off, i);
        }
        return i;
    }

    @Override
    public void close() throws IOException {
        super.close();
        data.close();
    }

    public byte[] getData() {
        return data.toByteArray();
    }

    public boolean isRecording() {
        return recording;
    }

    public void setRecording(boolean recording) {
        this.recording = recording;
    }
}
