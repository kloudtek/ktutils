package com.kloudtek.util;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.testng.Assert;
import org.testng.annotations.Test;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.URL;

/**
 * Created by yannick on 14/3/16.
 */
public class URLBuilderTest {
    private String path;

    @Test
    public void testBuildUrlNoQuery() {
        String url = new URLBuilder("http://site").path("foo").path("bar").path("/buz/").path("baz/").path("/boz").toString();
        Assert.assertEquals(url,"http://site/foo/bar/buz/baz/boz");
    }

    @Test
    public void testBuildUrlWithQueryParams() {
        String url = new URLBuilder("http://site/foo").path("bar").param("key1","val1").param("key2","val2").toString();
        Assert.assertEquals(url,"http://site/foo/bar?key1=val1&key2=val2");
    }

    @Test
    public void testBuildUrlWithInlineQuery() {
        String url = new URLBuilder("http://site/foo").param("key1","val1").path("bar?key2=val2&key3=val3").param("key4","val4").path("baz").toString();
        Assert.assertEquals(url,"http://site/foo/bar/baz?key1=val1&key2=val2&key3=val3&key4=val4");
    }
}