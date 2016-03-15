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

import static org.junit.Assert.*;

/**
 * Created by yannick on 15/3/16.
 */
public class StringUtilsTest {
    String decodedPath;
    public static final String PATH = "/\\!@#$%^&*(){}'\"`§てすとｽｾｿﾀ %$&+,/:;=?@<>#%";

    @Test
    public void testUrlPathEncode() throws Exception {
        String path = decodeUrl("/test/"+StringUtils.urlPathEncode(PATH));
        Assert.assertEquals(path,"/test/"+PATH);
    }

    public String decodeUrl(final String path) throws Exception {
        ServerSocket serverSocket = new ServerSocket(0);
        final int port = serverSocket.getLocalPort();
        serverSocket.close();
        Server server = new Server(port);
        server.setStopAtShutdown(true);
        ServletContextHandler ctx = new ServletContextHandler(server, "/", true, false);
        ctx.addServlet(new ServletHolder(new DecodeServlet()), "/*");
        server.start();
        ThreadUtils.sleep(500);
        new Thread() {
            @Override
            public void run() {
                try {
                    ThreadUtils.sleep(500);
                    InputStream is = new URL("http://localhost:" + port + path).openStream();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }.start();
        synchronized (this) {
            wait();
        }
        return this.decodedPath;
    }

    public class DecodeServlet extends HttpServlet {
        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            decodedPath = req.getPathInfo();
            synchronized (StringUtilsTest.this) {
                StringUtilsTest.this.notifyAll();
            }
        }
    }
}