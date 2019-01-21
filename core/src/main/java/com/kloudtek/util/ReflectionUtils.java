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
            Number.class, Boolean.class, Date.class, Collection.class, Map.class));
    private static final HashSet<String> OBJ2MAP_METHODBLACKLIST = new HashSet<>(Arrays.asList("getClass"));

    public static String toString(Method method) {
        return "Method " + method.getDeclaringClass().getName() + "#" + method.getName();
    }

    /**
     * Convert an object into a structure designed to serializable to json
     * @param object object
     * @return Json object
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public static Object objectToJson(Object object) throws InvocationTargetException, IllegalAccessException {
        LinkedList<Object> processed = new LinkedList<>();
        return objectToMapInternal(object,processed);
    }

    private static Map<String, Object> objectToMapInternal(Object object, LinkedList<Object> processed) throws IllegalAccessException, InvocationTargetException {
        if (object == null) {
            return null;
        }
        processed.add(object);
        Map<String, Object> map = new HashMap<>();
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
                    if( value instanceof Optional ) {
                        if( ((Optional) value).isPresent()) {
                            value = ((Optional) value).get();
                        } else {
                            value = null;
                        }
                    }
                    if( value != null ) {
                        ObjectToMapObjectType type = objectToMapConvertObjectGetType(value, processed);
                        if( type != null ) {
                            switch (type) {
                                case PASSTHROUGH:
                                    map.put(key, value);
                                    break;
                                case OBJECT:
                                    map.put(key,objectToMapInternal(value, processed));
                                    break;
                            }
                        } else {
                            map.put(key, null);
                        }
                    } else {
                        map.put(key, null);
                    }
                }
            }
        }
        return map;
    }

    private static boolean isSame(LinkedList<Object> processed, Object value) {
        for (Object p : processed) {
            if( p == value ) {
               return true;
            }
        }
        return false;
    }

    private static ObjectToMapObjectType objectToMapConvertObjectGetType(Object val, LinkedList<Object> processed) {
        if( val == null ) {
            return null;
        }
        Class<?> cl = val.getClass();
        for (Class<?> pt : OBJ2MAP_PASSTHROUGH) {
            if( pt.isAssignableFrom(cl)) {
                return ObjectToMapObjectType.PASSTHROUGH;
            }
        }
        if (cl.isPrimitive() || cl.isEnum() || cl.isArray() ) {
            return ObjectToMapObjectType.PASSTHROUGH;
        } else {
            if( ! isSame(processed, val) ) {
                return ObjectToMapObjectType.OBJECT;
            } else {
                return ObjectToMapObjectType.PROCESSED;
            }
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
