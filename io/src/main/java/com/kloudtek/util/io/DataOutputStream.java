/*
 * Copyright (c) 2015 Kloudtek Ltd
 */

package com.kloudtek.util.io;

import org.jetbrains.annotations.Nullable;

import java.io.DataOutput;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.UUID;

/**
 * Extends JDK DataOutputStream to provide some extra methods.
 */
public class DataOutputStream extends java.io.DataOutputStream {
    public static final long MAX = 9223372036854775807L;
    public static final long[] BOUNDS = new long[]{127L, 16383L, 2097151L, 268435455L, 34359738367L, 4398046511103L,
            562949953421311L, 72057594037927935L, 9223372036854775807L};

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

    public void writeUnsignedNumber(long number) throws IOException {
        if (number < 0) {
            throw new IllegalArgumentException("Number isn't unsigned");
        }
        int shift = 0;
        final int maxBounds = BOUNDS.length;
        for (int i = 0; i < maxBounds; i++) {
            long bound = BOUNDS[i];
            boolean more = number > bound;
            write((int) ((number & bound) >> shift) | (more ? 128 : 0));
            if (!more) {
                break;
            }
            shift += 7;
        }
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
        writeByte(enumValue(enumeration));
    }

    public void writeShortEnum(Enum<?> enumeration) throws IOException {
        writeShort(enumValue(enumeration));
    }

    public void writeIntEnum(Enum<?> enumeration) throws IOException {
        writeInt(enumValue(enumeration));
    }

    public void writeEnum(@Nullable Enum<?> enumeration) throws IOException {
        writeUnsignedNumber(enumValue(enumeration));
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

    public void writeNullableBoolean(Boolean value) throws IOException {
        final boolean isNotNull = value != null;
        writeBoolean(isNotNull);
        if (isNotNull) {
            writeBoolean(value);
        }
    }

    public void writeNullableByte(Byte value) throws IOException {
        final boolean isNotNull = value != null;
        writeBoolean(isNotNull);
        if (isNotNull) {
            writeByte(value);
        }
    }

    public void writeNullableShort(Short value) throws IOException {
        final boolean isNotNull = value != null;
        writeBoolean(isNotNull);
        if (isNotNull) {
            writeShort(value);
        }
    }

    public void writeNullableInt(Integer value) throws IOException {
        final boolean isNotNull = value != null;
        writeBoolean(isNotNull);
        if (isNotNull) {
            writeInt(value);
        }
    }

    public void writeNullableLong(Long value) throws IOException {
        final boolean isNotNull = value != null;
        writeBoolean(isNotNull);
        if (isNotNull) {
            writeLong(value);
        }
    }

    public void writeNullableFloat(Float value) throws IOException {
        final boolean isNotNull = value != null;
        writeBoolean(isNotNull);
        if (isNotNull) {
            writeFloat(value);
        }
    }

    public void writeNullableDouble(Float value) throws IOException {
        final boolean isNotNull = value != null;
        writeBoolean(isNotNull);
        if (isNotNull) {
            writeDouble(value);
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
        if (uuid != null) {
            out.writeLong(uuid.getMostSignificantBits());
            out.writeLong(uuid.getLeastSignificantBits());
        } else {
            out.writeLong(0);
            out.writeLong(0);
        }
    }

    protected static int enumValue(Enum<?> enumeration) {
        return enumeration != null ? enumeration.ordinal() + 1 : 0;
    }
}
