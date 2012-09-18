/*
 * Copyright (c) Kloudtek Ltd 2012.
 */

package com.kloudtek.util.cdi;

import javax.enterprise.inject.Instance;
import javax.enterprise.inject.spi.BeanManager;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashSet;

public class CDIUtils {
    public static BeanManager getBeanManager() {
        try {
            InitialContext initialContext = new InitialContext();
            return (BeanManager) initialContext.lookup("java:comp/BeanManager");
        } catch (NamingException e) {
            return null;
        }
    }

    @SuppressWarnings({"unchecked"})
    public static <X> X getByQualifier
            (Instance<X> instances, Class<?> qualifier, Object... qualifierArgs) {
        HashSet<Class<?>> cl = new HashSet<Class<?>>();
        for (Object qualifierArg : qualifierArgs) {
            Class<?> qac = qualifierArg.getClass();
            if (cl.contains(qac)) {
                throw new IllegalArgumentException("Multiple qualifier arguments must not be of the same class");
            }
            cl.add(qac);
        }
        Annotation proxy = (Annotation) Proxy.newProxyInstance(qualifier.getClassLoader(), new Class<?>[]{qualifier}, new Handler(qualifier, qualifierArgs));
        return (X) instances.select(proxy).get();
    }

    static class Handler implements InvocationHandler {
        private Class<?> qualifier;
        private Object[] qualifierArgs;

        Handler(Class<?> qualifier, Object[] qualifierArgs) {
            this.qualifier = qualifier;
            this.qualifierArgs = qualifierArgs;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (method.getName().equals("annotationType")) {
                return qualifier;
            }
            try {
                return getClass().getMethod(method.getName(), method.getParameterTypes()).invoke(this, args);
            } catch (NoSuchMethodException e) {
                for (Object arg : qualifierArgs) {
                    Class<?> retType = method.getReturnType();
                    if (retType != null && retType.equals(arg.getClass())) {
                        return arg;
                    }
                }
                throw new RuntimeException("Unsupported method");
            }
        }
    }
}
