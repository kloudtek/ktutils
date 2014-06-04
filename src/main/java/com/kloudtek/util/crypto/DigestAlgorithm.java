/*
 * Copyright (c) 2014 Kloudtek Ltd
 */

package com.kloudtek.util.crypto;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;

/**
 * Enumeration of digest algorithms
 */
@XmlEnum
public enum DigestAlgorithm {
    @XmlEnumValue("md5")MD5(16), @XmlEnumValue("sha1")SHA1(20, "SHA-1"), @XmlEnumValue("sha256")SHA256(32, "SHA-256"),
    @XmlEnumValue("sha512")SHA512(64, "SHA-512");
    private int hashLen;
    private String jceId;
    private String jceHmacId;

    DigestAlgorithm(int hashLen) {
        init(hashLen, name());
    }

    DigestAlgorithm(int hashLen, String jceId) {
        init(hashLen, jceId);
    }

    private void init(int hashLen, String jceId) {
        this.hashLen = hashLen;
        this.jceId = jceId;
        jceHmacId = "Hmac" + jceId;
    }

    public String getJceId() {
        return jceId;
    }

    public int getHashLen() {
        return hashLen;
    }

    public static DigestAlgorithm get(String id) {
        for (DigestAlgorithm alg : values()) {
            if (alg.jceId.equals(id) || alg.jceHmacId.equals(id)) {
                return alg;
            }
        }
        return null;
    }
}
