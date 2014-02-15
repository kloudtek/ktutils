/*
 * Copyright (c) 2014 Kloudtek Ltd
 */

package com.kloudtek.util.crypto;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.SecretKey;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.SignatureException;


public class CryptoEngineTest extends Assert {
    public static final byte[] DATA = "SOMEDATA".getBytes();

    @Test(dataProvider = "engines")
    public void testSimpleRSAEncryptDecrypt(CryptoEngine cryptoEngine) throws BadPaddingException, InvalidKeyException, IllegalBlockSizeException {
        KeyPair keyPair = cryptoEngine.generateRSA2048KeyPair();
        byte[] encrypted = cryptoEngine.rsaEncrypt(keyPair.getPublic(), DATA);
        byte[] decrypted = cryptoEngine.rsaDecrypt(keyPair.getPrivate(), encrypted);
        assertEquals(decrypted, DATA);
    }

    @Test(dataProvider = "engines")
    public void testSimpleAESEncryptDecrypt(CryptoEngine cryptoEngine) throws BadPaddingException, InvalidKeyException, IllegalBlockSizeException {
        SecretKey key = cryptoEngine.generateAesKey(128);
        byte[] encrypted = cryptoEngine.aesEncrypt(key, DATA);
        byte[] decrypted = cryptoEngine.aesDecrypt(key, encrypted);
        assertEquals(decrypted, DATA);
    }

    @Test(dataProvider = "engines")
    public void testSimpleRSASigningVerify(CryptoEngine cryptoEngine) throws SignatureException, InvalidKeyException {
        KeyPair keyPair = cryptoEngine.generateRSA2048KeyPair();
        byte[] signature = cryptoEngine.rsaSign(DigestAlgorithm.SHA256, keyPair.getPrivate(), DATA);
        cryptoEngine.rsaVerifySignature(DigestAlgorithm.SHA256, keyPair.getPublic(), DATA, signature);
    }

    @DataProvider(name = "engines")
    public Object[][] provideData() {
        return new Object[][]{
                {new JCECryptoEngine()}
        };
    }
}
