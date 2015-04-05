/*
 * Copyright (c) 2015 Kloudtek Ltd
 */

package com.kloudtek.util.io;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class DataInputStreamTest {
    @DataProvider(name = "unsignedNumberProvider")
    public static Object[][] unsignedNumberProvider() {
        return new Object[][]{
                {0, 1}, {127, 1}, {128, 2}, {500, 2}, {16383, 2}, {16384, 3}, {2097151, 3}, {2097152, 4}, {268435455, 4},
                {268435456, 5}, {34359738367L, 5}, {34359738368L, 6}, {4398046511103L, 6}, {4398046511104L, 7},
                {562949953421311L, 7}, {562949953421312L, 8}, {72057594037927935L, 8}, {72057594037927936L, 9},
                {Long.MAX_VALUE, 9}
        };
    }

    @Test(dataProvider = "unsignedNumberProvider")
    public void testReadUnsignedNumber(long number, int expectedSize) throws Exception {
        ByteArrayDataOutputStream buf = new ByteArrayDataOutputStream();
        buf.writeUnsignedNumber(number);
        byte[] data = buf.toByteArray();
        Assert.assertEquals(data.length, expectedSize);
        ByteArrayDataInputStream is = new ByteArrayDataInputStream(data);
        Assert.assertEquals(is.readUnsignedNumber(), number);
    }
}