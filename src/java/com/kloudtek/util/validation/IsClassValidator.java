/*
 * Copyright (c) Kloudtek Ltd 2013.
 */

package com.kloudtek.util.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class IsClassValidator implements ConstraintValidator<IsClass, Object> {
    private Class<?> isClass;

    public void initialize(IsClass isClass) {
        this.isClass = isClass.value();
    }

    public boolean isValid(Object object, ConstraintValidatorContext constraintContext) {
        if (object == null) {
            return true;
        }
        if (!object.getClass().getName().equals(isClass.getName())) {
            constraintContext.disableDefaultConstraintViolation();
            constraintContext.buildConstraintViolationWithTemplate("{com.kloudtek.util.validation.isclass}")
                    .addConstraintViolation();
            return false;
        }
        return true;
    }
}
