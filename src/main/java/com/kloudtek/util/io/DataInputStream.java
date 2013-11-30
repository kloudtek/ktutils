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
        return IOUtils.readString(this);
    }

    public byte[] readData() throws IOException {
        return IOUtils.readData(this);
    }
}
