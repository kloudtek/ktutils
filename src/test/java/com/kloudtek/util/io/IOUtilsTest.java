/*
 * Copyright (c) 2014 Kloudtek Ltd
 */

package com.kloudtek.util.io;

import com.kloudtek.util.DataUtils;
import org.junit.Test;
import org.testng.Assert;

/**
 * Created by yannick on 25/01/2014.
 */
public class IOUtilsTest extends Assert {
    @Test
    public void testByteArrayToLong() throws Exception {
        ByteArrayDataOutputStream expected = new ByteArrayDataOutputStream();
        expected.writeLong(Long.MAX_VALUE);
        assertEquals(DataUtils.byteArrayToLong(expected.toByteArray()), Long.MAX_VALUE);
    }

    @Test
    public void testLongToByteArray() throws Exception {
        ByteArrayDataOutputStream expected = new ByteArrayDataOutputStream();
        expected.writeLong(Long.MAX_VALUE);
        assertEquals(DataUtils.longToByteArray(Long.MAX_VALUE), expected.toByteArray());
    }
}
