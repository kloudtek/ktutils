/*
 * Copyright (c) 2014 Kloudtek Ltd
 */

package com.kloudtek.util.crypto;

import java.security.Key;
import java.security.KeyPair;
import java.security.PublicKey;
import java.util.HashMap;

/**
 * Created by yannick on 21/04/2014.
 */
public class UnattendedKeyStoreMemoryImpl extends AbstractUnattendedKeyStore {
    public UnattendedKeyStoreMemoryImpl(CryptoEngine cryptoEngine) {
        super(cryptoEngine);
    }

    private HashMap<String, Object> keys = new HashMap<String, Object>();

    public HashMap<String, Object> getKeys() {
        return keys;
    }

    @Override
    public Object getEntry(String id) {
        return keys.get(id);
    }

    @Override
    public KeyPair getKeyPair(String id) {
        return (KeyPair) keys.get(id);
    }

    @Override
    public Key getKey(String id) {
        return (Key) keys.get(id);
    }

    @Override
    public PublicKey getPublicKey(String id) {
        return (PublicKey) keys.get(id);
    }

    @Override
    public void importKey(String id, Key key) {
        keys.put(id, key);
    }

    @Override
    public void importKeyPair(String id, KeyPair keyPair) {
        keys.put(id, keyPair);
    }

    @Override
    public void removeKey(String id) {
        keys.remove(id);
    }
}
