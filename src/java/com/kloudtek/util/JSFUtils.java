/*
 * Copyright (c) Kloudtek Ltd 2013.
 */

package com.kloudtek.util;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.MessageFormat;
import java.util.Map;
import java.util.ResourceBundle;

public class JSFUtils {
    public static String getParameter(String key) {
        return getExternalContext().getRequestParameterMap().get(key);
    }

    public static ExternalContext getExternalContext() {
        return FacesContext.getCurrentInstance().getExternalContext();
    }

    public static HttpServletRequest getHttpRequest() {
        return (HttpServletRequest) getExternalContext().getRequest();
    }

    public static HttpServletResponse getHttpResponse() {
        return (HttpServletResponse) getExternalContext().getResponse();
    }

    public static void login(String username, String password) throws ServletException {
        getHttpRequest().login(username, password);
    }

    public static void logout() throws ServletException {
        getHttpRequest().logout();
    }

    public static ResourceBundle getResourceBundle(String var) {
        final FacesContext context = FacesContext.getCurrentInstance();
        return context.getApplication().getResourceBundle(context, var);
    }

    public static void addMessage(String clientId, FacesMessage.Severity severity, String summary, String details) {
        FacesContext.getCurrentInstance().addMessage(clientId, new FacesMessage(severity, summary, details));
    }

    public static void addI18NMessage(String clientId, FacesMessage.Severity severity, String bundleVar, String messageId) {
        final FacesContext context = FacesContext.getCurrentInstance();
        final ResourceBundle bundle = context.getApplication().getResourceBundle(context, bundleVar);
        final String summary = bundle.getString(messageId);
        final String detailsKey = messageId + "_detail";
        final String details = bundle.containsKey(detailsKey) ? bundle.getString(detailsKey) : null;
        context.addMessage(clientId, new FacesMessage(severity, summary, details));
    }

    @SuppressWarnings({"SynchronizationOnLocalVariableOrMethodParameter"})
    public static void addI18NMessage(String clientId, FacesMessage.Severity severity, String bundleVar, String messageId, Object... args) {
        final FacesContext context = FacesContext.getCurrentInstance();
        final ResourceBundle bundle = context.getApplication().getResourceBundle(context, bundleVar);
        final String cacheKey = "jsfmsgfmtc" + messageId;
        final String detailsKey = messageId + "_detail";
        final String detailsCacheKey = cacheKey + "_detail";

        Map<String, Object> appMap = context.getExternalContext().getApplicationMap();
        MessageFormat summaryFormatter = (MessageFormat) appMap.get(cacheKey);
        if (summaryFormatter == null) {
            summaryFormatter = new MessageFormat(bundle.getString(messageId));
            appMap.put(cacheKey, summaryFormatter);
        }
        MessageFormat detailsFormatter = (MessageFormat) appMap.get(detailsCacheKey);
        if (detailsFormatter == null) {
            final String detailsMsg = bundle.containsKey(detailsKey) ? bundle.getString(detailsKey) : null;
            if (detailsMsg != null) {
                detailsFormatter = new MessageFormat(detailsMsg);
                appMap.put(detailsCacheKey, detailsFormatter);
            } else {
                detailsFormatter = null;
            }
        }
        synchronized (summaryFormatter) {
            context.addMessage(clientId, new FacesMessage(severity, summaryFormatter.format(args),
                    detailsFormatter != null ? detailsFormatter.format(args) : null));
        }
    }

    public static void addI18NErrorMessage(String clientId, String bundleVar, String messageId) {
        addI18NMessage(clientId, FacesMessage.SEVERITY_ERROR, bundleVar, messageId);
    }

    public static void addI18NInfoMessage(String clientId, String bundleVar, String messageId) {
        addI18NMessage(clientId, FacesMessage.SEVERITY_INFO, bundleVar, messageId);
    }

    public static void addI18NWarnMessage(String clientId, String bundleVar, String messageId) {
        addI18NMessage(clientId, FacesMessage.SEVERITY_WARN, bundleVar, messageId);
    }

