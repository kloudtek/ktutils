/*
 * Copyright (c) 2013 KloudTek Ltd
 */

package com.kloudtek.util;

/**
 * Created by yannick on 03/11/13.
 */
public class SystemUtils {
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
}
