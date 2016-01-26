/*
 * Copyright (c) 2016 Kloudtek Ltd
 */

package com.kloudtek.util.io;

import java.io.*;
import java.util.logging.Logger;

/**
 * Various I/O relation utilities
 */
public class IOUtils {
    private static final Logger logger = Logger.getLogger(IOUtils.class.getName());
    private static final int DEF_BUFF_SIZE = 10240;
    private static final int DEF_CHAR_BUFF_SIZE = 200;

    public static byte[] toByteArray(InputStream inputStream) throws IOException {
        ByteArrayDataOutputStream buffer = new ByteArrayDataOutputStream();
        copy(inputStream, buffer);
        buffer.close();
        return buffer.toByteArray();
    }

    public static byte[] toByteArray(File file) throws IOException {
        FileInputStream is = new FileInputStream(file);
        try {
            return toByteArray(is);
        } finally {
            close(is);
        }
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

    public static String toString(File file) throws IOException {
        StringWriter buffer = new StringWriter();
        FileReader fileReader = new FileReader(file);
        copy(fileReader, buffer);
        return buffer.toString();
    }

    public static String toString(InputStream inputStream) throws IOException {
        return toString(new InputStreamReader(inputStream));
    }

    public static String toString(Reader reader) throws IOException {
        StringWriter buffer = new StringWriter();
        copy(reader, buffer);
        return buffer.toString();
    }

    /**
     * Close a closeable object, suppressing any resulting exception
     *
     * @param closeable Closeable object, can be null (in which case nothing will be done)
     */
    public static void close(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Throwable e) {
                // needs to be throwable because in some cases closing a classpath resource jar throws ExceptionInInitializerError ?!?!?!?!?!?
            }
        }
    }

    /**
     * Write data to a file
     *
     * @param file File to write data to
     * @param data Data to write
     */
    public static void write(File file, byte[] data) throws IOException {
        FileOutputStream fos = new FileOutputStream(file);
        try {
            fos.write(data);
        } finally {
            close(fos);
        }
    }
}
