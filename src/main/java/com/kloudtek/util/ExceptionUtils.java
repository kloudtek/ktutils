/*
 * Copyright (c) 2013 KloudTek Ltd
 */

package com.kloudtek.util;

import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Various Exception-related utilities.
 */
public class ExceptionUtils {
    private static final Logger logger = Logger.getAnonymousLogger();
    private static final Map<Class<?>, Boolean> userDisplayable = Collections.synchronizedMap(new WeakHashMap<Class<?>, Boolean>());
    private static Class<? extends Exception> clazz;

    /**
     * Throw an exception of the specified class
     *
     * @param exceptionClass Exception class
     * @param msg            Optional message (in which case the exception must have a constructor with a single parameter that is the message)
     */
    public static <E extends Exception> void throwException(Class<E> exceptionClass, String msg) throws E {
        E exception;
        try {
            if (msg == null) {
                exception = exceptionClass.newInstance();
            } else {
                exception = exceptionClass.getConstructor(String.class).newInstance(msg);
            }
        } catch (InstantiationException e) {
            throw new UnexpectedException(e);
        } catch (IllegalAccessException e) {
            throw new UnexpectedException(e);
        } catch (NoSuchMethodException e) {
            throw new UnexpectedException(e);
        } catch (InvocationTargetException e) {
            throw new UnexpectedException(e);
        }
        throw exception;
    }

    /**
     * Wrap the exception into a RuntimeException, and throw it
     *
     * @param e Exception.
     * @throws RuntimeException Wrapped language.
     */
    public static void rthrow(Exception e) throws RuntimeException {
        throw new RuntimeException(e);
    }

    /**
     * Log the message in the exception at the specified error level.
     *
     * @param exception Exception that contains the message to log.
     * @param logger    Logger to use for logging.
     * @param level     Logging level to use.
     * @return The exception passed as parameter
     */
    public static <X extends Exception> X log(X exception, Logger logger, Level level) {
        logger.log(level, exception.getMessage());
        return exception;
    }

    /**
     * Log the message in the exception at severe logging level.
     *
     * @param exception Exception that contains the message to log.
     * @param logger    Logger to use for logging.
     * @return The exception passed as parameter
     */
    public static <X extends Exception> X log(X exception, Logger logger) {
        logger.log(Level.SEVERE, exception.getMessage());
        return exception;
    }

    /**
     * Log the message in the exception at severe logging level, using an anonymous logger.
     *
     * @param exception Exception that contains the message to log.
     * @return The exception passed as parameter
     */
    public static <X extends Exception> X log(X exception) {
        logger.log(Level.SEVERE, exception.getMessage());
        return exception;
    }

    /**
     * Checks that the exception is tagged with the {@link UserDisplayable} annotation. If that's the case, re-throws
     * the exception, otherwise throws {@link SystemErrorException}
     *
     * @param exception Exception to check
     * @throws X Exception passed as argument if it's tagged {@link UserDisplayable}, or a {@link SystemErrorException} if that's not the case.
     */
    public static <X extends Exception> void checkExceptionDisplayable(X exception) throws X {
        clazz = exception.getClass();
        Boolean ud = userDisplayable.get(clazz);
        if (ud == null) {
            ud = clazz.isAnnotationPresent(UserDisplayable.class);
            userDisplayable.put(clazz, ud);
        }
        if (ud) {
            throw exception;
        } else {
            throw new SystemErrorException();
        }
    }
}
