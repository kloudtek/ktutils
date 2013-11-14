/*
 * Copyright (c) 2013 KloudTek Ltd
 */

package com.kloudtek.util.io;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

/**
 * Created by yannick on 13/11/13.
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
