/*
 * Copyright (c) 2013 KloudTek Ltd
 */

package com.kloudtek.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Various reflection related utility functions
 */
public class ReflectionUtils {
    public static String toString(Method method) {
        return "Method " + method.getDeclaringClass().getName() + "#" + method.getName();
    }

    public static String toString(Field field) {
        return field.getDeclaringClass().getName() + "#" + field.getName();
    }

    public static Object invoke(Method method, Object obj) throws Throwable {
        try {
            return method.invoke(obj);
        } catch (IllegalAccessException e) {
            throw new IllegalAccessException("Method " + toString(method) + " cannot be invoked: " + e.getMessage());
        } catch (InvocationTargetException e) {
            throw e.getCause() != null ? e.getCause() : e;
        }
    }

    public static void set(Object obj, String name, Object value) {
        try {
            findField(obj, name).set(obj, value);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public static Object get(Object obj, String name) {
        try {
            return findField(obj, name).get(obj);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private static Field findField(Object obj, String name) {
        Class<?> cl = obj.getClass();
        while (cl != null) {
            try {
                Field field = cl.getDeclaredField(name);
                if (!field.isAccessible()) {
                    field.setAccessible(true);
                }
                return field;
            } catch (NoSuchFieldException e) {
                cl = cl.getSuperclass();
            }
        }
        throw new IllegalArgumentException("Field " + name + " not found in " + obj.getClass().getName());
    }
}
