/*
 * Copyright (c) 2014 Kloudtek Ltd
 */

package com.kloudtek.util.crypto;

/**
 * Created by yannick on 08/02/2014.
 */
public class JCECryptoEngineTest extends AbstractCryptoEngineTest {
    public JCECryptoEngineTest() {
        super(new JCECryptoEngine());
    }
}
