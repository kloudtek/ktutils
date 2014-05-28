/*
 * Copyright (c) 2014 Kloudtek Ltd
 */

package com.kloudtek.util.crypto;

import com.kloudtek.util.UnexpectedException;
import org.jetbrains.annotations.NotNull;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.security.auth.DestroyFailedException;
import javax.security.auth.Destroyable;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.security.*;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Various cryptographic methods
 */
public class CryptoUtils {
    private static final Logger logger = Logger.getLogger(CryptoUtils.class.getName());
    private static CryptoEngine provider = new JCECryptoEngine();
    private static final SecureRandom rng = new SecureRandom();

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

    public static SecretKey generatePBEAESKey(char[] key, int iterations, byte[] salt, int keyLen) throws InvalidKeySpecException {
        return provider.generatePBEAESKey(key, iterations, salt, keyLen);
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

    public static SecretKey readAESKey(byte[] encodedAesKey) {
        return provider.readAESKey(encodedAesKey);
    }

    public static SecretKey readHMACKey(@NotNull DigestAlgorithm algorithm, @NotNull byte[] encodedKey) {
        return provider.readHMACKey(algorithm, encodedKey);
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
        ArrayList<byte[]> keys = new ArrayList<byte[]>(amount);
        if (amount < 0) {
            throw new IllegalArgumentException("Amount must be 1 or more");
        } else if (amount == 1) {
            keys.add(key);
        } else {
            SecureRandom rng = new SecureRandom();
            byte[] xorVal = key;
            for (int i = 0; i < amount - 1; i++) {
                byte[] newKey = new byte[keyLen];
                rng.nextBytes(newKey);
                keys.add(newKey);
                xorVal = xor(xorVal, newKey);
            }
            keys.add(xorVal);
        }
        return keys.toArray(new byte[key.length][amount]);
    }

    /**
     * fill the array with zeros
     *
     * @param data
     */
    public static void zero(char[] data) {
        Arrays.fill(data, '\u0000');
    }

    /**
     * fill the array with zeros
     *
     * @param data
     */
    public static void zero(byte[] data) {
        Arrays.fill(data, (byte) 0);
    }

    /**
     * fill the array with zeros
     *
     * @param data
     */
    public static void zero(CharBuffer data) {
        zero(data.array());
    }

    /**
     * fill the array with zeros
     *
     * @param data
     */
    public static void zero(ByteBuffer data) {
        zero(data.array());
    }

    public static byte[] mergeSplitKey(byte[]... keys) {
        if (keys == null) {
            throw new IllegalArgumentException("There must be at least one key");
        } else {
            return mergeSplitKey(Arrays.asList(keys));
        }
    }

    public static byte[] mergeSplitKey(Collection<byte[]> keys) {
        if (keys == null || keys.isEmpty()) {
            throw new IllegalArgumentException("There must be at least one key");
        } else if (keys.size() == 1) {
            return keys.iterator().next();
        } else {
            Iterator<byte[]> i = keys.iterator();
            byte[] val = i.next();
            int len = val.length;
            while (i.hasNext()) {
                byte[] next = i.next();
                if (next.length != len) {
                    throw new IllegalArgumentException("All keys must have the same length");
                }
                val = xor(val, next);
            }
            return val;
        }
    }

    private static byte[] xor(byte[] b1, byte[] b2) {
        byte[] val = new byte[b1.length];
        for (int i = 0; i < b1.length; i++) {
            val[i] = (byte) (b1[i] ^ b2[i]);
        }
        return val;
    }

    /**
     * Retrieve shared instance of {@link SecureRandom}
     *
     * @return {@link SecureRandom} instance
     */
    public static SecureRandom rng() {
        return rng;
    }

    /**
     * Attempt to zero all data in the key
     *
     * @param key Key to destroy
     */
    public static void destroy(Key key) {
        if (key instanceof Destroyable) {
            if (!((Destroyable) key).isDestroyed()) {
                try {
                    ((Destroyable) key).destroy();
                } catch (DestroyFailedException e) {
                    logger.log(Level.WARNING, "Failed to destroy key: " + e.getMessage(), e);
                }
            }
        }
    }
}
