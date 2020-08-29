/*
 * Copyright (c) 2016 Kloudtek Ltd
 */

package com.aeontronix.commons.io;

import com.aeontronix.commons.FileUtils;

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

    public static byte[] toByteArray(DataGenerator dataGenerator) throws IOException {
        ByteArrayDataOutputStream buffer = new ByteArrayDataOutputStream();
        dataGenerator.generateData(buffer);
        close(buffer);
        return buffer.toByteArray();
    }

    public static long copy(final InputStream inputStream, final OutputStream outputStream) throws IOException {
        return copy(inputStream, outputStream, DEF_BUFF_SIZE);
    }

    @SuppressWarnings("Duplicates")
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

    @SuppressWarnings("Duplicates")
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

    public static String toString(InputStream inputStream) throws IOException {
        return toString(inputStream,"UTF-8");
    }

    public static String toString(InputStream inputStream, String encoding) throws IOException {
        return toString(new InputStreamReader(inputStream,encoding));
    }

    public static String toString(Reader reader) throws IOException {
        StringWriter buffer = new StringWriter();
        copy(reader, buffer);
        return buffer.toString();
    }

    /**
     * Close a closeable object, suppressing any resulting exception
     *
     * @param closeables Closeable objects which can be null (in which case nothing will be done)
     */
    public static void close(Closeable... closeables) {
        for (Closeable closeable : closeables) {
            if (closeable != null) {
                try {
                    closeable.close();
                } catch (Throwable e) {
                    // needs to be throwable because in some cases closing a classpath resource jar throws ExceptionInInitializerError ?!?!?!?!?!?
                }
            }
        }
    }

    public static String toString(File file) throws IOException {
        return FileUtils.toString(file);
    }

    public static byte[] toByteArray(File file) throws IOException {
        return FileUtils.toByteArray(file);
    }

    /**
     * Write data to a file
     *
     * @param file File to write data to
     * @param data Data to write
     */
    public static void write(File file, byte[] data) throws IOException {
        FileUtils.write(file,data);
    }

    public interface DataGenerator {
        void generateData(OutputStream os) throws IOException;
    }
}
