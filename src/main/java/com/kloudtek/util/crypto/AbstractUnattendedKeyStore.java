/*
 * Copyright (c) 2014 Kloudtek Ltd
 */

package com.kloudtek.util.crypto;

import java.security.KeyPair;

/**
 * Created by yannick on 21/04/2014.
 */
public abstract class AbstractUnattendedKeyStore implements UnattendedKeyStore {
    @Override
    public KeyPair createRSAKeyPair(String id, int keySize) {
        KeyPair keyPair = CryptoUtils.generateKeyPair(AsymmetricAlgorithm.RSA, keySize);
        importKeyPair(id, keyPair);
        return keyPair;
    }
}
