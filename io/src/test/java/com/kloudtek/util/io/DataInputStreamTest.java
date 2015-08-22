/*
 * Copyright (c) 2015 Kloudtek Ltd
 */

package com.kloudtek.util.io;

import com.kloudtek.util.UnexpectedException;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

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

    @Test
    public void testReadRemainingByteArray() throws IOException {
        final byte[] originalData = "dsfaj8owqjf98owmfos8jufsdmfvoilw3uj234rjrf8ewoirfjd.ksfmodilsfj".getBytes();
        final ByteArrayDataInputStream is = new ByteArrayDataInputStream(originalData);
        final byte[] data = DataInputStream.readRemainingData(is);
        Assert.assertEquals(originalData, data);
    }

    @Test
    public void testReadRemainingTrickyStream() throws IOException {
        final ByteArrayOutputStream v = new ByteArrayOutputStream();
        final java.io.DataInputStream mock = new java.io.DataInputStream(new InputStream() {
            private int avCount = 0;
            private int rCount = 0;
            private int frCount = 0;

            @Override
            public int available() throws IOException {
                switch (avCount++) {
                    case 0:
                        return 3;
                    case 1:
                        return 0;
                    case 2:
                        return 2;
                    case 3:
                        return 0;
                    default:
                        throw new UnexpectedException();
                }
            }

            @Override
            public int read() throws IOException {
                switch (rCount++) {
                    case 0:
                        v.write(9);
                        return 9;
                    case 1:
                        v.write(8);
                        return 8;
                    case 2:
                        v.write(7);
                        return 7;
                    case 3:
                        return -1;
                    default:
                        throw new UnexpectedException();
                }
            }

            @Override
            public int read(byte[] b, int off, int len) throws IOException {
                switch (frCount++) {
                    case 0:
                        writeData(b, new byte[]{1, 2, 3});
                        return 3;
                    case 1:
                        writeData(b, new byte[]{4, 5});
                        return 2;
                    default:
                        throw new UnexpectedException();
                }
            }

            private void writeData(byte[] byteArray, byte[] data) throws IOException {
                if (byteArray.length != data.length) {
                    throw new IllegalArgumentException("Expect array = " + byteArray.length);
                }
                System.arraycopy(data, 0, byteArray, 0, data.length);
                v.write(data);
            }
        });

        final byte[] data = DataInputStream.readRemainingData(mock);
        Assert.assertEquals(v.toByteArray(), data);
    }
}