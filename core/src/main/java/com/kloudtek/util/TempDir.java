/*
 * Copyright (c) 2014 Kloudtek Ltd
 */

package com.kloudtek.util;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;

/**
 * Used to create a temporary directory with restricted permissions (only owner can read/write/execute).
 */
public class TempDir extends File implements Closeable {
    private static final long serialVersionUID = -3906256837550665841L;

    public TempDir(String prefix, String suffix) throws IOException {
        super(create(prefix, suffix));
    }

    public TempDir(String prefix) throws IOException {
        super(create(prefix, "tmp"));
    }

    /**
     * Generate the temporary directory
     *
     * @param prefix Directory prefix
     * @param suffix Directory suffix
     * @return Path to the temporary directory
     * @throws IOException If an error occurs creating the temp directory
     */
    private static String create(String prefix, String suffix) throws IOException {
        final File tmp = File.createTempFile(prefix, suffix);
        if (!tmp.delete()) {
            throw new IOException("Unable to delete temp file: " + tmp.getPath());
        }
        if (!tmp.mkdir()) {
            throw new IOException("Unable to create temp dir: " + tmp.getPath());
        }
        boolean permChange = tmp.setReadable(false, false);
        permChange = permChange && tmp.setWritable(false, false);
        permChange = permChange && tmp.setExecutable(false, false);
        permChange = permChange && tmp.setReadable(true, true);
        permChange = permChange && tmp.setWritable(true, true);
        permChange = permChange && tmp.setExecutable(true, true);
        if (!permChange) {
            throw new IOException("Unable to change permissions on temp dir: " + tmp.getPath());
        }
        return tmp.getPath();
    }

    /**
     * Deletes the directory and all files inside it. If any file delete fails, they will be scheduled for deletion using {@link java.io.File#deleteOnExit()}
     *
     * @throws IOException If an error occurs while deleting the directory.
     */
    public void close() throws IOException {
        delete(this);
    }

    /**
     * Recursively delete all files
     *
     * @param file
     */
    private static void delete(File file) {
        if (file.isDirectory()) {
            final File[] childs = file.listFiles();
            if (childs != null) {
                for (File child : childs) {
                    delete(child);
                }
            }
        }
        if (!file.delete()) {
            file.deleteOnExit();
        }
    }
}
