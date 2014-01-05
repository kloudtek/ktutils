package com.kloudtek.util.validation;

/**
 * Created by yannick on 05/01/2014.
 */
public interface AnnotationBasedValidator {
    @SuppressWarnings({"unchecked", "ThrowableResultOfMethodCallIgnored"})
    <E extends Exception> void validate(Object object, Class<E> exceptionClass, Class<?>... validationGroups) throws E;
}
