/*
 * Copyright (c) 2013 KloudTek Ltd
 */

package com.kloudtek.util;

import java.io.IOException;

/**
 * Created by yannick on 03/11/13.
 */
public class ACTempDir extends TempDir implements AutoCloseable {
    public ACTempDir(String prefix, String suffix) throws IOException {
        super(prefix, suffix);
    }

    public ACTempDir(String prefix) throws IOException {
        super(prefix);
    }
}
