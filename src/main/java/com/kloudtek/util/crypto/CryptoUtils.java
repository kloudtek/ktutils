/*
 * Copyright (c) 2013 KloudTek Ltd
 */

package com.kloudtek.util.crypto;

import com.kloudtek.util.SystemUtils;
import com.kloudtek.util.UnexpectedException;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;

import static com.kloudtek.util.StringUtils.base64Decode;
import static com.kloudtek.util.StringUtils.base64Encode;
import static com.kloudtek.util.crypto.DigestAlgorithm.*;

/**
 * <p>Provide easy to use cryptographic methods, and automatically implement workarounds for known security
 * vulnerabilities in the cryptographic libraries.</p>
 * <p>
 * The following things are automatically done when appropriate:
 * </p>
 * <ul>
 * <li>On Android, will automatically apply fix to random number generator if required</li>
 * <li>Automatically invoke {@link #useBouncycastle()} if the system property 'usebouncycastle' is set to 'true'</li>
 * </ul>
 */
public class CryptoUtils {
    public static final int BUFSZ = 8192;
    public static final String S_AES = "AES";
    public static final String S_RSA = "RSA";
    private static final SecureRandom random = new SecureRandom();
    private static final AtomicBoolean bouncyInstalled = new AtomicBoolean();
    private static final boolean limitedJce;
    private static final Class<Provider> spongyProvider;
    private static final Class<Provider> bouncyProvider;
    private static String bestAesAlg = "AES";

    static {
        if (SystemUtils.isAndroid()) {
            AndroidPRNFix.apply();
        }
        try {
            limitedJce = Cipher.getMaxAllowedKeyLength("AES") < 256;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        Class<Provider> p;
        try {
            p = (Class<Provider>) Class.forName("org.bouncycastle.jce.provider.BouncyCastleProvider");
        } catch (ClassNotFoundException e) {
            p = null;
        }
        bouncyProvider = p;
        try {
            p = (Class<Provider>) Class.forName("org.spongycastle.jce.provider.BouncyCastleProvider");
        } catch (ClassNotFoundException e) {
            p = null;
        }
        spongyProvider = p;
        String usebouncycastle = System.getProperty("usebouncycastle");
        if (usebouncycastle != null && usebouncycastle.equalsIgnoreCase("true")) {
            useBouncycastle();
        }
    }

    /**
     * Invokes {@link #useBouncycastle()} with the first parameter as false unless running on android with spongycastle
     * available (in which case it will be true).
     */
    public static void useBouncycastle() {
        useBouncycastle(SystemUtils.isAndroid() && spongyProvider != null);
        findBestAlgs();
    }

    /**
     * Find best available cryptographic algorithms
     */
    private static void findBestAlgs() {
        // todo
    }

    /**
     * This will register bouncycastle (or spongycastle if running on android and the library is present, this method
     * will do nothing is running on android since it already has bouncycastle installed, which cannot be overriden) as a provider.
     * This is an idempotent method that can safely be invoked multiple times.
     *
     * @param first If set to true, the provider will be set as the first provider and thus become the default provider
     *              for all JCE operations
     */
    public static void useBouncycastle(boolean first) {
        synchronized (bouncyInstalled) {
            if (!bouncyInstalled.get()) {
                Class<Provider> provClass = null;
                if (SystemUtils.isAndroid() && spongyProvider != null) {
                    provClass = spongyProvider;
                } else if (!SystemUtils.isAndroid() && bouncyProvider != null) {
                    provClass = bouncyProvider;
                }
                if (provClass != null) {
                    try {
                        Provider provider = provClass.newInstance();
                        if (first) {
                            Security.insertProviderAt(provider, 0);
                        } else {
                            Security.addProvider(provider);
                        }
                    } catch (InstantiationException e) {
                        throw new RuntimeException(e);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                }
                bouncyInstalled.set(true);
            }
        }
    }

    public static MessageDigest digest(DigestAlgorithm alg) {
        try {
            return MessageDigest.getInstance(alg.getJceId());
        } catch (NoSuchAlgorithmException e) {
            throw new UnexpectedException(e);
        }
    }

    public static byte[] digest(byte[] data, DigestAlgorithm alg) {
        try {
            MessageDigest sha = MessageDigest.getInstance(alg.getJceId());
            sha.update(data);
            return sha.digest();
        } catch (NoSuchAlgorithmException e) {
            throw new UnexpectedException(e);
        }
    }

    public static byte[] digest(File file, DigestAlgorithm alg) throws IOException {
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
        return saltedDigest(text.getBytes(), alg);
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

    // Key management methods

    public static SecretKey generateKey(SymmetricAlgorithm alg, int keysize) {
        try {
            KeyGenerator kg = KeyGenerator.getInstance(alg.getJceId());
            kg.init(keysize);
            return kg.generateKey();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static KeyPair generateKeyPair(AsymmetricAlgorithm alg, int keysize) {
        try {
            KeyPairGenerator kg = KeyPairGenerator.getInstance(alg.getJceId());
            kg.initialize(keysize);
            return kg.generateKeyPair();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Read an X509 Encoded public key
     *
     * @param key X509 encoded key
     * @return Public key object
     * @throws InvalidKeySpecException If the key is invalid
     */
    public static PublicKey readPublicKey(String algorithm, byte[] key) throws InvalidKeySpecException {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
            X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(key);
            return keyFactory.generatePublic(publicKeySpec);
        } catch (NoSuchAlgorithmException e) {
            throw new UnexpectedException(e);
        }
    }

    /**
     * Read an X509 Encoded RSA public key
     *
     * @param key X509 encoded rsa key
     * @return Public key object
     * @throws InvalidKeySpecException If the key is invalid
     */
    public static RSAPublicKey readRSAPublicKey(byte[] key) throws InvalidKeySpecException {
        return (RSAPublicKey) readPublicKey(S_RSA, key);
    }

    // AES encryption

    public static byte[] aesEncrypt(byte[] key, byte[] data) throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        return aesEncrypt(new SecretKeySpec(key, 0, key.length, S_AES), data);
    }

    public static byte[] aesEncrypt(SecretKey key, byte[] data) throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        return crypt(key, data, "AES", Cipher.ENCRYPT_MODE);
    }

    public static byte[] aesDecrypt(byte[] key, byte[] data) throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        return aesDecrypt(new SecretKeySpec(key, 0, key.length, S_AES), data);
    }

    public static byte[] aesDecrypt(SecretKey key, byte[] data) throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        return crypt(key, data, "AES", Cipher.DECRYPT_MODE);
    }

    public static byte[] crypt(SecretKey key, byte[] data, String alg, int mode) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        try {
            Cipher aesCipher = Cipher.getInstance(alg);
            aesCipher.init(mode, key);
            return aesCipher.doFinal(data);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (NoSuchPaddingException e) {
            throw new RuntimeException(e);
        }
    }

}
