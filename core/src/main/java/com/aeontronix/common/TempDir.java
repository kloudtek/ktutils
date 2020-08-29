/*
 * Copyright (c) 2014 Kloudtek Ltd
 */

package com.aeontronix.common;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;

/**
 * Used to create a temporary directory with restricted permissions (only owner can read/write/execute).
 */
public class TempDir extends File implements Closeable {
    private static final long serialVersionUID = -3906256837550665841L;

    public TempDir(String prefix, String suffix, File directory) throws IOException {
        super(create(prefix, suffix, directory));
    }

    public TempDir(String prefix, String suffix) throws IOException {
        super(create(prefix, suffix));
    }

    public TempDir(String prefix) throws IOException {
        super(create(prefix, ".tmp"));
    }

    /**
     * Generate the temporary directory in the system tmp dir
     *
     * @param prefix Directory prefix
     * @param suffix Directory suffix
     * @return Path to the temporary directory
     * @throws IOException If an error occurs creating the temp directory
     */
    private static String create(String prefix, String suffix) throws IOException {
        return create(prefix, suffix, null);
    }

    /**
     * Generate the temporary directory
     *
     * @param prefix    Directory prefix
     * @param suffix    Directory suffix
     * @param directory Directory where temp dir should be created in (null for system tmp dir)
     * @return Path to the temporary directory
     * @throws IOException If an error occurs creating the temp directory
     */
    private static String create(String prefix, String suffix, File directory) throws IOException {
        File tmp = null;
        try {
            tmp = File.createTempFile(prefix, suffix, directory);
            FileUtils.delete(tmp);
            if (!tmp.mkdir()) {
                throw new IOException("Unable to create temp dir: " + tmp.getPath());
            }
            boolean permChange = tmp.setReadable(false, false);
            permChange = permChange && tmp.setWritable(false, false);
            permChange = permChange && tmp.setExecutable(false, false);
            permChange = permChange && tmp.setReadable(true, true);
            permChange = permChange && tmp.setWritable(true, true);
            permChange = permChange && tmp.setExecutable(true, true);
            return tmp.getPath();
        } catch (IOException e) {
            try {
                FileUtils.delete(tmp);
            } catch (IOException ex) {
                throw new IOException("An error occured while creating temporary directory, and was unable to delete it afterwards",ex);
            }
            throw e;
        } catch (RuntimeException e) {
            try {
                FileUtils.delete(tmp);
            } catch (IOException ex) {
                throw new IOException("An error occured while creating temporary directory, and was unable to delete it afterwards",ex);
            }
            throw e;
        }
    }

    /**
     * Deletes the directory and all files inside it. If any file delete fails, they will be scheduled for deletion using {@link java.io.File#deleteOnExit()}
     */
    public void close() {
        try {
            FileUtils.delete(true,(File) this);
        } catch (IOException e) {
            //
        }
    }

    public static TempDir createMavenTmpDir() throws IOException {
        return createMavenTmpDir(null);
    }

    public static TempDir createMavenTmpDir(String altPath) throws IOException {
        File dir = new File("target");
        if (!dir.exists()) {
            if (altPath != null) {
                dir = new File(altPath.replace("/", File.separator) + File.separator + altPath);
            }
            if (altPath == null || !dir.exists()) {
                dir = new File(".");
            }
        }
        return new TempDir("_tmp",".tmp",dir);
    }
}
