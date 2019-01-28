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
    @Test
    public void testBuildUrlNoQuery() {
        String url = new URLBuilder("http://site?x=y+y").path("foo").path("bar").path("/buz/").path("baz/").path("/boz").toString();
        Assert.assertEquals(url,"http://site/foo/bar/buz/baz/boz?x=y+y");
    }

    @Test
    public void testBuildUrlWithQueryParams() {
        String url = new URLBuilder("http://site/foo").path("bar").param("key1","val1").param("key2","val2").setUserInfo("bahamut").setRef("boo").toString();
        Assert.assertEquals(url,"http://bahamut@site/foo/bar#boo?key1=val1&key2=val2");
    }

    @Test
    public void testBuildUrlWithQueryParamsFromRelative() {
        String url = new URLBuilder("foo").path("bar").param("key1","val1").param("key2","val2").setUserInfo("bahamut")
                .setRef("boo").setHost("mysite").setPort(33).setProtocol("https").toString();
        Assert.assertEquals(url,"https://bahamut@mysite:33/foo/bar#boo?key1=val1&key2=val2");
    }

    @Test
    public void testBuildUrlWithInlineQuery() {
        String url = new URLBuilder("http://site/foo").param("key1","val1").path("bar?key2=val2&key3=val3").param("key4","val4").path("baz").toString();
        Assert.assertEquals(url,"http://site/foo/bar/baz?key1=val1&key2=val2&key3=val3&key4=val4");
    }

    @Test
    public void testBuildRelative() {
        String url = new URLBuilder("/foo").param("key1","val1").path("bar?key2=val2&key3=val3").param("key4","val4").path("baz").toString();
        Assert.assertEquals(url,"/foo/bar/baz?key1=val1&key2=val2&key3=val3&key4=val4");
    }

    @Test
    public void testBuildRelativeWithEncodedParams() {
        String url = new URLBuilder("/foo?k=x+x").param("key1","val 1").path("bar?key2=val2&key3=val3").param("key4","val4").path("baz").toString();
        url = new URLBuilder(new URLBuilder("http://boo").path(url).path("gah").param("bla","ble").toString()).toString();
        Assert.assertEquals(url,"http://boo/foo/bar/baz/gah?k=x+x&key1=val+1&key2=val2&key3=val3&key4=val4&bla=ble");
    }

    @Test
    public void testBuildWithFragment() {
        String url = new URLBuilder("http://boo/lok#rev?qq=rr").path("xoy").param("groo","obp").toString();
        Assert.assertEquals(url,"http://boo/lok/xoy#rev?qq=rr&groo=obp");
    }

    @Test
    public void testBuildWithFragmentInterMarkNoQParam() {
        String url = new URLBuilder("http://boo/lok#rev?").path("xoy").param("groo","obp").toString();
        Assert.assertEquals(url,"http://boo/lok/xoy#rev?groo=obp");
    }
}