/*
 * Copyright (c) 2013 KloudTek Ltd
 */

package com.kloudtek.util;

import com.kloudtek.util.crypto.DigestUtils;
import org.testng.annotations.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static com.kloudtek.util.crypto.DigestAlgorithm.SHA1;
import static org.testng.Assert.*;

public class DigestUtilsTest {
    @Test
    public void testSSHA() {
        byte[] data = "HelloWorldThisIsADigestToEncode@#$gfms8i23o5m*T)6y891'".getBytes();
        byte[] digest = DigestUtils.digest(data, SHA1);
        assertTrue(DigestUtils.compareSaltedDigest(digest, data, SHA1));
        assertFalse(DigestUtils.compareSaltedDigest(digest, "sfaiofdsadjiofsadjiosafjasfdo".getBytes(), SHA1));
    }

    @Test
    public void testSSHABase64() {
        String data = "HelloWorldThisIsADigestToEncode@#$gfms8i23o5m*T)6y891'";
        String digest = DigestUtils.saltedB64Digest(data, SHA1);
        assertTrue(DigestUtils.compareSaltedDigest(digest, data, SHA1));
        assertFalse(DigestUtils.compareSaltedDigest(digest, "sfaiofdsadjiofsadjiosafjasfdo", SHA1));
    }

    @Test
    public void testCompareGeneratedBase64() {
        String value = "ASfdasfdfsdafsdajfsdaljfdslakjfsadkjf";
        String cryptedSaltedValue = DigestUtils.saltedB64Digest(value, SHA1);
        assertTrue(DigestUtils.compareSaltedDigest(cryptedSaltedValue, value, SHA1));
    }

    @Test
    public void createSHADigestFromStream() throws NoSuchAlgorithmException, IOException {
        byte[] value = "afdsfsdafasdafdsasfdsa".getBytes();
        byte[] digest = MessageDigest.getInstance("SHA-1").digest(value);
        assertEquals(DigestUtils.digest(new ByteArrayInputStream(value), SHA1), digest);
    }
}
