/*
 * Copyright (c) Kloudtek Ltd 2013.
 */

package com.kloudtek.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;

public class CryptoUtils {
    private static final SecureRandom random = new SecureRandom();
    public static final int BUFSZ = 8192;

    public static MessageDigest digest(Algorithm alg) {
        try {
            return MessageDigest.getInstance(alg.id);
        } catch (NoSuchAlgorithmException e) {
            throw new UnexpectedException(e);
        }
    }

    public static byte[] digest(byte[] data, Algorithm alg) {
        try {
            MessageDigest sha = MessageDigest.getInstance(alg.id);
            sha.update(data);
            return sha.digest();
        } catch (NoSuchAlgorithmException e) {
            throw new UnexpectedException(e);
        }
    }

    public static byte[] digest(File file, Algorithm alg) throws IOException {
        FileInputStream is = new FileInputStream(file);
        try {
            return digest(is, alg);
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                //
            }
        }
    }

    public static byte[] digest(InputStream inputStream, Algorithm alg) throws IOException {
        return digest(inputStream, alg.id);
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

    public static byte[] saltedDigest(byte[] data, Algorithm alg) {
        return saltedDigest(generateSalt(), data, alg);
    }

    public static byte[] saltedDigest(byte[] salt, byte[] data, Algorithm alg) {
        return saltedDigest(salt, data, alg.id, alg.hashLen);
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

    public static boolean compareSaltedDigest(byte[] digest, byte[] data, Algorithm alg) {
        try {
            MessageDigest sha = MessageDigest.getInstance(alg.id);
            byte[] digestData = Arrays.copyOfRange(digest, 0, alg.hashLen);
            byte[] salt = Arrays.copyOfRange(digest, alg.hashLen, digest.length);
            sha.update(data);
            sha.update(salt);
            byte[] encoded = sha.digest();
            return MessageDigest.isEqual(digestData, encoded);
        } catch (NoSuchAlgorithmException e) {
            throw new UnexpectedException(e);
        }
    }

    public static boolean compareSaltedDigest(String b64Digest, String data, Algorithm alg) {
        return compareSaltedDigest(Base64.decode(b64Digest), data.getBytes(), alg);
    }

    private static byte[] generateSalt() {
        try {
            final byte[] salt = new byte[8];
            final SecureRandom saltGen = SecureRandom.getInstance("SHA1PRNG");
            saltGen.nextBytes(salt);
            return salt;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static byte[] saltedDigest(String text, Algorithm alg) {
        return saltedDigest(text.getBytes(), alg);
    }

    public static String saltedB64Digest(String text, Algorithm alg) {
        return Base64.encode(saltedDigest(text, alg));
    }

    public static String saltedB64Digest(byte[] data, Algorithm alg) {
        return Base64.encode(saltedDigest(data, alg));
    }

    public static byte[] sha1(byte[] data) {
        return digest(data, Algorithm.SHA1);
    }

    public static byte[] sha1(InputStream data) throws IOException {
        return digest(data, Algorithm.SHA1);
    }

    public static byte[] sha1(File file) throws IOException {
        return digest(file, Algorithm.SHA1);
    }

    public static byte[] sha256(byte[] data) {
        return digest(data, Algorithm.SHA256);
    }

    public static byte[] sha256(InputStream data) throws IOException {
        return digest(data, Algorithm.SHA256);
    }

    public static byte[] sha256(File file) throws IOException {
        return digest(file, Algorithm.SHA256);
    }

    public static byte[] sha512(byte[] data) {
        return digest(data, Algorithm.SHA512);
    }

    public static byte[] sha512(InputStream data) throws IOException {
        return digest(data, Algorithm.SHA512);
    }

    public static byte[] sha512(File file) throws IOException {
        return digest(file, Algorithm.SHA512);
    }

    public static byte[] md5(byte[] data) {
        return digest(data, Algorithm.MD5);
    }

    public static byte[] md5(InputStream data) throws IOException {
        return digest(data, Algorithm.MD5);
    }

    public static byte[] md5(File file) throws IOException {
        return digest(file, Algorithm.MD5);
    }

    public enum Algorithm {
        MD5(16, "MD5"), SHA1(20, "SHA-1"), SHA256(32, "SHA-256"), SHA512(64, "SHA-512");
        private int hashLen;
        private String id;

        private Algorithm(int hashLen, String id) {
            this.hashLen = hashLen;
            this.id = id;
        }
    }
}
