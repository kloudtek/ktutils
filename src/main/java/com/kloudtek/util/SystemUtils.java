/*
 * Copyright (c) 2013 KloudTek Ltd
 */

package com.kloudtek.util;

import com.kloudtek.util.io.IOUtils;

import java.io.File;
import java.io.IOException;

/**
 * Created by yannick on 03/11/13.
 */
public class SystemUtils {
    private static final String[] SERVER_TYPE_FILES = new String[] {"/etc/server-type"};
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
     *     <li>System Property: server-type</li>
     *     <li>File: /etc/server-type</li>
     * </ul>
     * <p>
     *     This allows to easily have a consistent manner to distinguish between development / test / production servers,
     *     and configure your application appropriately.
     * </p>
     * @return
     */
    public static String getServerType() {
        String type = System.getProperty("server-type");
        while( type == null ) {
            for (String filename : SERVER_TYPE_FILES) {
                File file = new File(filename);
                if( file.exists() && file.canRead()) {
                    try {
                        return IOUtils.toString(file).trim();
                    } catch (IOException e) {
                        //
                    }
                }
            }
        }
        return null;
    }
}
