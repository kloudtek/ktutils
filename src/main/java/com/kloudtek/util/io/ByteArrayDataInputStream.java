/*
 * Copyright (c) 2013 KloudTek Ltd
 */

package com.kloudtek.util.io;

import java.io.ByteArrayInputStream;

/**
 * Created by yannick on 13/11/13.
 */
public class ByteArrayDataInputStream extends DataInputStream {
    public ByteArrayDataInputStream(byte[] buf) {
        super(new ByteArrayInputStream(buf));
    }

    public ByteArrayDataInputStream(byte[] buf, int offset, int length) {
        super(new ByteArrayInputStream(buf, offset, length));
    }
}
