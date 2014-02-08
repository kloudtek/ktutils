/*
 * Copyright (c) 2013 KloudTek Ltd
 */

package com.kloudtek.util;

import java.lang.reflect.Array;

/**
 * Various array-related utilities
 */
@SuppressWarnings("unchecked")
public class ArrayUtils {
    /**
     * Concatenate an array with additional elements
     * @param array1 Original array
     * @param array2 Elements to add to array
     * @return New array containing all specified elements
     */
    public static <X extends Object> X[] concat(X[] array1, X... array2) {
        X[] result = (X[]) Array.newInstance(array1.getClass().getComponentType(), array1.length + array2.length);
        System.arraycopy(array1, 0, result, 0, array1.length);
        System.arraycopy(array2, 0, result, array1.length, array2.length);
        return result;
    }

    /**
     * Concatenate an array with additional elements
     * @param array1 Original array
     * @param array2 Elements to add to array
     * @return New array containing all specified elements
     */
    public static boolean[] concat(boolean[] array1, boolean... array2) {
        boolean[] result = new boolean[array1.length + array2.length];
        System.arraycopy(array1, 0, result, 0, array1.length);
        System.arraycopy(array2, 0, result, array1.length, array2.length);
        return result;
    }

    /**
     * Concatenate an array with additional elements
     * @param array1 Original array
     * @param array2 Elements to add to array
     * @return New array containing all specified elements
     */
    public static byte[] concat(byte[] array1, byte... array2) {
        byte[] result = new byte[array1.length + array2.length];
        System.arraycopy(array1, 0, result, 0, array1.length);
        System.arraycopy(array2, 0, result, array1.length, array2.length);
        return result;
    }

    /**
     * Concatenate an array with additional elements
     * @param array1 Original array
     * @param array2 Elements to add to array
     * @return New array containing all specified elements
     */
    public static short[] concat(short[] array1, short... array2) {
        short[] result = new short[array1.length + array2.length];
        System.arraycopy(array1, 0, result, 0, array1.length);
        System.arraycopy(array2, 0, result, array1.length, array2.length);
        return result;
    }

    /**
     * Concatenate an array with additional elements
     * @param array1 Original array
     * @param array2 Elements to add to array
     * @return New array containing all specified elements
     */
    public static int[] concat(int[] array1, int... array2) {
        int[] result = new int[array1.length + array2.length];
        System.arraycopy(array1, 0, result, 0, array1.length);
        System.arraycopy(array2, 0, result, array1.length, array2.length);
        return result;
    }

    /**
     * Concatenate an array with additional elements
     * @param array1 Original array
     * @param array2 Elements to add to array
     * @return New array containing all specified elements
     */
    public static long[] concat(long[] array1, long... array2) {
        long[] result = new long[array1.length + array2.length];
        System.arraycopy(array1, 0, result, 0, array1.length);
        System.arraycopy(array2, 0, result, array1.length, array2.length);
        return result;
    }

    /**
     * Concatenate an array with additional elements
     * @param array1 Original array
     * @param array2 Elements to add to array
     * @return New array containing all specified elements
     */
    public static float[] concat(float[] array1, float... array2) {
        float[] result = new float[array1.length + array2.length];
        System.arraycopy(array1, 0, result, 0, array1.length);
        System.arraycopy(array2, 0, result, array1.length, array2.length);
        return result;
    }

    /**
     * Concatenate an array with additional elements
     * @param array1 Original array
     * @param array2 Elements to add to array
     * @return New array containing all specified elements
     */
    public static double[] concat(double[] array1, double... array2) {
        double[] result = new double[array1.length + array2.length];
        System.arraycopy(array1, 0, result, 0, array1.length);
        System.arraycopy(array2, 0, result, array1.length, array2.length);
        return result;
    }

    /**
     * Concatenate an array with additional elements
     * @param array1 Original array
     * @param array2 Elements to add to array
     * @return New array containing all specified elements
     */
    public static char[] concat(char[] array1, char... array2) {
        char[] result = new char[array1.length + array2.length];
        System.arraycopy(array1, 0, result, 0, array1.length);
        System.arraycopy(array2, 0, result, array1.length, array2.length);
        return result;
    }
}
