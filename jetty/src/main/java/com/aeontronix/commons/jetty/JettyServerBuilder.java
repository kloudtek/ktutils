/*
 * Copyright (c) 2014 Kloudtek Ltd
 */

package com.aeontronix.commons.jetty;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;

/**
 * Created by yannick on 07/10/2014.
 */
public class JettyServerBuilder {
    private int port;
    private String springContext;
    private boolean restEasyEnabled;

    public JettyServerBuilder(int port) {
        this.port = port;
    }

    /**
     * Set the resource path to spring context file.
     * When this is set to a non-null value this will add SpringContextLoaderListener to the context and set the init
     * parameter 'contextConfigLocation' to the specified springContext
     *
     * @param springContext Spring context location
     * @return the same builder object.
     */
    public JettyServerBuilder setSpringContext(String springContext) {
        this.springContext = springContext;
        return this;
    }

    public boolean isRestEasyEnabled() {
        return restEasyEnabled;
    }

    /**
     * Set if resteasy should be added to the server.
     * If this is true, it will add the ResteasyBootstrap listener to the server
     *
     * @param restEasyEnabled
     */
    public JettyServerBuilder setRestEasyEnabled(boolean restEasyEnabled) {
        this.restEasyEnabled = restEasyEnabled;
        return this;
    }

    public Server build() {
//        try {
        Server server = new Server(port);
        ServletContextHandler ctx = new ServletContextHandler(ServletContextHandler.SESSIONS);
        if (springContext != null && restEasyEnabled) {
//                ctx.addEventListener(Class.forName("org.jboss.resteasy.plugins.spring.SpringContextLoaderListener").newInstance());
        }
//            ctx.addEventListener(new ResteasyBootstrap());
//            ctx.addServlet(HttpServletDispatcher.class, "/*");
//            ctx.setInitParameter("contextConfigLocation", "classpath:applicationContext-test.xml");
        server.setHandler(ctx);
        return server;
//        } catch (InstantiationException e) {
//            throw new IllegalArgumentException(e);
//        } catch (IllegalAccessException e) {
//            throw new IllegalArgumentException(e);
//        } catch (ClassNotFoundException e) {
//            throw new IllegalArgumentException(e);
//        }
    }
}
