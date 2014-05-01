/*
 * Copyright (c) 2014 Kloudtek Ltd
 */

package com.kloudtek.util.crypto;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.SecretKey;
import java.math.BigInteger;
import java.security.*;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;

/**
 * An implementation of {@link com.kloudtek.util.crypto.CryptoEngine} that doesn't do anything for dev/test use.
 */
public class NoOpCryptoEngine extends CryptoEngine {
    @Override
    public SecretKey generateKey(SymmetricAlgorithm alg, int keysize) {
        return new DummySecretKey(alg.getJceId());
    }

    @Override
    public SecretKey generateHmacKey(DigestAlgorithm algorithm) {
        return new DummySecretKey(algorithm.getJceId());
    }

    @Override
    public KeyPair generateKeyPair(AsymmetricAlgorithm alg, int keysize) {
        return new KeyPair(new DummyPublicKey(alg.getJceId()), new DummyPrivateKey(alg.getJceId()));
    }

    @Override
    public RSAPublicKey readRSAPublicKey(byte[] key) throws InvalidKeySpecException {
        return new DummyRSAPublicKey();
    }

    @Override
    public PrivateKey readRSAPrivateKey(byte[] encodedPriKey) throws InvalidKeySpecException {
        return new DummyPrivateKey("RSA");
    }

    @Override
    public byte[] hmac(DigestAlgorithm algorithm, SecretKey key, byte[] data) throws InvalidKeyException {
        return data;
    }

    @Override
    public byte[] hmacSha1(SecretKey key, byte[] data) throws InvalidKeyException {
        return data;
    }

    @Override
    public byte[] hmacSha256(SecretKey key, byte[] data) throws InvalidKeyException {
        return data;
    }

    @Override
    public byte[] hmacSha512(SecretKey key, byte[] data) throws InvalidKeyException {
        return data;
    }

    @Override
    public byte[] aesEncrypt(byte[] key, byte[] data) throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        return data;
    }

    @Override
    public byte[] aesEncrypt(SecretKey key, byte[] data) throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        return data;
    }

    @Override
    public byte[] aesDecrypt(byte[] key, byte[] data) throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        return data;
    }

    @Override
    public byte[] aesDecrypt(SecretKey key, byte[] data) throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        return data;
    }

    @Override
    public byte[] encrypt(Key key, byte[] data, String alg) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        return data;
    }

    @Override
    public byte[] decrypt(Key key, byte[] data, String alg) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        return data;
    }

    @Override
    public byte[] crypt(Key key, byte[] data, String alg, int mode) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        return data;
    }

    @Override
    public byte[] rsaEncrypt(byte[] key, byte[] data) throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException, InvalidKeySpecException {
        return data;
    }

    @Override
    public byte[] rsaEncrypt(PublicKey key, byte[] data) throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        return data;
    }

    @Override
    public byte[] rsaDecrypt(byte[] key, byte[] data) throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException, InvalidKeySpecException {
        return data;
    }

    @Override
    public byte[] rsaDecrypt(PrivateKey key, byte[] data) throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        return data;
    }

    @Override
    public byte[] rsaSign(DigestAlgorithm digestAlgorithms, PrivateKey key, byte[] data) throws InvalidKeyException, SignatureException {
        return data;
    }

    @Override
    public void rsaVerifySignature(DigestAlgorithm digestAlgorithms, PublicKey key, byte[] data, byte[] signature) throws InvalidKeyException, SignatureException {
        if (!Arrays.equals(data, signature)) {
            throw new SignatureException();
        }
    }

    @Override
    public byte[] sign(String algorithm, PrivateKey key, byte[] data) throws SignatureException, InvalidKeyException {
        return data;
    }

    @Override
    public void verifySignature(String algorithm, PublicKey key, byte[] data, byte[] signature) throws SignatureException, InvalidKeyException {
        if (!Arrays.equals(data, signature)) {
            throw new SignatureException();
        }
    }

    public static class DummyKey implements Key {
        private String alg;

        public DummyKey(String alg) {
            this.alg = alg;
        }

        @Override
        public String getAlgorithm() {
            return alg;
        }

        @Override
        public String getFormat() {
            return "null";
        }

        @Override
        public byte[] getEncoded() {
            return new byte[0];
        }
    }

    public static class DummySecretKey extends DummyKey implements SecretKey {
        public DummySecretKey(String alg) {
            super(alg);
        }
    }

    public static class DummyPublicKey extends DummyKey implements PublicKey {
        public DummyPublicKey(String alg) {
            super(alg);
        }
    }

    public static class DummyPrivateKey extends DummyKey implements PrivateKey {
        public DummyPrivateKey(String alg) {
            super(alg);
        }
    }

    public static class DummyRSAPublicKey extends DummyPublicKey implements RSAPublicKey {
        public DummyRSAPublicKey() {
            super("RSA");
        }

        @Override
        public BigInteger getPublicExponent() {
            return new BigInteger("22");
        }

        @Override
        public BigInteger getModulus() {
            return new BigInteger("243");
        }
    }
}
