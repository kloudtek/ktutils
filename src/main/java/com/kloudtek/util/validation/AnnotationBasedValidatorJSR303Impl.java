package com.kloudtek.util.validation;

import com.kloudtek.util.ExceptionUtils;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

/**
 * Annotation based validator that uses a JSR 303 implementation
 */
public class AnnotationBasedValidatorJSR303Impl implements AnnotationBasedValidator {
    private final Validator validator;
    private final Exception validatorFailException;

    public AnnotationBasedValidatorJSR303Impl() {
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

    @Override
    @SuppressWarnings({"unchecked", "ThrowableResultOfMethodCallIgnored"})
    public <E extends Exception> void validate(Object object, Class<E> exceptionClass, Class<?>... validationGroups) throws E {
        if (validator == null) {
            throw new RuntimeException("No validator available: " + validatorFailException.getMessage(), validatorFailException);
        }
        Set<ConstraintViolation<Object>> violations = validator.validate(object, validationGroups);
        if (!violations.isEmpty()) {
            ConstraintViolation<Object> violation = violations.iterator().next();
            String errorMsg = violation.getPropertyPath().toString() + ": " + violation.getMessage();
            ExceptionUtils.throwException(exceptionClass, errorMsg);
        }
    }
}
