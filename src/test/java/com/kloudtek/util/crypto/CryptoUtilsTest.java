/*
 * Copyright (c) 2014 Kloudtek Ltd
 */

package com.kloudtek.util.crypto;

import org.testng.annotations.Test;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.SecretKey;
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;

import static com.kloudtek.util.crypto.SymmetricAlgorithm.AES;
import static org.junit.Assert.assertTrue;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.fail;

/**
 * Created by yannick on 15/02/2014.
 */
public class CryptoUtilsTest {
    @Test
    public void testEncryptDecryptAES() throws UnsupportedEncodingException, BadPaddingException, InvalidKeyException, IllegalBlockSizeException {
        String data = "helloworld";
        SecretKey key = CryptoUtils.generateKey(AES, 128);
        byte[] encrypted = CryptoUtils.aesEncrypt(key, data.getBytes("UTF-8"));
        byte[] decrypted = CryptoUtils.aesDecrypt(key, encrypted);
        assertEquals(new String(decrypted), data);
    }

    @Test
    public void testEncryptDecryptAESLargeMsg() throws UnsupportedEncodingException, BadPaddingException, InvalidKeyException, IllegalBlockSizeException {
        byte[] data = getRandomData();
        SecretKey key = CryptoUtils.generateKey(AES, 128);
        byte[] encrypted = CryptoUtils.aesEncrypt(key, data);
        byte[] decrypted = CryptoUtils.aesDecrypt(key, encrypted);
        assertTrue(Arrays.equals(decrypted, data));
    }

    @Test
    public void testEncryptDecryptAESRawKeys() throws UnsupportedEncodingException, BadPaddingException, InvalidKeyException, IllegalBlockSizeException {
        String data = "helloworld";
        byte[] key = CryptoUtils.generateKey(AES, 128).getEncoded();
        byte[] encrypted = CryptoUtils.aesEncrypt(key, data.getBytes("UTF-8"));
        byte[] decrypted = CryptoUtils.aesDecrypt(key, encrypted);
        assertEquals(new String(decrypted), data);
    }

    @Test
    public void testRSAPublicKeyEncoding() throws NoSuchAlgorithmException, InvalidKeySpecException {
        KeyPair kp = KeyPairGenerator.getInstance("RSA").generateKeyPair();
        byte[] key = kp.getPublic().getEncoded();
        PublicKey pubKey = CryptoUtils.readRSAPublicKey(key);
        assertEquals(((RSAPublicKey) kp.getPublic()).getModulus(), ((RSAPublicKey) pubKey).getModulus());
    }

    @Test
    public void testRSAPrivateKeyEncoding() throws NoSuchAlgorithmException, InvalidKeySpecException {
        KeyPair kp = KeyPairGenerator.getInstance("RSA").generateKeyPair();
        byte[] key = kp.getPrivate().getEncoded();
        PrivateKey privateKey = CryptoUtils.readRSAPrivateKey(key);
        assertEquals(kp.getPrivate(), privateKey);
    }

    @Test
    public void testRSASign() throws NoSuchAlgorithmException, SignatureException, InvalidKeyException {
        KeyPair kp = KeyPairGenerator.getInstance("RSA").generateKeyPair();
        byte[] data = getRandomData();
        byte[] signature = CryptoUtils.rsaSign(DigestAlgorithm.SHA256, kp.getPrivate(), data);
        CryptoUtils.rsaVerifySignature(DigestAlgorithm.SHA256, kp.getPublic(), data, signature);
        scrambleByte(data);
        try {
            CryptoUtils.rsaVerifySignature(DigestAlgorithm.SHA256, kp.getPublic(), data, signature);
            fail("signature verification didn't fail");
        } catch (SignatureException e) {
            // Good
        }
    }

    @Test
    public void testRSAEncrypt() throws NoSuchAlgorithmException, SignatureException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        KeyPair kp = KeyPairGenerator.getInstance("RSA").generateKeyPair();
        byte[] data = getRandomData(80);
        byte[] encrypted = CryptoUtils.rsaEncrypt(kp.getPublic(), data);
        assertTrue(Arrays.equals(data, CryptoUtils.rsaDecrypt(kp.getPrivate(), encrypted)));
        data = getRandomData(32);
        encrypted = CryptoUtils.rsaEncrypt(kp.getPublic(), data);
        assertTrue(Arrays.equals(data, CryptoUtils.rsaDecrypt(kp.getPrivate(), encrypted)));
    }

    @Test
    public void testSplitKey() throws UnsupportedEncodingException {
        String data = "hello world";
        byte[][] keys = CryptoUtils.splitKey(data.getBytes("UTF-8"), 4);
        byte[] merged = CryptoUtils.mergeSplitKey(keys[0], keys[1], keys[2], keys[3]);
        assertEquals(new String(merged), data);
        merged = CryptoUtils.mergeSplitKey(keys[3], keys[0], keys[1], keys[2]);
        assertEquals(new String(merged), data);
        merged = CryptoUtils.mergeSplitKey(keys[3], keys[2], keys[1], keys[0]);
        assertEquals(new String(merged), data);
    }

    /**
     * Change a random byte of the specified data
     *
     * @param data Data to scramble
     */
    private void scrambleByte(byte[] data) {
        int idx = new SecureRandom().nextInt(data.length);
        data[idx] = (byte) (data[idx] - 1);
        if (data[idx] < 0) {
            data[idx] = 50;
        }
    }

    private static byte[] getRandomData() {
        return getRandomData(1000000);
    }

    private static byte[] getRandomData(int size) {
        byte[] data = new byte[size];
        new SecureRandom().nextBytes(data);
        return data;
    }
}
