/*
 * Copyright (c) Kloudtek Ltd 2013.
 */

package com.kloudtek.util;

import org.testng.annotations.Test;

import java.security.SecureRandom;

import static org.testng.Assert.assertEquals;

public class Base64Test {
    @Test
    public void testBase64() {
        byte[] data = new byte[2048];
        new SecureRandom().nextBytes(data);
        Base64.setType(Base64.Type.JDK);
        String jdkEncoded = Base64.encode(data);
        Base64.setType(Base64.Type.APACHECOMMONS);
        String ccEncoded = Base64.encode(data);

        Base64.setType(Base64.Type.APACHECOMMONS);
        byte[] ccDecodeJdk = Base64.decode(jdkEncoded);
        Base64.setType(Base64.Type.JDK);
        byte[] jdkDecodeCc = Base64.decode(ccEncoded);

        assertEquals(jdkEncoded, ccEncoded);
        assertEquals(data, ccDecodeJdk);
        assertEquals(data, jdkDecodeCc);
    }
}
