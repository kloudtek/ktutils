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
public interface CryptoProvider {
    /**
     * Generate a private key using a symmetric algorithm
     *
     * @param alg     Symmetric algorithm
     * @param keysize Key size
     * @return secret key
     */
    SecretKey generateKey(SymmetricAlgorithm alg, int keysize);

    /**
     * Generate an HMAC key
     *
     * @param algorithm digest algorithm
     * @return secret key
     */
    SecretKey generateHmacKey(DigestAlgorithm algorithm);

    /**
     * Generate an AES secret key
     *
     * @param keysize key size
     * @return key size
     */
    SecretKey generateAesKey(int keysize);

    SecretKey generateAes256Key();

    KeyPair generateKeyPair(AsymmetricAlgorithm alg, int keysize);

    KeyPair generateRSA2048KeyPair();

    KeyPair generateRSA4096KeyPair();

    /**
     * Read an X509 Encoded S_RSA public key
     *
     * @param key X509 encoded rsa key
     * @return Public key object
     * @throws java.security.spec.InvalidKeySpecException If the key is invalid
     */
    RSAPublicKey readRSAPublicKey(byte[] key) throws InvalidKeySpecException;

    /**
     * Read a PKCS8 Encoded S_RSA private key
     *
     * @param encodedPriKey PKCS8 encoded rsa key
     * @return Public key object
     * @throws InvalidKeySpecException If the key is invalid
     */
    PrivateKey readRSAPrivateKey(byte[] encodedPriKey) throws InvalidKeySpecException;

    byte[] hmac(DigestAlgorithm algorithm, SecretKey key, byte[] data) throws InvalidKeyException;

    byte[] hmacSha1(SecretKey key, byte[] data) throws InvalidKeyException;

    byte[] hmacSha256(SecretKey key, byte[] data) throws InvalidKeyException;

    byte[] hmacSha512(SecretKey key, byte[] data) throws InvalidKeyException;

    byte[] aesEncrypt(byte[] key, byte[] data) throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException;

    byte[] aesEncrypt(SecretKey key, byte[] data) throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException;

    byte[] aesDecrypt(byte[] key, byte[] data) throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException;

    byte[] aesDecrypt(SecretKey key, byte[] data) throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException;

    byte[] encrypt(Key key, byte[] data, String alg) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException;

    byte[] decrypt(Key key, byte[] data, String alg) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException;

    byte[] crypt(Key key, byte[] data, String alg, int mode) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException;

    byte[] rsaEncrypt(byte[] key, byte[] data) throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException, InvalidKeySpecException;

    byte[] rsaEncrypt(PublicKey key, byte[] data) throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException;

    byte[] rsaDecrypt(byte[] key, byte[] data) throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException, InvalidKeySpecException;

    byte[] rsaDecrypt(PrivateKey key, byte[] data) throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException;

    byte[] rsaSign(DigestAlgorithm digestAlgorithms, PrivateKey key, byte[] data) throws InvalidKeyException, SignatureException;

    void rsaVerifySignature(DigestAlgorithm digestAlgorithms, PublicKey key, byte[] data, byte[] signature) throws InvalidKeyException, SignatureException;
}
