/*
 * Copyright (c) 2014 Kloudtek Ltd
 */

package com.aeontronix.common;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;

/**
 * Used to create a temporary file with restricted permissions (only owner can read/write/execute)
 */
public class TempFile extends File implements Closeable {
    private static final long serialVersionUID = 3421025956189900097L;

    public TempFile(String prefix, String suffix, File directory) throws IOException {
        super(genPath(prefix, suffix, directory));
    }

    public TempFile(String prefix, String suffix) throws IOException {
        super(genPath(prefix, suffix));
    }

    public TempFile(String prefix) throws IOException {
        super(genPath(prefix, "tmp"));
    }

    private static String genPath(String prefix, String suffix) throws IOException {
        return genPath(prefix, suffix, null);
    }

    private static String genPath(String prefix, String suffix, File dir) throws IOException {
        final File tmp = File.createTempFile(prefix, suffix, dir);
        boolean permChange = tmp.setReadable(false, false);
        permChange = permChange && tmp.setWritable(false, false);
        permChange = permChange && tmp.setExecutable(false, false);
        permChange = permChange && tmp.setReadable(true, true);
        permChange = permChange && tmp.setWritable(true, true);
        return tmp.getPath();
    }

    /**
     * Deletes the files, or should it fail to, schedule it for deletion using {@link java.io.File#deleteOnExit()}
     *
     * @throws IOException If an error occurs while deleting the file.
     */
    public void close() throws IOException {
        if (!delete()) {
            deleteOnExit();
        }
    }
}
