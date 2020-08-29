/*
 * Copyright (c) 2014 Kloudtek Ltd
 */

package com.aeontronix.common.spring;

import javax.faces.component.UIViewRoot;
import javax.faces.event.*;
import java.util.HashMap;
import java.util.Map;

/**
 * This implementation of JSF {@link javax.faces.event.ViewMapListener} will register view scope callbacks
 */
public class SpringJSFViewScopeRegistrationListener implements ViewMapListener {
    @SuppressWarnings("unchecked")
    public void processEvent(SystemEvent event) throws AbortProcessingException {
        if (event instanceof PostConstructViewMapEvent) {
            UIViewRoot viewRoot = (UIViewRoot) ((PostConstructViewMapEvent) event).getComponent();
            viewRoot.getViewMap().put(SpringJSFViewScope.CALLBACKS, new HashMap<String, Runnable>());
        } else if (event instanceof PreDestroyViewMapEvent) {
            UIViewRoot viewRoot = (UIViewRoot) ((PreDestroyViewMapEvent) event).getComponent();
            Map<String, Runnable> callbacks = (Map<String, Runnable>) viewRoot.getViewMap().get(SpringJSFViewScope.CALLBACKS);
            if (callbacks != null) {
                for (Runnable c : callbacks.values()) {
                    c.run();
                }
                callbacks.clear();
            }
        }
    }

    public boolean isListenerForSource(Object source) {
        return source instanceof UIViewRoot;
    }
}
