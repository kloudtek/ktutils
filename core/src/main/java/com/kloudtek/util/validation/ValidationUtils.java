/*
 * Copyright (c) 2014 Kloudtek Ltd
 */

package com.kloudtek.util.validation;

import com.kloudtek.util.StringUtils;
import com.kloudtek.util.SystemUtils;
import com.kloudtek.util.UnexpectedException;

import static com.kloudtek.util.ExceptionUtils.throwException;

/**
 * Validation utilities
 */
public class ValidationUtils {
    private static final AnnotationBasedValidator annotationBasedValidator;

    static {
        if (SystemUtils.hasClass("javax.validation.Validator")) {
            annotationBasedValidator = new AnnotationBasedValidatorJSR303Impl();
        } else {
            annotationBasedValidator = null;
        }
    }

    @SuppressWarnings({"unchecked", "ThrowableResultOfMethodCallIgnored"})
    public static <E extends Exception> void validate(Object object, Class<E> exceptionClass, Class<?>... validationGroups) throws E {
        if (annotationBasedValidator == null) {
            throw new UnexpectedException("No annotation based validator available");
        }
        annotationBasedValidator.validate(object, exceptionClass, validationGroups);
    }

    public static void validateWithinBounds(String msg, long min, long max, long... values) throws IllegalArgumentException {
        validateWithinBounds(IllegalArgumentException.class, msg, min, max, values);
    }

    public static <E extends Exception> void validateWithinBounds(Class<E> exceptionClass, String msg, long min, long max, long... values) throws E {
        for (long value : values) {
            if (value < min || value > max) {
                throwException(exceptionClass, msg);
            }
        }
    }

    public static <E extends Exception> void notNull(Class<E> exceptionClass, String msg, Object... values) throws E {
        if (values != null) {
            for (Object value : values) {
                if (value == null) {
                    throwException(exceptionClass, msg);
                }
            }
        }
    }

    public static <E extends Exception> void notEmpty(Class<E> exceptionClass, String msg, String... values) throws E {
        if (values != null) {
            for (String value : values) {
                if (StringUtils.isEmpty(value)) {
                    throwException(exceptionClass, msg);
                }
            }
        }
    }

    public static boolean notEmpty(String... values) {
        for (String value : values) {
            if (StringUtils.isEmpty(value)) {
                return false;
            }
        }
        return true;
    }
}
