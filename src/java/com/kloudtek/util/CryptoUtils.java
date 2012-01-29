/*
 * Copyright (c) 2011. KloudTek Ltd
 */

package com.kloudtek.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;

public class CryptoUtils {
    private static final SecureRandom random = new SecureRandom();
    private static final String SHA1 = "SHA-1";
    private static final int SHA_DIGEST_SIZE = 20;

    public static byte[] createSSHADigest(String base64Data) {
        return createSaltedDigest(base64Data, SHA1);
    }

    public static byte[] createSSHADigest(byte[] data) {
        return createSaltedDigest(data, SHA1);
    }

    public static String createSSHAStrDigest(String text) {
        return Base64.encode(createSaltedDigest(text, SHA1));
    }

    public static String createSSHAStrDigest(byte[] data) {
        return Base64.encode(createSaltedDigest(data, SHA1));
    }

    public static byte[] createSaltedDigest(String data, String alg) {
        return createSaltedDigest(data.getBytes(), alg);
    }

    public static byte[] createSaltedDigest(byte[] data, String alg) {
        byte[] salt = generateSalt();
        return createSaltedDigest(salt, data, alg);
    }

    public static byte[] createSaltedDigest(byte[] salt, byte[] data, String alg) {
        try {
            MessageDigest sha = MessageDigest.getInstance(alg);
            sha.update(data);
            sha.update(salt);
            byte[] digest = sha.digest();
            byte[] digestWithSalt = new byte[SHA_DIGEST_SIZE + salt.length];
            System.arraycopy(digest, 0, digestWithSalt, 0, digest.length);
            System.arraycopy(salt, 0, digestWithSalt, digest.length, salt.length);
            return digestWithSalt;
        } catch (NoSuchAlgorithmException e) {
            throw new UnexpectedException(e);
        }
    }

    public static boolean compareSSHADigest(byte[] digest, byte[] data) {
        return compareSaltedDigest(SHA_DIGEST_SIZE, digest, data, SHA1);
    }

    public static boolean compareSSHADigest(byte[] digest, String base64Data) {
        return compareSaltedDigest(SHA_DIGEST_SIZE, digest, Base64.decode(base64Data), SHA1);
    }

    public static boolean compareSSHADigest(String base64Digest, String data) {
        return compareSaltedDigest(SHA_DIGEST_SIZE, Base64.decode(base64Digest), data.getBytes(), SHA1);
    }

    public static boolean compareSaltedDigest(int digestSize, byte[] digest, byte[] data, String alg) {
        try {
            MessageDigest sha = MessageDigest.getInstance(alg);
            byte[] digestData = Arrays.copyOfRange(digest, 0, digestSize);
            byte[] salt = Arrays.copyOfRange(digest, digestSize, digest.length);
            sha.update(data);
            sha.update(salt);
            byte[] encoded = sha.digest();
            return MessageDigest.isEqual(digestData, encoded);
        } catch (NoSuchAlgorithmException e) {
            throw new UnexpectedException(e);
        }
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
}
