/*
 * Copyright (c) 2014 Kloudtek Ltd
 */

package com.kloudtek.util.io;

import java.io.DataInput;
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
        return readString(this);
    }

    public byte[] readData() throws IOException {
        return readData(this);
    }

    public static String readString(DataInput in) throws IOException {
        if (in.readBoolean()) {
            return in.readUTF();
        } else {
            return null;
        }
    }

    public static byte[] readData(DataInput in) throws IOException {
        int len = in.readInt();
        if (len > 0) {
            byte[] data = new byte[len];
            in.readFully(data);
            return data;
        } else if (len == 0) {
            return new byte[0];
        } else {
            return null;
        }
    }
}
