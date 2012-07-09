package com.kloudtek.util;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;

/**
 * Used to create a temporary file with owner-only r/w permission
 */
public class TempFile extends File implements Closeable {
    public TempFile(String name) throws IOException {
        super(create(name));
    }

    private static String create(String name) throws IOException {
        final File tmp = File.createTempFile(name, "tmp");
        boolean permChange = tmp.setReadable(false,false);
        permChange = permChange && tmp.setWritable(false, false);
        permChange = permChange && tmp.setExecutable(false, false);
        permChange = permChange && tmp.setReadable(true, true);
        permChange = permChange && tmp.setWritable(true, true);
        if( ! permChange ) {
            throw new IOException("Unable to change permissions on temp dir: "+tmp.getPath());
        }
        return tmp.getPath();
    }

    @Override
    public void close() throws IOException {
        if( ! delete() ) {
            deleteOnExit();
        }
    }
}
