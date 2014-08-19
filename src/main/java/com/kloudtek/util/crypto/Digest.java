/*
 * Copyright (c) 2014 Kloudtek Ltd
 */

package com.kloudtek.util.crypto;

import java.nio.ByteBuffer;
import java.security.DigestException;

/**
 * Created by yannick on 19/08/2014.
 */
public interface Digest {
    byte[] digest();

    int digest(byte[] buf, int offset, int len) throws DigestException;

    byte[] digest(byte[] input);

    void reset();

    int getDigestLength();

    String getAlgorithm();

    void update(ByteBuffer input);

    void update(byte[] input);

    void update(byte[] input, int offset, int len);

    void update(byte input);
}
