/*
 * Copyright (c) 2014 Kloudtek Ltd
 */

package com.kloudtek.util.io;

import java.io.ByteArrayOutputStream;

/**
 * Combines a {@link DataOutputStream} with a {@link java.io.ByteArrayOutputStream}
 */
public class ByteArrayDataOutputStream extends DataOutputStream {
    public ByteArrayDataOutputStream() {
        super(new ByteArrayOutputStream());
    }

    public ByteArrayDataOutputStream(int size) {
        super(new ByteArrayOutputStream(size));
    }

    public byte[] toByteArray() {
        return ((ByteArrayOutputStream) out).toByteArray();
    }
}
