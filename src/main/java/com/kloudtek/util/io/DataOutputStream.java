/*
 * Copyright (c) 2014 Kloudtek Ltd
 */

package com.kloudtek.util.io;

import java.io.DataOutput;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Extends JDK DataOutputStream to provide some extra methods.
 */
public class DataOutputStream extends java.io.DataOutputStream {
    public DataOutputStream(OutputStream out) {
        super(out);
    }

    /**
     * Will write a boolean to indicate if the string is not null, and if it isn't then write the string using writeUTF
     *
     * @param str
     * @throws IOException
     */
    public void writeString(String str) throws IOException {
        writeString(this, str);
    }

    /**
     * Will first write the data lenght (or -1 if data null) and then write the data if non-null.
     *
     * @param data Data to write
     * @throws IOException
     */
    public void writeData(byte[] data) throws IOException {
        writeData(this, data);
    }

    public static void writeString(DataOutput out, String str) throws IOException {
        out.writeBoolean(str != null);
        if (str != null) {
            out.writeUTF(str);
        }
    }

    public static void writeData(DataOutput out, byte[] data) throws IOException {
        int len = data != null ? data.length : -1;
        out.writeInt(len);
        if (len > -1) {
            out.write(data);
        }
    }
}
