package com.kloudtek.util.io;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Arrays;

/**
 * Created by yannick on 29/4/16.
 */
public class CompressUtilsTest extends Assert {
    @Test
    public void testGzip() throws IOException {
        byte[] data = "testdata".getBytes();
        byte[] compressed = CompressUtils.gzip(data);
        assertFalse(Arrays.equals(data, compressed));
        byte[] decompressed = CompressUtils.gunzip(compressed);
        assertTrue(Arrays.equals(data, decompressed));
    }
}