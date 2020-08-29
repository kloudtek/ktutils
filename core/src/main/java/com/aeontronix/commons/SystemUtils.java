/*
 * Copyright (c) 2014 Kloudtek Ltd
 */

package com.aeontronix.commons;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Various system level utility functions
 */
public class SystemUtils {
    private static final String[] SERVER_TYPE_FILES = new String[]{"/etc/server-type"};
    public static final boolean android;

    static {
        android = System.getProperty("java.vm.name").equalsIgnoreCase("Dalvik");
    }

    public static boolean isAndroid() {
        return android;
    }

    public static boolean hasClass(String className) {
        try {
            Class.forName(className);
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    /**
     * <p>Returns the server type.</p>
     * <p>This value is obtained by trying to find it from the following locations and returning the first one found</p>
     * <ul>
     * <li>System Property: server-type</li>
     * <li>File: /etc/server-type</li>
     * </ul>
     * <p>
     * This allows to easily have a consistent manner to distinguish between development / test / production servers,
     * and configure your application appropriately.
     * </p>
     *
     * @return server type
     */
    public static String getServerType() {
        String type = System.getProperty("server-type");
        while (type == null) {
            for (String filename : SERVER_TYPE_FILES) {
                File file = new File(filename);
                if (file.exists() && file.canRead()) {
                    try {
                        BufferedReader fileReader = new BufferedReader(new FileReader(file));
                        try {
                            return fileReader.readLine().trim();
                        } finally {
                            fileReader.close();
                        }
                    } catch (IOException e) {
                        //
                    }
                }
            }
        }
        return null;
    }
}
