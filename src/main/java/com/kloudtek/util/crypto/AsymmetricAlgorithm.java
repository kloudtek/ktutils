/*
 * Copyright (c) 2014 Kloudtek Ltd
 */

package com.kloudtek.util.crypto;

/**
 * Created by yannick on 09/11/13.
 */
public enum AsymmetricAlgorithm {
    RSA("RSA", "RSA/ECB/PKCS1PADDING");
    private String jceId;
    private String cryptAlg;

    AsymmetricAlgorithm() {
        jceId = name();
    }

    AsymmetricAlgorithm(String jceId, String cryptAlg) {
        this.jceId = jceId;
        this.cryptAlg = cryptAlg;
    }

    AsymmetricAlgorithm(String jceId) {
        this.jceId = jceId != null ? jceId : name();
    }

    public String getJceId() {
        return jceId;
    }

    public String getCryptAlg() {
        return cryptAlg;
    }
}
