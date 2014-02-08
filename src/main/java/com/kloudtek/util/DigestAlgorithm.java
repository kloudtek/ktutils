/*
 * Copyright (c) 2013 KloudTek Ltd
 */

package com.kloudtek.util;

/**
 * Enumeration of digest algorithms
 */
public enum DigestAlgorithm {
    MD5(16), SHA1(20, "SHA-1"), SHA256(32, "SHA-256"), SHA512(64, "SHA-512");
    private int hashLen;
    private String jceId;

    DigestAlgorithm(int hashLen) {
        this.hashLen = hashLen;
        jceId = name();
    }

    DigestAlgorithm(int hashLen, String jceId) {
        this.hashLen = hashLen;
        this.jceId = jceId;
    }

    public String getJceId() {
        return jceId;
    }

    public int getHashLen() {
        return hashLen;
    }
}
