/*
 * Copyright (c) 2014 Kloudtek Ltd
 */

package com.aeontronix.common.spring;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.Scope;
import org.springframework.web.context.request.FacesRequestAttributes;

import javax.faces.context.FacesContext;
import java.util.Map;

/**
 * Spring view scope implementation for JSF 2 view scope
 */
// TODO check if https://stackoverflow.com/questions/12182844/memory-leak-with-viewscoped-bean applies ?
public class SpringJSFViewScope implements Scope {
    public static final String CALLBACKS = "aeon-commonsViewScopeCallbacks";

    @Override
    public Object get(String name, ObjectFactory<?> factory) {
        Map<String, Object> viewMap = getViewMap();
        Object obj = viewMap.get(name);
        if (obj == null) {
            obj = factory.getObject();
            viewMap.put(name, obj);
        }
        return obj;
    }

    private Map<String, Object> getViewMap() {
        return FacesContext.getCurrentInstance().getViewRoot().getViewMap(true);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Object remove(String name) {
        Map<String, Object> viewMap = getViewMap();
        Object instance = viewMap.remove(name);
        if (instance != null) {
            Map<String, Runnable> callbacks = (Map<String, Runnable>) viewMap.get(CALLBACKS);
            if (callbacks != null) {
                callbacks.remove(name);
            }
        }
        return instance;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void registerDestructionCallback(String name, Runnable runnable) {
        Map<String, Runnable> callbacks = (Map<String, Runnable>) getViewMap().get(CALLBACKS);
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
