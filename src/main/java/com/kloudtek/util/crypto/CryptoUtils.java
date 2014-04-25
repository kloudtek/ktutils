/*
 * Copyright (c) 2014 Kloudtek Ltd
 */

package com.kloudtek.util.crypto;

import com.kloudtek.util.UnexpectedException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import java.security.*;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;

/**
 * Various cryptographic methods
 */
public class CryptoUtils {
    private static CryptoEngine provider = new JCECryptoEngine();

    /**
     * Generate a private key using a symmetric algorithm
     *
     * @param alg     Symmetric algorithm
     * @param keysize Key size
     * @return secret key
     */
    public static SecretKey generateKey(SymmetricAlgorithm alg, int keysize) {
        return provider.generateKey(alg, keysize);
    }

    /**
     * Generate RSA 4096bit key pair
     *
     * @return RSA Key pair
     */
    public static KeyPair generateRSA4096KeyPair() {
        return provider.generateRSA4096KeyPair();
    }

    /**
     * Generate RSA 4096bit key pair
     *
     * @return RSA Key pair
     */
    public static KeyPair generateRSA2048KeyPair() {
        return provider.generateRSA2048KeyPair();
    }

    /**
     * Generate HMAC key
     *
     * @param algorithm digest algorithm
     * @return HMAC key
     */
    public static SecretKey generateHmacKey(DigestAlgorithm algorithm) {
        return provider.generateHmacKey(algorithm);
    }

    /**
     * Read an X509 Encoded S_RSA public key
     *
     * @param key X509 encoded rsa key
     * @return Public key object
     * @throws java.security.spec.InvalidKeySpecException If the key is invalid
     */
    public static RSAPublicKey readRSAPublicKey(byte[] key) throws InvalidKeySpecException {
        return provider.readRSAPublicKey(key);
    }

    /**
     * Read a PKCS8 Encoded S_RSA private key
     *
     * @param encodedPriKey PKCS8 encoded rsa key
     * @return Public key object
     * @throws InvalidKeySpecException If the key is invalid
     */
    public static PrivateKey readRSAPrivateKey(byte[] encodedPriKey) throws InvalidKeySpecException {
        return provider.readRSAPrivateKey(encodedPriKey);
    }

    /**
     * Decrypt data encrypted using AES/CBC/PKCS5_PADDING algorithm
     *
     * @param key  Key
     * @param data
     * @return
     * @throws InvalidKeyException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     */
    public static byte[] aesDecrypt(SecretKey key, byte[] data) throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        return provider.aesDecrypt(key, data);
    }

    public static byte[] rsaEncrypt(PublicKey key, byte[] data) throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        return provider.rsaEncrypt(key, data);
    }

    public static void rsaVerifySignature(DigestAlgorithm digestAlgorithms, PublicKey key, byte[] data, byte[] signature) throws InvalidKeyException, SignatureException {
        provider.rsaVerifySignature(digestAlgorithms, key, data, signature);
    }

    public static byte[] rsaEncrypt(byte[] key, byte[] data) throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException, InvalidKeySpecException {
        return provider.rsaEncrypt(key, data);
    }

    public static byte[] aesEncrypt(SecretKey key, byte[] data) throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        return provider.aesEncrypt(key, data);
    }

    public static SecretKey generateAes128Key() {
        return provider.generateAes128Key();
    }

    public static SecretKey generateAes256Key() {
        return provider.generateAes256Key();
    }

    public static byte[] hmacSha256(SecretKey key, byte[] data) throws InvalidKeyException {
        return provider.hmacSha256(key, data);
    }

    public static KeyPair generateKeyPair(AsymmetricAlgorithm alg, int keysize) {
        return provider.generateKeyPair(alg, keysize);
    }

    public static byte[] decrypt(Key key, byte[] data, String alg) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        return provider.decrypt(key, data, alg);
    }

    public static SecretKey generateAesKey(int keysize) {
        return provider.generateAesKey(keysize);
    }

    public static byte[] rsaDecrypt(byte[] key, byte[] data) throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException, InvalidKeySpecException {
        return provider.rsaDecrypt(key, data);
    }

    public static void verifySignature(String algorithm, PublicKey key, byte[] data, byte[] signature) throws SignatureException, InvalidKeyException {
        provider.verifySignature(algorithm, key, data, signature);
    }

    public static byte[] aesEncrypt(byte[] key, byte[] data) throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        return provider.aesEncrypt(key, data);
    }

    public static byte[] aesDecrypt(byte[] key, byte[] data) throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        return provider.aesDecrypt(key, data);
    }

    public static byte[] hmacSha512(SecretKey key, byte[] data) throws InvalidKeyException {
        return provider.hmacSha512(key, data);
    }

    public static byte[] encrypt(Key key, byte[] data, String alg) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        return provider.encrypt(key, data, alg);
    }

    public static byte[] crypt(Key key, byte[] data, String alg, int mode) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        return provider.crypt(key, data, alg, mode);
    }

    public static byte[] hmac(DigestAlgorithm algorithm, SecretKey key, byte[] data) throws InvalidKeyException {
        return provider.hmac(algorithm, key, data);
    }

    public static byte[] rsaDecrypt(PrivateKey key, byte[] data) throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        return provider.rsaDecrypt(key, data);
    }

    public static byte[] rsaSign(DigestAlgorithm digestAlgorithms, PrivateKey key, byte[] data) throws InvalidKeyException, SignatureException {
        return provider.rsaSign(digestAlgorithms, key, data);
    }

    public static byte[] hmacSha1(SecretKey key, byte[] data) throws InvalidKeyException {
        return provider.hmacSha1(key, data);
    }

    public static byte[] sign(String algorithm, PrivateKey key, byte[] data) throws SignatureException, InvalidKeyException {
        return provider.sign(algorithm, key, data);
    }

    public static byte[] hmac(SecretKey key, DigestAlgorithm algorithms, byte[] data) throws InvalidKeyException {
        try {
            Mac mac = Mac.getInstance("Hmac" + algorithms.name());
            mac.init(key);
            return mac.doFinal(data);
        } catch (NoSuchAlgorithmException e) {
            throw new UnexpectedException(e);
        }
    }

    /**
     * Split a key into multiple keys using XOR
     *
     * @param key    key to split
     * @param amount How many new keys should be generated
     * @return List of keys
     */
    public static byte[][] splitKey(byte[] key, int amount) {
        int keyLen = key.length;
        SecureRandom rng = new SecureRandom();
        ArrayList<byte[]> keys = new ArrayList<byte[]>(amount);
        byte[] xorVal = key;
        for (int i = 0; i < amount - 1; i++) {
            byte[] newKey = new byte[keyLen];
            rng.nextBytes(newKey);
            keys.add(newKey);
            xorVal = xor(xorVal, newKey);
        }
        keys.add(xorVal);
        return keys.toArray(new byte[key.length][amount]);
    }

    public static byte[] mergeSplitKey(byte[]... keys) {
        byte[] val = keys[0];
        for (int i = 1; i < keys.length; i++) {
            val = xor(val, keys[i]);
        }
        return val;
    }

    private static byte[] xor(byte[] b1, byte[] b2) {
        byte[] val = new byte[b1.length];
        for (int i = 0; i < b1.length; i++) {
            val[i] = (byte) (b1[i] ^ b2[i]);
        }
        return val;
    }
}
