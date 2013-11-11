/*
 * Copyright (c) 2013 KloudTek Ltd
 */

package com.kloudtek.util.crypto;

import org.testng.annotations.Test;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.SecretKey;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;

import static com.kloudtek.util.crypto.DigestAlgorithm.SHA1;
import static com.kloudtek.util.crypto.SymmetricAlgorithm.AES;
import static org.testng.Assert.*;

public class CryptoUtilsTest {
    @Test
    public void testSSHA() {
        byte[] data = "HelloWorldThisIsADigestToEncode@#$gfms8i23o5m*T)6y891'".getBytes();
        byte[] digest = CryptoUtils.digest(data, SHA1);
        assertTrue(CryptoUtils.compareSaltedDigest(digest, data, SHA1));
        assertFalse(CryptoUtils.compareSaltedDigest(digest, "sfaiofdsadjiofsadjiosafjasfdo".getBytes(), SHA1));
    }

    @Test
    public void testSSHABase64() {
        String data = "HelloWorldThisIsADigestToEncode@#$gfms8i23o5m*T)6y891'";
        String digest = CryptoUtils.saltedB64Digest(data, SHA1);
        assertTrue(CryptoUtils.compareSaltedDigest(digest, data, SHA1));
        assertFalse(CryptoUtils.compareSaltedDigest(digest, "sfaiofdsadjiofsadjiosafjasfdo", SHA1));
    }

    @Test
    public void testCompareGeneratedBase64() {
        String value = "ASfdasfdfsdafsdajfsdaljfdslakjfsadkjf";
        String cryptedSaltedValue = CryptoUtils.saltedB64Digest(value, SHA1);
        assertTrue(CryptoUtils.compareSaltedDigest(cryptedSaltedValue, value, SHA1));
    }

    @Test
    public void createSHADigestFromStream() throws NoSuchAlgorithmException, IOException {
        byte[] value = "afdsfsdafasdafdsasfdsa".getBytes();
        byte[] digest = MessageDigest.getInstance("SHA-1").digest(value);
        assertEquals(CryptoUtils.digest(new ByteArrayInputStream(value), SHA1), digest);
    }

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
        byte[] data = new byte[1000000];
        new SecureRandom().nextBytes(data);
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
    public void testRSAKeyEncoding() throws NoSuchAlgorithmException, InvalidKeySpecException {
        KeyPair kp = KeyPairGenerator.getInstance("RSA").generateKeyPair();
        byte[] key = kp.getPublic().getEncoded();
        PublicKey pubKey = CryptoUtils.readRSAPublicKey(key);
        assertEquals(((RSAPublicKey) kp.getPublic()).getModulus(), ((RSAPublicKey) pubKey).getModulus());
    }
}
