/*
 * Copyright (c) 2011. KloudTek Ltd
 */

package com.kloudtek.util;

import org.testng.annotations.Test;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class CryptoUtilsTest {
    @Test
    public void testSSHA() {
        byte[] data = "HelloWorldThisIsADigestToEncode@#$gfms8i23o5m*T)6y891'".getBytes();
        byte[] digest = CryptoUtils.createSSHADigest(data);
        assertTrue(CryptoUtils.compareSSHADigest(digest, data));
        assertFalse(CryptoUtils.compareSSHADigest(digest, "sfaiofdsadjiofsadjiosafjasfdo".getBytes()));
    }

    @Test
    public void testSSHABase64() {
        String data = "HelloWorldThisIsADigestToEncode@#$gfms8i23o5m*T)6y891'";
        String digest = CryptoUtils.createSSHAStrDigest(data);
        assertTrue(CryptoUtils.compareSSHADigest(digest, data));
        assertFalse(CryptoUtils.compareSSHADigest(digest, "sfaiofdsadjiofsadjiosafjasfdo"));
    }
}
