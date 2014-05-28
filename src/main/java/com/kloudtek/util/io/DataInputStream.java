/*
 * Copyright (c) 2014 Kloudtek Ltd
 */

package com.kloudtek.util.io;

import java.io.DataInput;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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

    public UUID readUUID() throws IOException {
        return readUUID(this);
    }

    public byte[] readData() throws IOException {
        return readData(this);
    }

    public List<Long> readLongList() throws IOException {
        return readLongList(this);
    }

    public List<UUID> readUUIDList() throws IOException {
        return readUUIDList(this);
    }

    public List<Integer> readIntList() throws IOException {
        return readIntList(this);
    }

    public List<String> readUTFList() throws IOException {
        return readUTFList(this);
    }

    public <X> X readByteEnum(Class<X> enumClass) throws IOException {
        return readByteEnum(enumClass, this);
    }

    public <X> X readShortEnum(Class<X> enumClass) throws IOException {
        return readShortEnum(enumClass, this);
    }

    public <X> X readIntEnum(Class<X> enumClass) throws IOException {
        return readIntEnum(enumClass, this);
    }

    public static String readString(DataInput in) throws IOException {
        if (in.readBoolean()) {
            return in.readUTF();
        } else {
            return null;
        }
    }

    public static UUID readUUID(DataInput in) throws IOException {
        return new UUID(in.readLong(), in.readLong());
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

    public static List<UUID> readUUIDList(DataInput in) throws IOException {
        int nb = in.readInt();
        ArrayList<UUID> list = new ArrayList<UUID>(nb);
        for (int i = 0; i < nb; i++) {
            list.add(new UUID(in.readLong(), in.readLong()));
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

    public static <X> X readByteEnum(Class<X> enumClass, DataInput in) throws IOException {
        return readEnum(enumClass, in.readByte());
    }

    public static <X> X readShortEnum(Class<X> enumClass, DataInput in) throws IOException {
        return readEnum(enumClass, in.readShort());
    }

    public static <X> X readIntEnum(Class<X> enumClass, DataInput in) throws IOException {
        return readEnum(enumClass, in.readInt());
    }

    private static <X> X readEnum(Class<X> enumClass, int idx) throws IOException {
        try {
            return enumClass.getEnumConstants()[idx];
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new IOException("Invalid enum value");
        }
    }
}
