/*
 * Copyright (c) 2013 KloudTek Ltd
 */

package com.kloudtek.util.io;

import java.io.ByteArrayInputStream;

/**
 * Combines a {@link DataInputStream} with a {@link java.io.ByteArrayInputStream}
 */
public class ByteArrayDataInputStream extends DataInputStream {
    public ByteArrayDataInputStream(byte[] buf) {
        super(new ByteArrayInputStream(buf));
    }

    public ByteArrayDataInputStream(byte[] buf, int offset, int length) {
        super(new ByteArrayInputStream(buf, offset, length));
    }
}
