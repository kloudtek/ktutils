/*
 * Copyright (c) 2014 Kloudtek Ltd
 */

package com.aeontronix.common;

import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * Java EE related utilities.
 */
public class JEEUtils {
    /**
     * Retrieve the module's name (using JNDI lookup of java:module/ModuleName. Requires Java EE 6
     *
     * @return Module name
     */
    public static String getModuleName() {
        try {
            return (String) new InitialContext().lookup("java:module/ModuleName");
        } catch (NamingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Retrieve the application's name (using JNDI lookup of java:app/AppName. Requires Java EE 6
     *
     * @return Application name
     */
    public static String getAppName() {
        try {
            return (String) new InitialContext().lookup("java:app/AppName");
        } catch (NamingException e) {
            throw new RuntimeException(e);
        }
    }
}
