package com.kloudtek.util.crypto;

import java.security.Key;
import java.security.KeyPair;

/**
 * Interface for a keystore that run in an unattended applications (ie. a server application)
 */
public interface UnattendedKeyStore {
    Object getEntry(String id);

    Object getKeyPair(String id);

    Key getKey(String id);

    void importKey(String id, Key key);

    void importKeyPair(String id, KeyPair key);

    KeyPair createRSAKeyPair(String id, int keySize);

    void removeKey(String id);
}
