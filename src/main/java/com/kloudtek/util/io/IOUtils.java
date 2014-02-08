/*
 * Copyright (c) 2014 Kloudtek Ltd
 */

package com.kloudtek.util.io;

import java.io.*;

/**
 * Various I/O relation utilities
 */
public class IOUtils {
    private static final int DEF_BUFF_SIZE = 10240;
    private static final int DEF_CHAR_BUFF_SIZE = 200;

    public static byte[] toByteArray(InputStream inputStream) throws IOException {
        ByteArrayDataOutputStream buffer = new ByteArrayDataOutputStream();
        copy(inputStream, buffer);
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

    public static long copy(final Reader reader, final Writer writer) throws IOException {
        return copy(reader, writer, DEF_CHAR_BUFF_SIZE);
    }

    private static long copy(final Reader reader, final Writer writer, int bufSize) throws IOException {
        char[] buffer = new char[bufSize];
        long count = 0;
        while (true) {
            int read = reader.read(buffer);
            if (read > 0) {
                writer.write(buffer, 0, read);
                count += read;
            } else {
                return count;
            }
        }
    }

    public static long byteArrayToLong(byte[] data) {
        if (data == null || data.length != 8) {
            throw new IllegalArgumentException();
        }
        return (((long) data[0] << 56) +
                ((long) (data[1] & 255) << 48) +
                ((long) (data[2] & 255) << 40) +
                ((long) (data[3] & 255) << 32) +
                ((long) (data[4] & 255) << 24) +
                ((data[5] & 255) << 16) +
                ((data[6] & 255) << 8) +
                ((data[7] & 255) << 0));
    }

    public static byte[] longToByteArray(long value) {
        byte[] data = new byte[8];
        data[0] = (byte) (value >>> 56);
        data[1] = (byte) (value >>> 48);
        data[2] = (byte) (value >>> 40);
        data[3] = (byte) (value >>> 32);
        data[4] = (byte) (value >>> 24);
        data[5] = (byte) (value >>> 16);
        data[6] = (byte) (value >>> 8);
        data[7] = (byte) (value >>> 0);
        return data;
    }

    public static String toString(File file) throws IOException {
        StringWriter buffer = new StringWriter();
        FileReader fileReader = new FileReader(file);
        copy(fileReader, buffer);
        return buffer.toString();
    }
}
