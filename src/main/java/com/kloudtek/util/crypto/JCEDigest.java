/*
 * Copyright (c) 2014 Kloudtek Ltd
 */

package com.kloudtek.util.crypto;

import java.nio.ByteBuffer;
import java.security.DigestException;
import java.security.MessageDigest;

/**
 * Created by yannick on 19/08/2014.
 */
public class JCEDigest implements Digest {
    private MessageDigest messageDigest;

    public JCEDigest(MessageDigest messageDigest) {
        this.messageDigest = messageDigest;
    }

    @Override
    public byte[] digest() {
        return messageDigest.digest();
    }

    @Override
    public int digest(byte[] buf, int offset, int len) throws DigestException {
        return messageDigest.digest(buf, offset, len);
    }

    @Override
    public byte[] digest(byte[] input) {
        return messageDigest.digest(input);
    }

    @Override
    public void reset() {
        messageDigest.reset();
    }

    @Override
    public int getDigestLength() {
        return messageDigest.getDigestLength();
    }

    @Override
    public String getAlgorithm() {
        return messageDigest.getAlgorithm();
    }

    @Override
    public void update(ByteBuffer input) {
        messageDigest.update(input);
    }

    @Override
    public void update(byte[] input) {
        messageDigest.update(input);
    }

    @Override
    public void update(byte[] input, int offset, int len) {
        messageDigest.update(input, offset, len);
    }

    @Override
    public void update(byte input) {
        messageDigest.update(input);
    }
}
