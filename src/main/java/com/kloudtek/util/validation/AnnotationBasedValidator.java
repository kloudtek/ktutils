/*
 * Copyright (c) 2014 Kloudtek Ltd
 */

package com.kloudtek.util.validation;

/**
 * Interface for annotation based validators
 */
public interface AnnotationBasedValidator {
    @SuppressWarnings({"unchecked", "ThrowableResultOfMethodCallIgnored"})
    <E extends Exception> void validate(Object object, Class<E> exceptionClass, Class<?>... validationGroups) throws E;
}
