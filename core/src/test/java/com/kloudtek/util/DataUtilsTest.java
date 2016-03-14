/*
 * Copyright (c) 2015 Kloudtek Ltd
 */

package com.kloudtek.util;

import org.testng.annotations.Test;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.util.UUID;

import static com.kloudtek.util.DataUtils.*;
import static org.testng.Assert.assertEquals;

public class DataUtilsTest {
    @Test
    public void testLongConversion() throws Exception {
        testLongConversion(Long.MIN_VALUE);
        testLongConversion(0L);
        testLongConversion(Long.MAX_VALUE);
    }

    private void testLongConversion(long val) throws Exception {
        ByteArrayOutputStream data = new ByteArrayOutputStream();
        DataOutputStream os = new DataOutputStream(data);
        os.writeLong(val);
        byte[] expected = data.toByteArray();
        byte[] actual = longToByteArray(val);
        assertEquals(expected, actual);
        assertEquals(val, byteArrayToLong(actual));
    }

    @Test
    public void testShortConversion() throws Exception {
        testShortConversion(Short.MIN_VALUE);
        testShortConversion((short) 0);
        testShortConversion(Short.MAX_VALUE);
    }

    private void testShortConversion(short val) throws Exception {
        ByteArrayOutputStream data = new ByteArrayOutputStream();
        DataOutputStream os = new DataOutputStream(data);
        os.writeShort(val);
        byte[] expected = data.toByteArray();
        byte[] actual = shortToByteArray(val);
        assertEquals(expected, actual);
        assertEquals(val, byteArrayToShort(actual));
    }

    @Test
    public void testIntConversion() throws Exception {
        testIntConversion(Integer.MIN_VALUE);
        testIntConversion(0);
        testIntConversion(Integer.MAX_VALUE);
    }

    private void testIntConversion(int val) throws Exception {
        ByteArrayOutputStream data = new ByteArrayOutputStream();
        DataOutputStream os = new DataOutputStream(data);
        os.writeInt(val);
        byte[] expected = data.toByteArray();
        byte[] actual = intToByteArray(val);
        assertEquals(expected, actual);
        assertEquals(val, byteArrayToInt(actual));
    }

    @Test
    public void testUuidConversion() throws Exception {
        UUID uuid = UUID.randomUUID();
        UUID uuid2 = byteArrayToUuid(uuidToByteArray(uuid));
        assertEquals(uuid, uuid2);
    }

    @Test
    public void testB32UuidConversion() throws Exception {
        UUID uuid = UUID.randomUUID();
        String base32Uuid = DataUtils.uuidToB32Str(uuid);
        UUID uuid2 = b32StrToUuid(base32Uuid);
        assertEquals(uuid, uuid2);
    }
}