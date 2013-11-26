/*
 * Copyright (c) 2013 KloudTek Ltd
 */

package com.kloudtek.util.io;

import java.io.IOException;
import java.io.InputStream;

/**
 * Extends JDK DataInputStream to provide some extra methods.
 */
public class DataInputStream extends java.io.DataInputStream {
    public DataInputStream(InputStream in) {
        super(in);
    }

    public String readString() throws IOException {
        if (readBoolean()) {
            return readUTF();
        } else {
            return null;
        }
    }

    public byte[] readData() throws IOException {
        int len = readInt();
        if (len > 0) {
            byte[] data = new byte[len];
            readFully(data);
            return data;
        } else if (len == 0) {
            return new byte[0];
        } else {
            return null;
        }
    }
}
