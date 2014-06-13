/*
 * Copyright (c) 2014 Kloudtek Ltd
 */

package com.kloudtek.util;

import com.kloudtek.util.crypto.CryptoUtils;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Array;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.Arrays;

/**
 * Various array-related utilities
 */
@SuppressWarnings("unchecked")
public class ArrayUtils {
    /**
     * Concatenate an array with additional elements
     *
     * @param array1 Original array
     * @param array2 Elements to add to array
     * @return New array containing all specified elements
     */
    @NotNull
    public static <X extends Object> X[] concat(@NotNull X[] array1, @NotNull X... array2) {
        X[] result = (X[]) Array.newInstance(array1.getClass().getComponentType(), array1.length + array2.length);
        if (array1.length > 0) {
            System.arraycopy(array1, 0, result, 0, array1.length);
        }
        if (array2.length > 0) {
            System.arraycopy(array2, 0, result, array1.length, array2.length);
        }
        return result;
    }

    /**
     * Concatenate an array with additional elements
     *
     * @param array1 Original array
     * @param array2 Elements to add to array
     * @return New array containing all specified elements
     */
    @NotNull
    public static boolean[] concat(@NotNull boolean[] array1, @NotNull boolean... array2) {
        boolean[] result = new boolean[array1.length + array2.length];
        if (array1.length > 0) {
            System.arraycopy(array1, 0, result, 0, array1.length);
        }
        if (array2.length > 0) {
            System.arraycopy(array2, 0, result, array1.length, array2.length);
        }
        return result;
    }

    /**
     * Concatenate an array with additional elements
     *
     * @param array1 Original array
     * @param array2 Elements to add to array
     * @return New array containing all specified elements
     */
    @NotNull
    public static byte[] concat(@NotNull byte[] array1, @NotNull byte... array2) {
        byte[] result = new byte[array1.length + array2.length];
        if (array1.length > 0) {
            System.arraycopy(array1, 0, result, 0, array1.length);
        }
        if (array2.length > 0) {
            System.arraycopy(array2, 0, result, array1.length, array2.length);
        }
        return result;
    }

    /**
     * Concatenate an array with additional elements
     *
     * @param array1 Original array
     * @param array2 Elements to add to array
     * @return New array containing all specified elements
     */
    @NotNull
    public static short[] concat(@NotNull short[] array1, @NotNull short... array2) {
        short[] result = new short[array1.length + array2.length];
        if (array1.length > 0) {
            System.arraycopy(array1, 0, result, 0, array1.length);
        }
        if (array2.length > 0) {
            System.arraycopy(array2, 0, result, array1.length, array2.length);
        }
        return result;
    }

    /**
     * Concatenate an array with additional elements
     *
     * @param array1 Original array
     * @param array2 Elements to add to array
     * @return New array containing all specified elements
     */
    @NotNull
    public static int[] concat(@NotNull int[] array1, @NotNull int... array2) {
        int[] result = new int[array1.length + array2.length];
        if (array1.length > 0) {
            System.arraycopy(array1, 0, result, 0, array1.length);
        }
        if (array2.length > 0) {
            System.arraycopy(array2, 0, result, array1.length, array2.length);
        }
        return result;
    }

    /**
     * Concatenate an array with additional elements
     *
     * @param array1 Original array
     * @param array2 Elements to add to array
     * @return New array containing all specified elements
     */
    @NotNull
    public static long[] concat(@NotNull long[] array1, @NotNull long... array2) {
        long[] result = new long[array1.length + array2.length];
        if (array1.length > 0) {
            System.arraycopy(array1, 0, result, 0, array1.length);
        }
        if (array2.length > 0) {
            System.arraycopy(array2, 0, result, array1.length, array2.length);
        }
        return result;
    }

    /**
     * Concatenate an array with additional elements
     *
     * @param array1 Original array
     * @param array2 Elements to add to array
     * @return New array containing all specified elements
     */
    @NotNull
    public static float[] concat(@NotNull float[] array1, @NotNull float... array2) {
        float[] result = new float[array1.length + array2.length];
        if (array1.length > 0) {
            System.arraycopy(array1, 0, result, 0, array1.length);
        }
        if (array2.length > 0) {
            System.arraycopy(array2, 0, result, array1.length, array2.length);
        }
        return result;
    }

    /**
     * Concatenate an array with additional elements
     *
     * @param array1 Original array
     * @param array2 Elements to add to array
     * @return New array containing all specified elements
     */
    @NotNull
    public static double[] concat(@NotNull double[] array1, @NotNull double... array2) {
        double[] result = new double[array1.length + array2.length];
        if (array1.length > 0) {
            System.arraycopy(array1, 0, result, 0, array1.length);
        }
        if (array1.length > 0) {
            System.arraycopy(array2, 0, result, array1.length, array2.length);
        }
        return result;
    }

    /**
     * Concatenate an array with additional elements
     *
     * @param array1 Original array
     * @param array2 Elements to add to array
     * @return New array containing all specified elements
     */
    @NotNull
    public static char[] concat(@NotNull char[] array1, @NotNull char... array2) {
        char[] result = new char[array1.length + array2.length];
        if (array1.length > 0) {
            System.arraycopy(array1, 0, result, 0, array1.length);
        }
        if (array2.length > 0) {
            System.arraycopy(array2, 0, result, array1.length, array2.length);
        }
        return result;
    }

    /**
     * Concatenate multiple arrays
     *
     * @param arrays arrays to concatenate (elements of this array can be null)
     * @return New array containing all specified elements
     */
    @NotNull
    public static char[] concat(@NotNull char[]... arrays) {
        int len = 0;
        for (char[] array : arrays) {
            if (array != null) {
                len += array.length;
            }
        }
        char[] result = new char[len];
        int pos = 0;
        for (char[] array : arrays) {
            if (array != null) {
                System.arraycopy(array, 0, result, pos, array.length);
                pos += array.length;
            }
        }
        return result;
    }

    /**
     * Convert an array of characters to an array of bytes using UTF8 encoding.
     *
     * @param chars Array of characters
     * @return Byte array
     */
    @NotNull
    public static byte[] toBytes(@NotNull char[] chars) {
        CharBuffer charBuffer = CharBuffer.wrap(chars);
        ByteBuffer byteBuffer = Charset.forName("UTF-8").encode(charBuffer);
        byte[] bytes = Arrays.copyOfRange(byteBuffer.array(), byteBuffer.position(), byteBuffer.limit());
        CryptoUtils.zero(byteBuffer);
        return bytes;
    }

    /**
     * Convert an array of bytes to an array of chars using UTF8 encoding
     *
     * @param data Array of characters
     * @return Byte array
     */
    @NotNull
    public static char[] toChars(@NotNull byte[] data) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(data);
        CharBuffer charBuffer = Charset.forName("UTF-8").decode(byteBuffer);
        char[] chars = Arrays.copyOfRange(charBuffer.array(), charBuffer.position(), charBuffer.limit());
        CryptoUtils.zero(charBuffer);
        return chars;
    }

    /**
     * XOR each byte of both arrays, starting from the left
     *
     * @param b1 First byte array
     * @param b2 Second byte array
     * @return
     */
    @NotNull
    public static byte[] xor(@NotNull byte[] b1, @NotNull byte[] b2) {
        boolean b1Smallest = b1.length < b2.length;
        int smallest = b1Smallest ? b1.length : b2.length;
        byte[] result = new byte[b1Smallest ? b2.length : b1.length];
        for (int i = 0; i < smallest; i++) {
            result[i] = (byte) (b1[i] ^ b2[i]);
        }
        return result;
    }
}
