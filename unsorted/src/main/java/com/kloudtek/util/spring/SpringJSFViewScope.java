/*
 * Copyright (c) 2014 Kloudtek Ltd
 */

package com.kloudtek.util.spring;

import com.kloudtek.util.JSFUtils;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.Scope;
import org.springframework.web.context.request.FacesRequestAttributes;

import javax.faces.context.FacesContext;
import java.util.Map;

import static com.kloudtek.util.JSFUtils.getViewMap;

/**
 * Spring view scope implementation for JSF 2 flash scope
 */
public class SpringJSFViewScope implements Scope {
    public static final String CALLBACKS = "ktutilsViewScopeCallbacks";

    @Override
    public Object get(String name, ObjectFactory<?> factory) {
        Object obj = JSFUtils.getViewMap().get(name);
        if (obj == null) {
            obj = factory.getObject();
            JSFUtils.getViewMap().put(name, obj);
        }
        return obj;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Object remove(String name) {
        Object instance = JSFUtils.getViewMap().remove(name);
        if (instance != null) {
            Map<String, Runnable> callbacks = (Map<String, Runnable>) JSFUtils.getViewMap().get(CALLBACKS);
            if (callbacks != null) {
                callbacks.remove(name);
            }
        }
        return instance;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void registerDestructionCallback(String name, Runnable runnable) {
        Map<String, Runnable> callbacks = (Map<String, Runnable>) JSFUtils.getViewMap().get(CALLBACKS);
        if (callbacks != null) {
            callbacks.put(name, runnable);
        }
    }

    @Override
    public Object resolveContextualObject(String name) {
        return new FacesRequestAttributes(FacesContext.getCurrentInstance()).resolveReference(name);
    }

    @Override
    public String getConversationId() {
        FacesContext ctx = FacesContext.getCurrentInstance();
        return new FacesRequestAttributes(ctx).getSessionId() + "-" + ctx.getViewRoot().getViewId();
    }
}
