/*
 * Copyright (c) 2013 KloudTek Ltd
 */

package com.kloudtek.util;

import java.io.IOException;

/**
 * Created by yannick on 03/11/13.
 */
public class ACTempFile extends TempFile implements AutoCloseable {
    public ACTempFile(String prefix, String suffix) throws IOException {
        super(prefix, suffix);
    }

    public ACTempFile(String prefix) throws IOException {
        super(prefix);
    }
}
