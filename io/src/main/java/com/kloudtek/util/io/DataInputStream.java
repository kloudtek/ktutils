/*
 * Copyright (c) 2015 Kloudtek Ltd
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
    public static final int DEFAULT_MAX_LEN = 10240;

    public DataInputStream(InputStream in) {
        super(in);
    }

    public String readString() throws IOException {
        return readString(this);
    }

    public UUID readUUID() throws IOException {
        return readUUID(this);
    }

    public long readUnsignedNumber() throws IOException {
        long number = 0;
        int shift = 0;
        boolean more;
        do {
            int i = read();
            if (i == -1) {
                break;
            }
            more = (i & 128L) > 0;
            number = number | ((i & 127L) << shift);
            shift += 7;
        } while (more);
        return number;
    }

    /**
     * Create a new byte array of specified size, and read data using {@link java.io.DataInput#readFully(byte[])}
     *
     * @param dataLen new buffer size
     * @return data read
     * @throws IOException If an error occured reading the data
     */
    public byte[] readFully(int dataLen) throws IOException {
        return readFully(this, dataLen);
    }

    public byte[] readData() throws IOException {
        return readData(false, DEFAULT_MAX_LEN);
    }

    public byte[] readData(boolean nonNull) throws IOException {
        return readData(nonNull, DEFAULT_MAX_LEN);
    }

    public byte[] readData(boolean nonNull, int maxLen) throws IOException {
        byte[] bytes = readData(this, maxLen);
        if (bytes == null && nonNull) {
            throw new IOException("readData return null when expected not to");
        }
        return bytes;
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
        return readEnum(enumClass, readByte());
    }

    public <X> X readShortEnum(Class<X> enumClass) throws IOException {
        return readEnum(enumClass, readShort());
    }

    public <X> X readIntEnum(Class<X> enumClass) throws IOException {
        return readEnum(enumClass, readInt());
    }

    public <X> X readEnum(Class<X> enumClass) throws IOException {
        return readEnum(enumClass, (int) readUnsignedNumber());
    }

    public Boolean readNullableBoolean() throws IOException {
        if (readBoolean()) {
            return readBoolean();
        } else {
            return null;
        }
    }

    public Byte readNullableByte() throws IOException {
        if (readBoolean()) {
            return readByte();
        } else {
            return null;
        }
    }

    public Short readNullableShort() throws IOException {
        if (readBoolean()) {
            return readShort();
        } else {
            return null;
        }
    }

    public Integer readNullableInt() throws IOException {
        if (readBoolean()) {
            return readInt();
        } else {
            return null;
        }
    }

    public Long readNullableLong() throws IOException {
        if (readBoolean()) {
            return readLong();
        } else {
            return null;
        }
    }

    public Float readNullableFloat() throws IOException {
        if (readBoolean()) {
            return readFloat();
        } else {
            return null;
        }
    }

    public Double readNullableDouble() throws IOException {
        if (readBoolean()) {
            return readDouble();
        } else {
            return null;
        }
    }

    public static String readString(DataInput in) throws IOException {
        if (in.readBoolean()) {
            return in.readUTF();
        } else {
            return null;
        }
    }

    public static UUID readUUID(DataInput in) throws IOException {
        final long mostSigBits = in.readLong();
        final long leastSigBits = in.readLong();
        if (mostSigBits != 0 && leastSigBits != 0) {
            return new UUID(mostSigBits, leastSigBits);
        } else {
            return null;
        }
    }

    /**
     * Create a new byte array of specified size, and read data using {@link java.io.DataInput#readFully(byte[])}
     *
     * @param in      {@link java.io.DataInput} to read from
     * @param dataLen new buffer size
     * @return data read
     * @throws IOException If an error occured reading the data
     */
    public static byte[] readFully(DataInput in, int dataLen) throws IOException {
        byte[] data = new byte[dataLen];
        in.readFully(data);
        return data;
    }

    public static byte[] readData(DataInput in) throws IOException {
        return readData(in, DEFAULT_MAX_LEN);
    }

    public static byte[] readData(DataInput in, int maxLen) throws IOException {
        int len = in.readInt();
        if (len > maxLen) {
            throw new IOException("Data block larger than max " + maxLen + ": " + len);
        }
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
            list.add(readUUID(in));
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

    private static <X> X readEnum(Class<X> enumClass, int idx) throws IOException {
        try {
            return idx == 0 ? null : enumClass.getEnumConstants()[idx - 1];
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new IOException("Invalid enum value");
        }
    }
}
