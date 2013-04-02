/*
 * Copyright (c) Kloudtek Ltd 2013.
 */

package com.kloudtek.util.cdi;

import javax.inject.Qualifier;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Qualifier
@Target({ANNOTATION_TYPE})
@Retention(RUNTIME)
public @interface Main {
}
