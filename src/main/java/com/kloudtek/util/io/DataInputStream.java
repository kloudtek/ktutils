/*
 * Copyright (c) 2014 Kloudtek Ltd
 */

package com.kloudtek.util.io;

import java.io.DataInput;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

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


    public List<Long> readLongList() throws IOException {
        return readLongList(this);
    }

    public List<Integer> readIntList() throws IOException {
        return readIntList(this);
    }

    public List<String> readUTFList() throws IOException {
        return readUTFList(this);
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

    public static List<Long> readLongList(DataInput in) throws IOException {
        int nb = in.readInt();
        ArrayList<Long> list = new ArrayList<Long>(nb);
        for (int i = 0; i < nb; i++) {
            list.add(in.readLong());
        }
        return list;
    }

    public static List<Integer> readIntList(DataInput in) throws IOException {
        int nb = in.readInt();
        ArrayList<Integer> list = new ArrayList<Integer>(nb);
        for (int i = 0; i < nb; i++) {
            list.add(in.readInt());
        }
        return list;
    }

    public static List<String> readUTFList(DataInput in) throws IOException {
        int nb = in.readInt();
        ArrayList<String> list = new ArrayList<String>(nb);
        for (int i = 0; i < nb; i++) {
            list.add(in.readUTF());
        }
        return list;
    }
}
