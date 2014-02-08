/*
 * Copyright (c) 2014 Kloudtek Ltd
 */

package com.kloudtek.util.io;

import org.testng.annotations.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.fail;

/**
 * Tests {@link com.kloudtek.util.io.BoundedInputStream}
 */
public class BoundedInputStreamTest {
    private Random random = new Random();

    @Test
    public void testReadByteArrayGoodData() throws IOException {
        testReadByteArrayGoodData(true);
    }

    @Test
    public void testReadByteArrayGoodDataFailOnError() throws IOException {
        testReadByteArrayGoodData(false);
    }

    private void testReadByteArrayGoodData(boolean failOnError) throws IOException {
        byte[] data = generateData(10);
        BoundedInputStream in = createStream(data, failOnError, 10);
        byte[] buf = new byte[10];
        assertEqual(data, in, buf, 10);
        assertEquals(-1, in.read(new byte[10]));
    }

    @Test
    public void testReadByteArrayTooLongDataNoException() throws IOException {
        byte[] data = generateData(20);
        BoundedInputStream in = createStream(data, false, 10);
        byte[] buf = new byte[100];
        assertEqual(Arrays.copyOf(data, 10), in, buf, 10);
    }

    @Test(expectedExceptions = DataLenghtLimitException.class)
    public void testReadByteArrayTooLongDataWithException() throws IOException {
        byte[] data = generateData(20);
        BoundedInputStream in = createStream(data, true, 10);
        byte[] buf = new byte[100];
        in.read(buf);
    }

    @Test
    public void testReadByteGoodData() throws IOException {
        testReadByteGoodDataFailOnError(false);
    }

    @Test
    public void testReadByteGoodDataFailOnError() throws IOException {
        testReadByteGoodDataFailOnError(true);
    }

    private void testReadByteGoodDataFailOnError(boolean failOnError) throws IOException {
        byte[] data = new byte[]{33};
        BoundedInputStream in = createStream(data, failOnError, 1);
        assertEquals(in.read(), 33);
        assertEquals(in.read(), -1);
    }

    @Test
    public void testReadByteGoodDataWithException() throws IOException {
        byte[] data = new byte[]{33};
        BoundedInputStream in = createStream(data, true, 1);
        assertEquals(in.read(), 33);
        assertEquals(in.read(), -1);
    }

    @Test
    public void testReadByteLongDataNoFail() throws IOException {
        byte[] data = new byte[]{33, 55};
        BoundedInputStream in = createStream(data, false, 1);
        assertEquals(in.read(), 33);
        assertEquals(in.read(), -1);
    }

    @Test
    public void testReadByteLongDataFail() throws IOException {
        byte[] data = new byte[]{33, 55};
        BoundedInputStream in = createStream(data, true, 1);
        assertEquals(in.read(), 33);
        try {
            assertEquals(in.read(), -1);
            fail();
        } catch (DataLenghtLimitException e) {
        }
    }

    private BoundedInputStream createStream(byte[] data, boolean failOnTooMuchData, int size) {
        ByteArrayInputStream oin = new ByteArrayInputStream(data);
        return new BoundedInputStream(oin, size, failOnTooMuchData);
    }

    public byte[] generateData(int len) {
        byte[] data = new byte[len];
        random.nextBytes(data);
        return data;
    }

    private void assertEqual(byte[] data, BoundedInputStream in, byte[] buf, int size) throws IOException {
        assertEquals(in.read(buf), size);
        assertEquals(data, Arrays.copyOf(buf, 10));
    }
}
