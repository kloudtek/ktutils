/*
 * Copyright (c) 2014 Kloudtek Ltd
 */

package com.kloudtek.util.crypto;

import com.kloudtek.util.UnexpectedException;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * Cryptography provider that uses the standard java crypto extension (JCE)
 */
public class JCECryptoEngine extends CryptoEngine {
    public static final String S_RSA = "RSA";
    public static final String S_AES = "AES";
    public static final String AES_CBC_PKCS_5_PADDING = "AES/ECB/PKCS5PADDING";
    public static final String RSA_ECB_OAEPPADDING = "RSA/ECB/OAEPWithSHA1AndMGF1Padding";

    // Key generation

    /**
     * Generate a private key using a symmetric algorithm
     *
     * @param alg     Symmetric algorithm
     * @param keysize Key size
     * @return secret key
     */
    @Override
    public SecretKey generateKey(SymmetricAlgorithm alg, int keysize) {
        try {
            KeyGenerator kg = KeyGenerator.getInstance(alg.getJceId());
            kg.init(keysize);
            return kg.generateKey();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Generate an HMAC key
     *
     * @param algorithm digest algorithm
     * @return secret key
     */
    @Override
    public SecretKey generateHmacKey(DigestAlgorithm algorithm) {
        try {
            KeyGenerator kg = KeyGenerator.getInstance("Hmac" + algorithm.name());
            return kg.generateKey();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public KeyPair generateKeyPair(AsymmetricAlgorithm alg, int keySize) {
        try {
            KeyPairGenerator kg = KeyPairGenerator.getInstance(alg.getJceId());
            kg.initialize(keySize);
            return kg.generateKeyPair();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Read an X509 Encoded S_RSA public key
     *
     * @param key X509 encoded rsa key
     * @return Public key object
     * @throws java.security.spec.InvalidKeySpecException If the key is invalid
     */
    @Override
    public RSAPublicKey readRSAPublicKey(byte[] key) throws InvalidKeySpecException {
        PublicKey result;
        try {
            KeyFactory keyFactory = KeyFactory.getInstance(S_RSA);
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(key);
            result = keyFactory.generatePublic(keySpec);
        } catch (NoSuchAlgorithmException e) {
            throw new UnexpectedException(e);
        }
        return (RSAPublicKey) result;
    }

    /**
     * Read a PKCS8 Encoded S_RSA private key
     *
     * @param encodedPriKey PKCS8 encoded rsa key
     * @return Public key object
     * @throws InvalidKeySpecException If the key is invalid
     */
    @Override
    public PrivateKey readRSAPrivateKey(byte[] encodedPriKey) throws InvalidKeySpecException {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance(S_RSA);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(encodedPriKey);
            return keyFactory.generatePrivate(keySpec);
        } catch (NoSuchAlgorithmException e) {
            throw new UnexpectedException(e);
        }
    }

    // HMAC

    @Override
    public byte[] hmac(DigestAlgorithm algorithm, SecretKey key, byte[] data) throws InvalidKeyException {
        try {
            Mac mac = Mac.getInstance("Hmac" + algorithm.name());
            mac.init(key);
            return mac.doFinal(data);
        } catch (NoSuchAlgorithmException e) {
            throw new UnexpectedException(e);
        }
    }

    @Override
    public byte[] hmacSha1(SecretKey key, byte[] data) throws InvalidKeyException {
        return hmac(DigestAlgorithm.SHA1, key, data);
    }

    @Override
    public byte[] hmacSha256(SecretKey key, byte[] data) throws InvalidKeyException {
        return hmac(DigestAlgorithm.SHA256, key, data);
    }

    @Override
    public byte[] hmacSha512(SecretKey key, byte[] data) throws InvalidKeyException {
        return hmac(DigestAlgorithm.SHA512, key, data);
    }

    // AES encryption

    @Override
    public byte[] aesEncrypt(byte[] key, byte[] data) throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        return aesEncrypt(new SecretKeySpec(key, 0, key.length, S_AES), data);
    }

    @Override
    public byte[] aesEncrypt(SecretKey key, byte[] data) throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        return encrypt(key, data, AES_CBC_PKCS_5_PADDING);
    }

    @Override
    public byte[] aesDecrypt(byte[] key, byte[] data) throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        return aesDecrypt(new SecretKeySpec(key, 0, key.length, S_AES), data);
    }

    @Override
    public byte[] aesDecrypt(SecretKey key, byte[] data) throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        return decrypt(key, data, AES_CBC_PKCS_5_PADDING);
    }

    // RSA Encryption and signing

    @Override
    public byte[] rsaEncrypt(byte[] key, byte[] data) throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException, InvalidKeySpecException {
        return rsaEncrypt(readRSAPublicKey(key), data);
    }

    @Override
    public byte[] rsaEncrypt(PublicKey key, byte[] data) throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        return encrypt(key, data, RSA_ECB_OAEPPADDING);
    }

    @Override
    public byte[] rsaDecrypt(byte[] key, byte[] data) throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException, InvalidKeySpecException {
        return rsaDecrypt(readRSAPrivateKey(key), data);
    }

    @Override
    public byte[] rsaDecrypt(PrivateKey key, byte[] data) throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        return decrypt(key, data, RSA_ECB_OAEPPADDING);
    }

    @Override
    public byte[] rsaSign(DigestAlgorithm digestAlgorithms, PrivateKey key, byte[] data) throws InvalidKeyException, SignatureException {
        return sign(digestAlgorithms.name() + "withRSA", key, data);
    }

    @Override
    public void rsaVerifySignature(DigestAlgorithm digestAlgorithms, PublicKey key, byte[] data, byte[] signature) throws InvalidKeyException, SignatureException {
        verifySignature(digestAlgorithms.name() + "withRSA", key, data, signature);
    }

    // Basic encryption / signing methods

    @Override
    public byte[] encrypt(Key key, byte[] data, String alg) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        return crypt(key, data, alg, Cipher.ENCRYPT_MODE);
    }

    @Override
    public byte[] decrypt(Key key, byte[] data, String alg) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        return crypt(key, data, alg, Cipher.DECRYPT_MODE);
    }

    @Override
    public byte[] crypt(Key key, byte[] data, String alg, int mode) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        try {
            Cipher aesCipher = Cipher.getInstance(alg);
            aesCipher.init(mode, key);
            return aesCipher.doFinal(data);
        } catch (NoSuchAlgorithmException e) {
            throw new UnexpectedException(e);
        } catch (NoSuchPaddingException e) {
            throw new UnexpectedException(e);
        }
    }

    @Override
    public byte[] sign(String algorithm, PrivateKey key, byte[] data) throws SignatureException, InvalidKeyException {
        try {
            Signature signature = Signature.getInstance(algorithm);
            signature.initSign(key);
            signature.update(data);
            return signature.sign();
        } catch (NoSuchAlgorithmException e) {
            throw new UnexpectedException(e);
        }
    }

    @Override
    public void verifySignature(String algorithm, PublicKey key, byte[] data, byte[] signature) throws SignatureException, InvalidKeyException {
        try {
            Signature sig = Signature.getInstance(algorithm);
            sig.initVerify(key);
            sig.update(data);
            if (!sig.verify(signature)) {
                throw new SignatureException();
            }
        } catch (NoSuchAlgorithmException e) {
            throw new UnexpectedException(e);
        }
    }
}
