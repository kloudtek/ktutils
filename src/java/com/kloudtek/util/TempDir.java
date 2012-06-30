package com.kloudtek.util;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

public class TempDir extends File implements Closeable {
    public TempDir(String name) throws IOException {
        super(genPath(name));
    }

    private static String genPath( String name ) throws IOException {
        final File tmp = File.createTempFile(name, "tmp");
        if( ! tmp.delete() ) {
            throw new IOException("Unable to delete temp file: "+tmp.getPath());
        }
        if( ! tmp.mkdir() ) {
            throw new IOException("Unable to create temp dir: "+tmp.getPath());
        }
        return tmp.getPath();
    }

    @Override
    public void close() throws IOException {
        del(this);
    }

    private static void del(File file) {
        if( file.isDirectory() ) {
            final File[] childs = file.listFiles();
            if( childs != null ) {
                for (File child : childs) {
                    del(child);
                }
            }
        }
        if( ! file.delete() ) {
            file.deleteOnExit();
        }
    }
}
