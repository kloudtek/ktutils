/*
 * Copyright (c) 2014 Kloudtek Ltd
 */

package com.kloudtek.util.crypto;

import junit.framework.TestCase;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.SecretKey;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.SignatureException;
import java.util.Arrays;

/**
 * Created by yannick on 08/02/2014.
 */
public abstract class AbstractCryptoEngineTest extends TestCase {
    public static final byte[] DATA = "SOMEDATA".getBytes();
    private CryptoEngine provider;

    protected AbstractCryptoEngineTest(CryptoEngine provider) {
        this.provider = provider;
    }

    protected AbstractCryptoEngineTest(String name, CryptoEngine provider) {
        super(name);
        this.provider = provider;
    }

    public void testSimpleRSAEncryptDecrypt() throws BadPaddingException, InvalidKeyException, IllegalBlockSizeException {
        KeyPair keyPair = provider.generateRSA2048KeyPair();
        byte[] encrypted = provider.rsaEncrypt(keyPair.getPublic(), DATA);
        byte[] decrypted = provider.rsaDecrypt(keyPair.getPrivate(), encrypted);
        assertTrue(Arrays.equals(DATA, decrypted));
    }

    public void testSimpleAESEncryptDecrypt() throws BadPaddingException, InvalidKeyException, IllegalBlockSizeException {
        SecretKey key = provider.generateAesKey(128);
        byte[] encrypted = provider.aesEncrypt(key, DATA);
        byte[] decrypted = provider.aesDecrypt(key, encrypted);
        assertTrue(Arrays.equals(DATA, decrypted));
    }

    public void testSimpleRSASigningVerify() throws SignatureException, InvalidKeyException {
        KeyPair keyPair = provider.generateRSA2048KeyPair();
        byte[] signature = provider.rsaSign(DigestAlgorithm.SHA256, keyPair.getPrivate(), DATA);
        provider.rsaVerifySignature(DigestAlgorithm.SHA256, keyPair.getPublic(), DATA, signature);
    }
}
