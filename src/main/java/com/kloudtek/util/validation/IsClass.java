/*
 * Copyright (c) 2013 KloudTek Ltd
 */

package com.kloudtek.util.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Annotation used to specify a certain class is expected
 */
@Target({FIELD, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = IsClassValidator.class)
@Documented
public @interface IsClass {
    String message() default "{com.kloudtek.util.validation.isclass}";

    Class<?>[] groups() default {};

    Class<?> value();
}
