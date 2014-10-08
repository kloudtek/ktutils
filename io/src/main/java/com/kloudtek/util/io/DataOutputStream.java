/*
 * Copyright (c) 2014 Kloudtek Ltd
 */

package com.kloudtek.util.io;

import java.io.DataOutput;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.UUID;

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

    public void writeUUID(UUID uuid) throws IOException {
        writeUUID(this, uuid);
    }

    public void writeByteEnum(Enum<?> enumeration) throws IOException {
        writeByteEnum(this, enumeration);
    }

    public void writeShortEnum(Enum<?> enumeration) throws IOException {
        writeShortEnum(this, enumeration);
    }

    public void writeIntEnum(Enum<?> enumeration) throws IOException {
        writeIntEnum(this, enumeration);
    }

    public void writeLongList(List<Long> list) throws IOException {
        writeInt(list.size());
        for (Long val : list) {
            writeLong(val);
        }
    }

    public void writeIntList(List<Integer> list) throws IOException {
        writeInt(list.size());
        for (Integer val : list) {
            writeInt(val);
        }
    }

    public void writeUUIDList(List<UUID> list) throws IOException {
        writeInt(list.size());
        for (UUID val : list) {
            writeUUID(val);
        }
    }

    public void writeUTFList(List<String> list) throws IOException {
        writeInt(list.size());
        for (String val : list) {
            writeUTF(val);
        }
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

    public static void writeUUID(DataOutput out, UUID uuid) throws IOException {
        out.writeLong(uuid.getMostSignificantBits());
        out.writeLong(uuid.getLeastSignificantBits());
    }

    public static void writeByteEnum(DataOutput out, Enum<?> enumeration) throws IOException {
        out.writeByte(enumValue(enumeration));
    }

    public static void writeShortEnum(DataOutput out, Enum<?> enumeration) throws IOException {
        out.writeShort(enumValue(enumeration));
    }

    public static void writeIntEnum(DataOutput out, Enum<?> enumeration) throws IOException {
        out.writeInt(enumValue(enumeration));
    }

    protected static int enumValue(Enum<?> enumeration) {
        return enumeration != null ? enumeration.ordinal() : -1;
    }
}
