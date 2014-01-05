/*
 * Copyright (c) 2013 KloudTek Ltd
 */

package com.kloudtek.util.validation;

import com.kloudtek.util.SystemUtils;
import com.kloudtek.util.UnexpectedException;

import static com.kloudtek.util.ExceptionUtils.throwException;
import static com.kloudtek.util.StringUtils.isEmpty;

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

    public static <E extends Exception> void validateNotNull(Class<E> exceptionClass, String msg, Object... values) throws E {
        if (values != null) {
            for (Object value : values) {
                if (value == null) {
                    throwException(exceptionClass, msg);
                }
            }
        }
    }

    public static <E extends Exception> void validateNotEmpty(Class<E> exceptionClass, String msg, String... values) throws E {
        if (values != null) {
            for (String value : values) {
                if (isEmpty(value)) {
                    throwException(exceptionClass, msg);
                }
            }
        }
    }
}
