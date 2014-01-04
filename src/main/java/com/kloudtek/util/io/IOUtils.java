/*
 * Copyright (c) 2013 KloudTek Ltd
 */

package com.kloudtek.util.io;

import java.io.*;

public class IOUtils {
    private static final int DEF_BUFF_SIZE = 10240;

    public static byte[] toByteArray(InputStream inputStream) throws IOException {
        ByteArrayDataOutputStream buffer = new ByteArrayDataOutputStream();
        copy(inputStream,buffer);
        buffer.close();
        return buffer.toByteArray();
    }

    public static long copy(final InputStream inputStream, final OutputStream outputStream) throws IOException {
        return copy(inputStream, outputStream, DEF_BUFF_SIZE);
    }

    private static long copy(InputStream inputStream, OutputStream outputStream, int bufSize) throws IOException {
        byte[] buffer = new byte[bufSize];
        long count = 0;
        while (true) {
            int read = inputStream.read(buffer);
            if (read > 0) {
                outputStream.write(buffer, 0, read);
                count += read;
            } else {
                return count;
            }
        }
    }
}
