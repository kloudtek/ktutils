/*
 * Copyright (c) Kloudtek Ltd 2013.
 */

package com.kloudtek.util.validation;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.lang.reflect.Constructor;
import java.util.Set;

/**
 * Validation utilities
 */
public class ValidationUtils {
    private static final Validator validator;
    private static final Exception validatorFailException;

    static {
        Validator validatorImpl = null;
        Exception fail = null;
        try {
            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            validatorImpl = factory.getValidator();
        } catch (Exception e) {
            fail = e;
        }
        validator = validatorImpl;
        validatorFailException = fail;
    }

    public static void validateWithinBounds(String msg, long min, long max, long... values) {
        for (long value : values) {
            if (value < min) {
                throw new IllegalArgumentException(msg);
            } else if (value > max) {
                throw new IllegalArgumentException(msg);
            }
        }
    }

    @SuppressWarnings({"unchecked", "ThrowableResultOfMethodCallIgnored"})
    public static <E extends Exception> void validate(Object object, Class<E> exceptionClass, Class<?>... validationGroups) throws E {
        if (validator == null) {
            throw new RuntimeException("No validator available: " + validatorFailException.getMessage(), validatorFailException);
        }
        Set<ConstraintViolation<Object>> violations = validator.validate(object, validationGroups);
        if (!violations.isEmpty()) {
            E exception;
            try {
                Constructor<? extends Exception> constructor = exceptionClass.getConstructor(String.class);
                ConstraintViolation<Object> violation = violations.iterator().next();
                String errorMsg = violation.getPropertyPath().toString() + ": " + violation.getMessage();
                exception = (E) constructor.newInstance(errorMsg);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            throw exception;
        }
    }
}
