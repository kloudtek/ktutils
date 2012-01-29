/*
 * Copyright (c) 2011. KloudTek Ltd
 */

package com.kloudtek.util;

import java.lang.reflect.Field;

/**
 * Reflection helper methods.
 */
public class ReflectionUtils {
    /**
     * Set the specified value object into the object, using setAccessible() to have access to protected/private fields.
     *
     * @param obj   Object containing field to set.
     * @param name  Field name.
     * @param value Field value.
     */
    public static void forceSet(Object obj, String name, Object value) {
        try {
            Field field = obj.getClass().getDeclaredField(name);
            field.setAccessible(true);
            field.set(obj, value);
        } catch (NoSuchFieldException e) {
            throw new IllegalArgumentException(e.getMessage(), e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Set the specified value object into the object, field must be accessible.
     *
     * @param obj   Object containing field to set.
     * @param name  Field name.
     * @param value Field value.
     */
    public static void set(Object obj, String name, Object value) {
        try {
            obj.getClass().getDeclaredField(name).set(obj, value);
        } catch (NoSuchFieldException e) {
            throw new IllegalArgumentException(e.getMessage(), e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
