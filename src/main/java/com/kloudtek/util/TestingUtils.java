/*
 * Copyright (c) 2014 Kloudtek Ltd
 */

package com.kloudtek.util;

import java.util.HashSet;
import java.util.Random;

/**
 * Created by yannick on 18/02/2014.
 */
public class TestingUtils {
    /**
     * Randomly some one or more bytes in a byte array
     *
     * @param data   Original data
     * @param amount How many bytes to change
     * @return Data with byte(s) changed
     */
    public static byte[] corruptData(byte[] data, int amount) {
        byte[] copy = data.clone();
        Random rng = new Random();
        HashSet<Integer> offsets = new HashSet<Integer>();
        while (offsets.size() < amount) {
            offsets.add(rng.nextInt(copy.length - 1));
        }
        for (Integer offset : offsets) {
            copy[offset] = changeData(copy[offset]);
        }
        return copy;
    }

    /**
     * <p>Return a byte that is different than the provided byte</p>
     * <p>The exact algorithm used is to increment the value is smaller than the max value, or 0 otherwise</p>
     *
     * @param data Original byte
     * @return New byte that isn't the same as the original byte.
     */
    public static byte changeData(byte data) {
        data++;
        if (data > Byte.MAX_VALUE) {
            data = 0;
        }
        return data;
    }
}
