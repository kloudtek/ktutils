/*
 * Copyright (c) 2014 Kloudtek Ltd
 */

package com.kloudtek.util.crypto;

import java.security.Key;
import java.security.KeyPair;
import java.security.PublicKey;

/**
 * Interface for a keystore that run in an unattended applications (ie. a server application)
 */
public interface UnattendedKeyStore {
    Object getEntry(String id);

    KeyPair getKeyPair(String id);

    Key getKey(String id);

    PublicKey getPublicKey(String id);

    void importKey(String id, Key key);

    void importKeyPair(String id, KeyPair key);

    KeyPair createRSAKeyPair(String id, int keySize);

    void removeKey(String id);
}
