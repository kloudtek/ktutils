/*
 * Copyright (c) 2014 Kloudtek Ltd
 */

package com.kloudtek.util;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Created by yannick on 18/02/2014.
 */
public class TestingUtilsTest extends Assert {
    public static final byte ZERO = 0;

    @Test
    public void testCorruptData() throws Exception {
        byte[] data = new byte[100];
        assertNotEquals(data, TestingUtils.corruptData(data, 1));
        assertNotEquals(data, TestingUtils.corruptData(data, 10));
    }

    @Test
    public void testChangeData() throws Exception {
        assertNotEquals(TestingUtils.changeData(Byte.MAX_VALUE), Byte.MAX_VALUE);
        assertNotEquals(TestingUtils.changeData(ZERO), ZERO);
    }
}
