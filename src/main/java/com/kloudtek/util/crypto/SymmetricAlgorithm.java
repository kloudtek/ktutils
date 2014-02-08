/*
 * Copyright (c) 2013 KloudTek Ltd
 */

package com.kloudtek.util.crypto;

/**
 * Created by yannick on 09/11/13.
 */
public enum SymmetricAlgorithm {
    AES;
    private String jceId;

    SymmetricAlgorithm() {
        jceId = name();
    }

    SymmetricAlgorithm(String jceId) {
        this.jceId = jceId != null ? jceId : name();
    }

    public String getJceId() {
        return jceId;
    }
}
