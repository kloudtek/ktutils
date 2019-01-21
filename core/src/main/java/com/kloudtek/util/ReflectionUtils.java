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
    private static final HashSet<Class<?>> OBJ2MAP_PASSTHROUGH = new HashSet<>(Arrays.asList(String.class,
            Number.class, Boolean.class, Date.class, Collection.class));
    private static final HashSet<String> OBJ2MAP_METHODBLACKLIST = new HashSet<>(Arrays.asList("getClass"));


    public static String toString(Method method) {
        return "Method " + method.getDeclaringClass().getName() + "#" + method.getName();
    }

    public static Map<String, Object> objectToMap(Object object) throws InvocationTargetException, IllegalAccessException {
        if (object == null) {
            return null;
        }
        Map<String, Object> map = new HashMap<String, Object>();
        for (Method method : object.getClass().getMethods()) {
            final String methodName = method.getName();
            String key = null;
            if (! OBJ2MAP_METHODBLACKLIST.contains(methodName) && method.getParameters().length == 0 && !method.getReturnType().getName().equals("void")) {
                if (methodName.startsWith("get") && methodName.length() > 3) {
                    key = getJBFieldName(methodName, 3);
                } else if (methodName.startsWith("is") && methodName.length() > 3) {
                    key = getJBFieldName(methodName, 2);
                }
                if (key != null) {
                    Object value = method.invoke(object);
                    map.put(key, objectToMapConvertObject(value));
                }
            }
        }
        return map;
    }

    private static Object objectToMapConvertObject(Object val) throws InvocationTargetException, IllegalAccessException {
        if( val == null ) {
            return null;
        }
        Class<?> cl = val.getClass();
        for (Class<?> pt : OBJ2MAP_PASSTHROUGH) {
            if( pt.isAssignableFrom(cl)) {
                return val;
            }
        }
        if (cl.isPrimitive() || cl.isEnum() ) {
            return val;
        } else if (val instanceof Optional) {
            return ((Optional) val).isPresent() ? objectToMapConvertObject(((Optional) val).get()) : null;
        } else {
            return objectToMap(val);
        }
    }

    private static String getJBFieldName(String methodName, int index) {
        char c = Character.toLowerCase(methodName.charAt(index));
        if (methodName.length() > (index + 1)) {
            return c + methodName.substring(index + 1);
        } else {
            return new String(new char[]{c});
        }
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
}
