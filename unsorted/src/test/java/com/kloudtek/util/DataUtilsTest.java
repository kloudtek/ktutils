/*
 * Copyright (c) 2014 Kloudtek Ltd
 */

package com.kloudtek.util;

import junit.framework.TestCase;
import org.testng.annotations.Test;

import java.util.UUID;

import static com.kloudtek.util.DataUtils.*;

public class DataUtilsTest extends TestCase {
    @Test
    public void testLongConversion() throws Exception {
        testLongConversion(Long.MIN_VALUE);
        testLongConversion(0L);
        testLongConversion(Long.MAX_VALUE);
    }

    private void testLongConversion(long val) throws Exception {
        long val2 = byteArrayToLong(longToByteArray(val));
        assertEquals(val, val2);
    }

    @Test
    public void testShortConversion() throws Exception {
        testShortConversion(Short.MIN_VALUE);
        testShortConversion((short) 0);
        testShortConversion(Short.MAX_VALUE);
    }

    private void testShortConversion(short val) throws Exception {
        short val2 = byteArrayToShort(shortToByteArray(val));
        assertEquals(val, val2);
    }

    @Test
    public void testUuidConversion() throws Exception {
        UUID uuid = UUID.randomUUID();
        UUID uuid2 = byteArrayToUuid(uuidToByteArray(uuid));
        assertEquals(uuid, uuid2);
    }
}