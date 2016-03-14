/*
 * Copyright (c) 2015 Kloudtek Ltd
 */

package com.kloudtek.util;

import org.jetbrains.annotations.NotNull;

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
        return new byte[]{
                (byte) (value >>> 56),
                (byte) (value >>> 48),
                (byte) (value >>> 40),
                (byte) (value >>> 32),
                (byte) (value >>> 24),
                (byte) (value >>> 16),
                (byte) (value >>> 8),
                (byte) (value)
        };
    }

    public static short byteArrayToShort(byte[] data) {
        if (data.length != 2) {
            throw new IllegalArgumentException();
        }
        return (short) (
                ((data[0] & 255) << 8) +
                        ((data[1] & 255)));
    }

    public static byte[] shortToByteArray(short value) {
        byte[] returnByteArray = new byte[2];
        returnByteArray[0] = (byte) ((value >>> 8) & 0xff);
        returnByteArray[1] = (byte) (value & 0xff);
        return returnByteArray;
    }

    public static long byteArrayToInt(byte[] data) {
        if (data == null || data.length != 4) {
            throw new IllegalArgumentException();
        }
        return (((data[0] & 255) << 24) +
                ((data[1] & 255) << 16) +
                ((data[2] & 255) << 8) +
                ((data[3] & 255)));
    }

    public static byte[] intToByteArray(long value) {
        return new byte[]{
                (byte) (value >>> 24),
                (byte) (value >>> 16),
                (byte) (value >>> 8),
                (byte) value};
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
        if (data == null || data.length != 16) {
            throw new IllegalArgumentException();
        }
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

    /**
     * Convert a UUID to a base 32 string (without padding)
     * @param uuid UUID
     * @return base32 string for the uuid
     */
    @NotNull
    public static String toB32String(@NotNull UUID uuid) {
        return StringUtils.base32Encode(uuidToByteArray(uuid),true).replace("=","");
    }

    /**
     * Convert a UUID in base 32 string format into a {@link UUID} object
     * @param base32Uuid uuid in base 32 format
     * @return UUID object
     */
    @NotNull
    public static UUID b32StrToUuid( @NotNull  String base32Uuid ) {
        return byteArrayToUuid(StringUtils.base32Decode(base32Uuid));
    }
}
