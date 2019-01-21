/*
 * Copyright (c) 2014 Kloudtek Ltd
 */

package com.kloudtek.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

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

    public static void set(Class<?> clazz, String name, Object value) {
        try {
            findField(clazz, name).set(null, value);
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

    public static Object get(Class<?> clazz, String name) {
        try {
            return findField(clazz, name).get(null);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public static Field findField(Object obj, String name) {
        return findField(obj.getClass(),name);
    }

    public static Field findField(Class<?> cl, String name) {
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
        throw new IllegalArgumentException("Field " + name + " not found in " + cl.getName());
    }

    public enum ObjectToMapObjectType {
        PASSTHROUGH, OBJECT, PROCESSED
    }
}
