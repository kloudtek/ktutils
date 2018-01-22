package com.kloudtek.util;

import com.kloudtek.util.io.IOUtils;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

public class FileUtils {
    public static void mkdir(File file) throws IOException {
        if( ! file.mkdir() ) {
            throw new IOException("Unable to create directory: "+file.getPath());
        }
    }

    public static void mkdirs(File file) throws IOException {
        if( ! file.mkdirs() ) {
            throw new IOException("Unable to create directory: "+file.getPath());
        }
    }

    public static void copy( File src, File dst ) throws IOException {
        FileInputStream is = null;
        FileOutputStream os = null;
        try {
            is = new FileInputStream(src);
            os = new FileOutputStream(dst);
            IOUtils.copy(is,os);
        } finally {
            IOUtils.close(is,os);
        }
    }

    /**
     * Copy the files in the specified directory to another directory
     * @param dir Source directory
     * @param destination Destination Directory
     */
    public static void copyFilesInDir(File dir, File destination) throws IOException {
        for (File file : listFileInDir(dir)) {
            File destFile = new File(destination, file.getName());
            if( file.isDirectory() ) {
                mkdir(destFile);
                copyFilesInDir(file,destFile);
            } else {
                copy(file,destFile);
            }
        }
    }

    public static byte[] toByteArray(File file) throws IOException {
        FileInputStream is = new FileInputStream(file);
        try {
            return IOUtils.toByteArray(is);
        } finally {
            IOUtils.close(is);
        }
    }

    public static void checkFileIsDirectory(File... files) throws IOException {
        for (File file : files) {
            if( ! file.exists() ) {
                throw new IOException("File "+file.getPath()+" doesn't exist");
            }
            if( ! file.isDirectory() ) {
                throw new IOException("File "+file.getPath()+" isn't a directory");
            }
        }
    }

    public static File[] listFileInDir(File directory) throws IOException {
        File[] files = directory.listFiles();
        if( files == null ) {
            throw new IOException("Unable to list files in directory: "+directory.getPath());
        }
        return files;
    }

    /**
     * List all files/directories in a directory
     *
     * @param directory    Directory to list files in
     * @param recursive    If the search should be done recursively
     * @param includeFiles If files should be included in the list
     * @param includeDirs  If directories should be included in the list
     * @return List of files/directories
     * @throws IOException If an error listing the file occurs
     */
    public static Set<String> listAllFilesNames(File directory, boolean recursive, boolean includeFiles, boolean includeDirs) throws IOException {
        HashSet<String> filepaths = new HashSet<String>();
        recursiveFileNameList(filepaths, directory, null, recursive, includeFiles, includeDirs);
        return filepaths;
    }

    private static void recursiveFileNameList(HashSet<String> filepaths, File file, String path, boolean recursive, boolean includeFiles, boolean includeDirs) throws IOException {
        if (file.isDirectory()) {
            if (path == null || recursive) {
                File[] files = file.listFiles();
                if (files == null) {
                    throw new IOException("Unable to list files in directory: " + file.getPath());
                }
                for (File f : files) {
                    recursiveFileNameList(filepaths, f, path != null ? path + File.separator + f.getName() : f.getName(), recursive, includeFiles, includeDirs);
                }
            }
            if (path != null && includeDirs) {
                filepaths.add(path);
            }
        } else {
            if (includeFiles) {
                filepaths.add(path);
            }
        }
    }

    public static String toString(File file) throws IOException {
        return toString(file, "UTF-8");
    }

    public static String toString(File file, String encoding) throws IOException {
        StringWriter buffer = new StringWriter();
        InputStreamReader fileReader = null;
        try {
            fileReader = new InputStreamReader(new FileInputStream(file), encoding);
            IOUtils.copy(fileReader, buffer);
            return buffer.toString();
        } finally {
            IOUtils.close(buffer);
            IOUtils.close(fileReader);
        }
    }

    /**
     * Write string to a file using UTF-8 encoding
     *
     * @param file File to write data to
     * @param text Text to write
     */
    public static void write(File file, String text) throws IOException {
        write(file, text, "UTF-8");
    }

    /**
     * Write string to a file
     *
     * @param file     File to write text to
     * @param text     Text to write
     * @param encoding Character encoding
     */
    public static void write(File file, String text, String encoding) throws IOException {
        FileOutputStream fos = new FileOutputStream(file);
        try {
            fos.write(text.getBytes(encoding));
        } finally {
            IOUtils.close(fos);
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
            IOUtils.close(fos);
        }
    }

    /**
     * Same as invoking {@link #delete(boolean, File...)} with deleteOnExit set to true)}
     *
     * @param file File to delete
     */
    public static void delete(File... file) throws IOException {
        delete(true, file);
    }

    /**
     * Delete files/directories
     *
     * @param files        files or directories to delete
     * @param deleteOnExit If true and deleting a file fails, it will schedule the file to delete on JVM exit rather than throw an exception
     */
    public static void delete(boolean deleteOnExit, File... files) throws IOException {
        for (File file : files) {
            if (file != null && file.exists()) {
                if (file.isDirectory()) {
                    File[] childrens = file.listFiles();
                    if (childrens == null) {
                        throw new IOException("Unable to list files in " + file.getPath());
                    }
                    for (File f : childrens) {
                        delete(deleteOnExit, f);
                    }
                }
                if (!file.delete()) {
                    if (deleteOnExit) {
                        file.deleteOnExit();
                    } else {
                        throw new IOException("Unable to delete: " + file.getPath());
                    }
                }
            }
        }
    }
}
