/*
 * Copyright (c) 2014 Kloudtek Ltd
 */

package com.kloudtek.util;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.UUID;

/**
 * Data manipulation utilities
 */
public class DataUtils {
    public static long byteArrayToLong(byte[] data) {
        if (data == null || data.length != 8) {
            throw new IllegalArgumentException();
        }
        return (((long) data[0] << 56) +
                ((long) (data[1] & 255) << 48) +
                ((long) (data[2] & 255) << 40) +
                ((long) (data[3] & 255) << 32) +
                ((long) (data[4] & 255) << 24) +
                ((data[5] & 255) << 16) +
                ((data[6] & 255) << 8) +
                ((data[7] & 255)));
    }

    public static byte[] longToByteArray(long value) {
        byte[] data = new byte[8];
        data[0] = (byte) (value >>> 56);
        data[1] = (byte) (value >>> 48);
        data[2] = (byte) (value >>> 40);
        data[3] = (byte) (value >>> 32);
        data[4] = (byte) (value >>> 24);
        data[5] = (byte) (value >>> 16);
        data[6] = (byte) (value >>> 8);
        data[7] = (byte) (value);
        return data;
    }

    public static short byteArrayToShort(byte[] bytes) {
        if (bytes.length != 2) {
            throw new IllegalArgumentException();
        }
        return ByteBuffer.wrap(bytes).order(ByteOrder.LITTLE_ENDIAN).getShort();
    }

    public static byte[] shortToByteArray(short value) {
        byte[] returnByteArray = new byte[2];
        returnByteArray[0] = (byte) (value & 0xff);
        returnByteArray[1] = (byte) ((value >>> 8) & 0xff);
        return returnByteArray;
    }

    public static byte[] uuidToByteArray(UUID uuid) {
        long b1 = uuid.getMostSignificantBits();
        long b2 = uuid.getLeastSignificantBits();
        return new byte[]{
                (byte) (b1 >>> 56),
                (byte) (b1 >>> 48),
                (byte) (b1 >>> 40),
                (byte) (b1 >>> 32),
                (byte) (b1 >>> 24),
                (byte) (b1 >>> 16),
                (byte) (b1 >>> 8),
                (byte) b1,
                (byte) (b2 >>> 56),
                (byte) (b2 >>> 48),
                (byte) (b2 >>> 40),
                (byte) (b2 >>> 32),
                (byte) (b2 >>> 24),
                (byte) (b2 >>> 16),
                (byte) (b2 >>> 8),
                (byte) b2
        };
    }

    public static UUID byteArrayToUuid(byte[] data) {
        long m = (((long) data[0] << 56) +
                ((long) (data[1] & 255) << 48) +
                ((long) (data[2] & 255) << 40) +
                ((long) (data[3] & 255) << 32) +
                ((long) (data[4] & 255) << 24) +
                ((data[5] & 255) << 16) +
                ((data[6] & 255) << 8) +
                ((data[7] & 255)));
        long l = (((long) data[8] << 56) +
                ((long) (data[9] & 255) << 48) +
                ((long) (data[10] & 255) << 40) +
                ((long) (data[11] & 255) << 32) +
                ((long) (data[12] & 255) << 24) +
                ((data[13] & 255) << 16) +
                ((data[14] & 255) << 8) +
                ((data[15] & 255)));
        return new UUID(m, l);
    }
}
