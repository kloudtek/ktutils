/*
 * Copyright (c) 2014 Kloudtek Ltd
 */

package com.kloudtek.util.crypto;

import com.kloudtek.util.Base64;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
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
    private static final char[] symbolsAllCaps;
    private static final char[] symbols;
    private static final Logger logger = Logger.getLogger(CryptoUtils.class.getName());
    static CryptoEngine engine = new JCECryptoEngine();
    private static final SecureRandom rng = new SecureRandom();

    static {
        StringBuilder tmp = new StringBuilder();
        for (char c = '2'; c <= '9'; c++) {
            tmp.append(c);
        }
        for (char c = 'A'; c <= 'Z'; c++) {
            if (c != 'I' && c != 'O') {
                tmp.append(c);
            }
        }
        symbolsAllCaps = tmp.toString().toCharArray();
        for (char c = 'a'; c <= 'z'; c++) {
            if (c != 'l' && c != 'o') {
                tmp.append(c);
            }
        }
        symbols = tmp.toString().toCharArray();
    }

    /**
     * Generate a private key using a symmetric algorithm
     *
     * @param alg     Symmetric algorithm
     * @param keysize Key size
     * @return secret key
     */
    public static SecretKey generateKey(SymmetricAlgorithm alg, int keysize) {
        return engine.generateKey(alg, keysize);
    }

    /**
     * Generate RSA 4096bit key pair
     *
     * @return RSA Key pair
     */
    public static KeyPair generateRSA4096KeyPair() {
        return engine.generateRSA4096KeyPair();
    }

    /**
     * Generate RSA 4096bit key pair
     *
     * @return RSA Key pair
     */
    public static KeyPair generateRSA2048KeyPair() {
        return engine.generateRSA2048KeyPair();
    }

    /**
     * Generate HMAC key
     *
     * @param algorithm digest algorithm
     * @return HMAC key
     */
    public static SecretKey generateHmacKey(DigestAlgorithm algorithm) {
        return engine.generateHmacKey(algorithm);
    }

    public static SecretKey generatePBEAESKey(char[] key, int iterations, byte[] salt, int keyLen) throws InvalidKeySpecException {
        return engine.generatePBEAESKey(key, iterations, salt, keyLen);
    }

    /**
     * Read an X509 Encoded S_RSA public key
     *
     * @param key X509 encoded rsa key
     * @return Public key object
     * @throws java.security.spec.InvalidKeySpecException If the key is invalid
     */
    public static RSAPublicKey readRSAPublicKey(byte[] key) throws InvalidKeySpecException {
        return engine.readRSAPublicKey(key);
    }

    /**
     * Read a PKCS8 Encoded S_RSA private key
     *
     * @param encodedPriKey PKCS8 encoded rsa key
     * @return Public key object
     * @throws InvalidKeySpecException If the key is invalid
     */
    public static PrivateKey readRSAPrivateKey(byte[] encodedPriKey) throws InvalidKeySpecException {
        return engine.readRSAPrivateKey(encodedPriKey);
    }

    public static SecretKey readAESKey(byte[] encodedAesKey) {
        return engine.readAESKey(encodedAesKey);
    }

    public static SecretKey readHMACKey(@NotNull DigestAlgorithm algorithm, @NotNull byte[] encodedKey) {
        return engine.readHMACKey(algorithm, encodedKey);
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
        return engine.aesDecrypt(key, data);
    }

    public static byte[] rsaEncrypt(PublicKey key, byte[] data) throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        return engine.rsaEncrypt(key, data);
    }

    public static void rsaVerifySignature(DigestAlgorithm digestAlgorithms, PublicKey key, byte[] data, byte[] signature) throws InvalidKeyException, SignatureException {
        engine.rsaVerifySignature(digestAlgorithms, key, data, signature);
    }

    public static byte[] rsaEncrypt(byte[] key, byte[] data) throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException, InvalidKeySpecException {
        return engine.rsaEncrypt(key, data);
    }

    public static byte[] aesEncrypt(SecretKey key, byte[] data) throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        return engine.aesEncrypt(key, data);
    }

    public static SecretKey generateAes128Key() {
        return engine.generateAes128Key();
    }

    public static SecretKey generateAes192Key() {
        return engine.generateAes192Key();
    }

    public static SecretKey generateAes256Key() {
        return engine.generateAes256Key();
    }

    public static byte[] hmacSha256(SecretKey key, byte[] data) throws InvalidKeyException {
        return engine.hmacSha256(key, data);
    }

    public static KeyPair generateKeyPair(AsymmetricAlgorithm alg, int keysize) {
        return engine.generateKeyPair(alg, keysize);
    }

    public static byte[] decrypt(Key key, byte[] data, String alg) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        return engine.decrypt(key, data, alg);
    }

    public static SecretKey generateAesKey(int keysize) {
        return engine.generateAesKey(keysize);
    }

    public static byte[] rsaDecrypt(byte[] key, byte[] data) throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException, InvalidKeySpecException {
        return engine.rsaDecrypt(key, data);
    }

    public static void verifySignature(String algorithm, PublicKey key, byte[] data, byte[] signature) throws SignatureException, InvalidKeyException {
        engine.verifySignature(algorithm, key, data, signature);
    }

    public static byte[] aesEncrypt(byte[] key, byte[] data) throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        return engine.aesEncrypt(key, data);
    }

    public static byte[] aesDecrypt(byte[] key, byte[] data) throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        return engine.aesDecrypt(key, data);
    }

    public static byte[] hmacSha512(SecretKey key, byte[] data) throws InvalidKeyException {
        return engine.hmacSha512(key, data);
    }

    public static byte[] encrypt(Key key, byte[] data, String alg) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        return engine.encrypt(key, data, alg);
    }

    public static byte[] crypt(Key key, byte[] data, String alg, int mode) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        return engine.crypt(key, data, alg, mode);
    }

    public static byte[] hmac(DigestAlgorithm algorithm, SecretKey key, byte[] data) throws InvalidKeyException {
        return engine.hmac(algorithm, key, data);
    }

    public static byte[] rsaDecrypt(PrivateKey key, byte[] data) throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        return engine.rsaDecrypt(key, data);
    }

    public static byte[] rsaSign(DigestAlgorithm digestAlgorithms, PrivateKey key, byte[] data) throws InvalidKeyException, SignatureException {
        return engine.rsaSign(digestAlgorithms, key, data);
    }

    public static byte[] hmacSha1(SecretKey key, byte[] data) throws InvalidKeyException {
        return engine.hmacSha1(key, data);
    }

    public static byte[] sign(String algorithm, PrivateKey key, byte[] data) throws SignatureException, InvalidKeyException {
        return engine.sign(algorithm, key, data);
    }

    public static byte[] hmac(SecretKey key, DigestAlgorithm algorithms, byte[] data) throws InvalidKeyException {
        return engine.hmac(algorithms, key, data);
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
    public static void zero(@NotNull char[]... data) {
        for (char[] chars : data) {
            if (chars != null) {
                Arrays.fill(chars, '\u0000');
            }
        }
    }

    /**
     * fill the array with zeros
     *
     * @param data
     */
    public static void zero(@NotNull byte[]... data) {
        for (byte[] bytes : data) {
            if (bytes != null) {
                Arrays.fill(bytes, (byte) 0);
            }
        }
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
    public static void destroy(@Nullable Key key) {
        if (key != null && key instanceof Destroyable) {
            if (!((Destroyable) key).isDestroyed()) {
                try {
                    ((Destroyable) key).destroy();
                } catch (DestroyFailedException e) {
                    logger.log(Level.WARNING, "Failed to destroy key: " + e.getMessage(), e);
                }
            }
        }
    }

    public static char[] generateRandomPassword(int len, boolean allCaps) {
        char[] charSet = allCaps ? symbolsAllCaps : symbols;
        char[] pw = new char[len];
        for (int i = 0; i < len; i++) {
            pw[i] = charSet[rng.nextInt(charSet.length)];
        }
        return pw;
    }

    public static String fingerprint(byte[] data) {
        return new Base64(-1, new byte[0], true).encodeAsString(DigestUtils.md5(data)).toUpperCase();
    }
}