    public static void addI18NFatalMessage(String clientId, String bundleVar, String messageId) {
        addI18NMessage(clientId, FacesMessage.SEVERITY_FATAL, bundleVar, messageId);
    }

    public static void addI18NErrorMessage(String clientId, String bundleVar, String messageId, Object... args) {
        addI18NMessage(clientId, FacesMessage.SEVERITY_ERROR, bundleVar, messageId, args);
    }

    public static void addI18NInfoMessage(String clientId, String bundleVar, String messageId, Object... args) {
        addI18NMessage(clientId, FacesMessage.SEVERITY_INFO, bundleVar, messageId, args);
    }

    public static void addI18NWarnMessage(String clientId, String bundleVar, String messageId, Object... args) {
        addI18NMessage(clientId, FacesMessage.SEVERITY_WARN, bundleVar, messageId, args);
    }

    public static void addI18NFatalMessage(String clientId, String bundleVar, String messageId, Object... args) {
        addI18NMessage(clientId, FacesMessage.SEVERITY_FATAL, bundleVar, messageId, args);
    }

    public static void addErrorMessage(String clientId, String summary, String details) {
        addMessage(clientId, FacesMessage.SEVERITY_ERROR, summary, details);
    }

    public static void addInfoMessage(String clientId, String summary, String details) {
        addMessage(clientId, FacesMessage.SEVERITY_INFO, summary, details);
    }

    public static void addWarnMessage(String clientId, String summary, String details) {
        addMessage(clientId, FacesMessage.SEVERITY_WARN, summary, details);
    }

    public static void addFatalMessage(String clientId, String summary, String details) {
        addMessage(clientId, FacesMessage.SEVERITY_FATAL, summary, details);
    }

    public static void addErrorMessage(String clientId, String summary) {
        addMessage(clientId, FacesMessage.SEVERITY_ERROR, summary, null);
    }

    public static void addInfoMessage(String clientId, String summary) {
        addMessage(clientId, FacesMessage.SEVERITY_INFO, summary, null);
    }

    public static void addWarnMessage(String clientId, String summary) {
        addMessage(clientId, FacesMessage.SEVERITY_WARN, summary, null);
    }

    public static void addFatalMessage(String clientId, String summary) {
        addMessage(clientId, FacesMessage.SEVERITY_FATAL, summary, null);
    }

    public static String getForwardRequestUri() {
        return (String) getHttpRequest().getAttribute("javax.servlet.forward.request_uri");
    }

    public static String getForwardedContextPath() {
        return (String) getHttpRequest().getAttribute("javax.servlet.forward.context_path");
    }

    public static String getForwardedServletPath() {
        return (String) getHttpRequest().getAttribute("javax.servlet.forward.servlet_path");
    }

    public static String getForwardedPathInfo() {
        return (String) getHttpRequest().getAttribute("javax.servlet.forward.path_info");
    }

    public static String getForwardedQueryString() {
        return (String) getHttpRequest().getAttribute("javax.servlet.forward.query_string");
    }

    public static String getIncludedRequestUri() {
        return (String) getHttpRequest().getAttribute("javax.servlet.include.request_uri");
    }

    public static String getIncludedContextPath() {
        return (String) getHttpRequest().getAttribute("javax.servlet.include.context_path");
    }

    public static String getIncludedServletPath() {
        return (String) getHttpRequest().getAttribute("javax.servlet.include.servlet_path");
    }

    public static String getIncludedPathInfo() {
        return (String) getHttpRequest().getAttribute("javax.servlet.include.path_info");
    }

    public static String getIncludedQueryString() {
        return (String) getHttpRequest().getAttribute("javax.servlet.include.query_string");
    }

    /**
     * Build an URL, using the face context request's schema, address and port
     *
     * @param ctx  JSF FaceContext
     * @param path URL path
     * @return url.
     */
    public static String buildUrl(FacesContext ctx, String path) {
        final ExternalContext ectx = ctx.getExternalContext();
        return URLUtils.buildUrl(ectx.getRequestScheme(), ectx.getRequestServerName(), ectx.getRequestServerPort(), path);
    }
}