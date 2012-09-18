/*
 * Copyright (c) Kloudtek Ltd 2012.
 */

package com.kloudtek.util;

import java.lang.reflect.Method;

/**
 * Provides Based64 Encoding and Decoding.
 * Please note that rather than reimplementing Base64 for the nth time, this look lookup an available implementation in
 * the classpath, and use it.
 * At this time, it supports both apache commons codec, and the java.util.prefs.Base64 jdk class (using reflection to
 * use it, as it is not publicly available, thus this might fail if the security manager doesn't allow such reflection method
 * to be accessed).
 */
public class Base64 {
    private static Class<?> jdkClass;
    private static Class<?> commonsCodec;
    private static Type type;
    private static Method jdkEncodeMethod;
    private static Method encodeMethod;
    private static Method decodeMethod;
    private static Method jdkDecodeMethod;
    private static Method ccEncodeMethod;
    private static Method ccDecodeMethod;

    static {
        boolean match = false;
        try {
            commonsCodec = Class.forName("org.apache.commons.codec.binary.Base64");
            ccEncodeMethod = commonsCodec.getDeclaredMethod("encodeBase64", byte[].class, boolean.class);
            ccDecodeMethod = commonsCodec.getDeclaredMethod("decodeBase64", String.class);
            setType(Type.APACHECOMMONS);
        } catch (Exception e) {
            //
        }
        try {
            jdkClass = Class.forName("java.util.prefs.Base64");
            jdkEncodeMethod = jdkClass.getDeclaredMethod("byteArrayToBase64", byte[].class);
            jdkEncodeMethod.setAccessible(true);
            jdkDecodeMethod = jdkClass.getDeclaredMethod("base64ToByteArray", String.class);
            jdkDecodeMethod.setAccessible(true);
            setType(Type.JDK);
        } catch (Exception e) {
            //
        }
        if (type == null) {
            throw new RuntimeException("No Base64 implementation found");
        }
    }

    public static void setType(Type type) {
        Base64.type = type;
    }


    public static String encode(String data) {
        return encode(data.getBytes());
    }

    public static String encode(byte[] data) {
        try {
            switch (type) {
                case JDK:
                    return (String) jdkEncodeMethod.invoke(null, (Object) data);
                case APACHECOMMONS:
                    return new String((byte[]) ccEncodeMethod.invoke(null, data, false));
                default:
                    throw new RuntimeException("Unsupported type");
            }
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new UnexpectedException(e);
        }
    }

    public static byte[] decode(String data) {
        try {
            switch (type) {
                case JDK:
                    return (byte[]) jdkDecodeMethod.invoke(null, data);
                case APACHECOMMONS:
                    return (byte[]) ccDecodeMethod.invoke(null, data);
                default:
                    throw new RuntimeException("Unsupported type");
            }
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new UnexpectedException(e);
        }
    }

    public enum Type {
        JDK, APACHECOMMONS
    }
}
