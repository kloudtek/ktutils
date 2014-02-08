/*
 * Copyright (c) 2014 Kloudtek Ltd
 */

package com.kloudtek.util.crypto;

import com.kloudtek.util.StringUtils;
import com.kloudtek.util.UnexpectedException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.kloudtek.util.StringUtils.base64Decode;
import static com.kloudtek.util.StringUtils.base64Encode;
import static com.kloudtek.util.crypto.DigestAlgorithm.*;

/**
 * Various digest utilities
 */
public class DigestUtils {
    private static final Logger logger = Logger.getLogger(DigestUtils.class.getName());
    private static final SecureRandom random = new SecureRandom();
    public static final int BUFSZ = 8192;

    /**
     * Create a digest object
     *
     * @param alg Digest algorithm
     * @return Message digest object
     */
    public static MessageDigest digest(DigestAlgorithm alg) {
        try {
            return MessageDigest.getInstance(alg.getJceId());
        } catch (NoSuchAlgorithmException e) {
            throw new UnexpectedException(e);
        }
    }

    /**
     * Create a digest from a byte array
     *
     * @param data Data to create digest from
     * @param alg  Algorithm to use for digest
     * @return digest value
     */
    public static byte[] digest(byte[] data, DigestAlgorithm alg) {
        try {
            MessageDigest sha = MessageDigest.getInstance(alg.getJceId());
            sha.update(data);
            return sha.digest();
        } catch (NoSuchAlgorithmException e) {
            throw new UnexpectedException(e);
        }
    }

    /**
     * Create a digest from a file
     *
     * @param file File from which to create digest from
     * @param alg  Algorithm to use for digest
     * @return digest value
     * @throws java.io.IOException If an error occurs while reading the file
     */
    public static byte[] digest(File file, DigestAlgorithm alg) throws IOException {
        FileInputStream is = new FileInputStream(file);
        try {
            return digest(is, alg);
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                logger.log(Level.WARNING, e.getMessage(), e);
            }
        }
    }

    public static byte[] digest(InputStream inputStream, DigestAlgorithm alg) throws IOException {
        return digest(inputStream, alg.getJceId());
    }

    public static byte[] digest(InputStream inputStream, String alg) throws IOException {
        try {
            byte[] buffer = new byte[BUFSZ];
            MessageDigest digest = MessageDigest.getInstance(alg);
            for (int i = inputStream.read(buffer, 0, BUFSZ); i != -1; i = inputStream.read(buffer, 0, BUFSZ)) {
                digest.update(buffer, 0, i);
            }
            return digest.digest();
        } catch (NoSuchAlgorithmException e) {
            throw new UnexpectedException("Algorithm not supported: " + alg, e);
        }
    }

    public static byte[] saltedDigest(byte[] data, DigestAlgorithm alg) {
        return saltedDigest(generateSalt(), data, alg);
    }

    public static byte[] saltedDigest(byte[] salt, byte[] data, DigestAlgorithm alg) {
        return saltedDigest(salt, data, alg.getJceId(), alg.getHashLen());
    }

    public static byte[] saltedDigest(byte[] salt, byte[] data, String alg, int hashLen) {
        try {
            MessageDigest sha = MessageDigest.getInstance(alg);
            sha.update(data);
            sha.update(salt);
            byte[] digest = sha.digest();
            byte[] digestWithSalt = new byte[hashLen + salt.length];
            System.arraycopy(digest, 0, digestWithSalt, 0, digest.length);
            System.arraycopy(salt, 0, digestWithSalt, digest.length, salt.length);
            return digestWithSalt;
        } catch (NoSuchAlgorithmException e) {
            throw new UnexpectedException(e);
        }
    }

    public static boolean compareSaltedDigest(byte[] digest, byte[] data, DigestAlgorithm alg) {
        try {
            MessageDigest sha = MessageDigest.getInstance(alg.getJceId());
            byte[] digestData = Arrays.copyOfRange(digest, 0, alg.getHashLen());
            byte[] salt = Arrays.copyOfRange(digest, alg.getHashLen(), digest.length);
            sha.update(data);
            sha.update(salt);
            byte[] encoded = sha.digest();
            return MessageDigest.isEqual(digestData, encoded);
        } catch (NoSuchAlgorithmException e) {
            throw new UnexpectedException(e);
        }
    }

    public static boolean compareSaltedDigest(String b64Digest, String data, DigestAlgorithm alg) {
        return compareSaltedDigest(base64Decode(b64Digest), data.getBytes(), alg);
    }

    private static byte[] generateSalt() {
        final byte[] salt = new byte[8];
        random.nextBytes(salt);
        return salt;
    }

    public static byte[] saltedDigest(String text, DigestAlgorithm alg) {
        return saltedDigest(StringUtils.toUTF8(text), alg);
    }

    public static String saltedB64Digest(String text, DigestAlgorithm alg) {
        return base64Encode(saltedDigest(text, alg));
    }

    public static String saltedB64Digest(byte[] data, DigestAlgorithm alg) {
        return base64Encode(saltedDigest(data, alg));
    }

    public static byte[] sha1(byte[] data) {
        return digest(data, SHA1);
    }

    public static byte[] sha1(InputStream data) throws IOException {
        return digest(data, SHA1);
    }

    public static byte[] sha1(File file) throws IOException {
        return digest(file, SHA1);
    }

    public static byte[] sha256(byte[] data) {
        return digest(data, SHA256);
    }

    public static byte[] sha256(InputStream data) throws IOException {
        return digest(data, SHA256);
    }

    public static byte[] sha256(File file) throws IOException {
        return digest(file, SHA256);
    }

    public static byte[] sha512(byte[] data) {
        return digest(data, SHA512);
    }

    public static byte[] sha512(InputStream data) throws IOException {
        return digest(data, SHA512);
    }

    public static byte[] sha512(File file) throws IOException {
        return digest(file, SHA512);
    }

    public static byte[] md5(byte[] data) {
        return digest(data, MD5);
    }

    public static byte[] md5(InputStream data) throws IOException {
        return digest(data, MD5);
    }

    public static byte[] md5(File file) throws IOException {
        return digest(file, MD5);
    }
}
