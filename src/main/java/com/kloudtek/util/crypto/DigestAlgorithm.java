/*
 * Copyright (c) 2014 Kloudtek Ltd
 */

package com.kloudtek.util.crypto;


/**
 * Enumeration of digest algorithms
 */
public enum DigestAlgorithm {
    MD5(16), SHA1(20, "SHA-1"), SHA256(32, "SHA-256"),
    SHA512(64, "SHA-512");
    private int hashLen;
    private String jceId;

    DigestAlgorithm(int hashLen) {
        init(hashLen, name());
    }

    DigestAlgorithm(int hashLen, String jceId) {
        init(hashLen, jceId);
    }

    private void init(int hashLen, String jceId) {
        this.hashLen = hashLen;
        this.jceId = jceId;
    }

    public String getJceId() {
        return jceId;
    }

    public int getHashLen() {
        return hashLen;
    }

    public static DigestAlgorithm get(String id) {
        id = id.toUpperCase();
        for (DigestAlgorithm alg : values()) {
            if (id.endsWith(alg.jceId) || id.endsWith(alg.name())) {
                return alg;
            }
        }
        return null;
    }
}
