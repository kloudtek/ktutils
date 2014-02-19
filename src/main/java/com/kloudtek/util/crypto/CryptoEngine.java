/*
 * Copyright (c) 2014 Kloudtek Ltd
 */

package com.kloudtek.util.crypto;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.SecretKey;
import java.security.*;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;

/**
 * Interface for cryptography providers
 */
public abstract class CryptoEngine {
    /**
     * Generate a private key using a symmetric algorithm
     *
     * @param alg     Symmetric algorithm
     * @param keysize Key size
     * @return secret key
     */
    public abstract SecretKey generateKey(SymmetricAlgorithm alg, int keysize);

    /**
     * Generate an HMAC key
     *
     * @param algorithm digest algorithm
     * @return secret key
     */
    public abstract SecretKey generateHmacKey(DigestAlgorithm algorithm);

    /**
     * Generate an AES secret key
     *
     * @param keySize key size
     * @return key size
     */
    public SecretKey generateAesKey(int keySize) {
        return generateKey(SymmetricAlgorithm.AES, keySize);
    }

    public SecretKey generateAes128Key() {
        return generateKey(SymmetricAlgorithm.AES, 128);
    }

    public SecretKey generateAes256Key() {
        return generateKey(SymmetricAlgorithm.AES, 256);
    }

    public abstract KeyPair generateKeyPair(AsymmetricAlgorithm alg, int keysize);

    public KeyPair generateRSA2048KeyPair() {
        return generateKeyPair(AsymmetricAlgorithm.RSA, 2048);
    }

    public KeyPair generateRSA4096KeyPair() {
        return generateKeyPair(AsymmetricAlgorithm.RSA, 4096);
    }

    /**
     * Read an X509 Encoded S_RSA public key
     *
     * @param key X509 encoded rsa key
     * @return Public key object
     * @throws java.security.spec.InvalidKeySpecException If the key is invalid
     */
    public abstract RSAPublicKey readRSAPublicKey(byte[] key) throws InvalidKeySpecException;

    /**
     * Read a PKCS8 Encoded S_RSA private key
     *
     * @param encodedPriKey PKCS8 encoded rsa key
     * @return Public key object
     * @throws InvalidKeySpecException If the key is invalid
     */
    public abstract PrivateKey readRSAPrivateKey(byte[] encodedPriKey) throws InvalidKeySpecException;

    public abstract byte[] hmac(DigestAlgorithm algorithm, SecretKey key, byte[] data) throws InvalidKeyException;

    public abstract byte[] hmacSha1(SecretKey key, byte[] data) throws InvalidKeyException;

    public abstract byte[] hmacSha256(SecretKey key, byte[] data) throws InvalidKeyException;

    public abstract byte[] hmacSha512(SecretKey key, byte[] data) throws InvalidKeyException;

    public abstract byte[] aesEncrypt(byte[] key, byte[] data) throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException;

    public abstract byte[] aesEncrypt(SecretKey key, byte[] data) throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException;

    public abstract byte[] aesDecrypt(byte[] key, byte[] data) throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException;

    public abstract byte[] aesDecrypt(SecretKey key, byte[] data) throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException;

    public abstract byte[] encrypt(Key key, byte[] data, String alg) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException;

    public abstract byte[] decrypt(Key key, byte[] data, String alg) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException;

    public abstract byte[] crypt(Key key, byte[] data, String alg, int mode) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException;

    public abstract byte[] rsaEncrypt(byte[] key, byte[] data) throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException, InvalidKeySpecException;

    public abstract byte[] rsaEncrypt(PublicKey key, byte[] data) throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException;

    public abstract byte[] rsaDecrypt(byte[] key, byte[] data) throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException, InvalidKeySpecException;

    public abstract byte[] rsaDecrypt(PrivateKey key, byte[] data) throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException;

    public abstract byte[] rsaSign(DigestAlgorithm digestAlgorithms, PrivateKey key, byte[] data) throws InvalidKeyException, SignatureException;

    public abstract void rsaVerifySignature(DigestAlgorithm digestAlgorithms, PublicKey key, byte[] data, byte[] signature) throws InvalidKeyException, SignatureException;

    public abstract byte[] sign(String algorithm, PrivateKey key, byte[] data) throws SignatureException, InvalidKeyException;

    public abstract void verifySignature(String algorithm, PublicKey key, byte[] data, byte[] signature) throws SignatureException, InvalidKeyException;
}
