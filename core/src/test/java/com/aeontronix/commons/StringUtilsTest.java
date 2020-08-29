package com.aeontronix.commons;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.jetbrains.annotations.NotNull;
import org.testng.Assert;
import org.testng.annotations.Test;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.URL;
import java.util.HashMap;

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

    @Test
    public void testVarSubSimple() {
        assertEquals("blabarbleh",StringUtils.substituteVariables("bla${foo}bleh", genVars()));
    }

    @Test
    public void testVarSubEscape() {
        assertEquals("bla${foo}bleh",StringUtils.substituteVariables("bla\\${foo}bleh", genVars()));
    }

    @Test
    public void testVarSubEscape2() {
        assertEquals("bla$bleh",StringUtils.substituteVariables("bla\\$bleh", genVars()));
    }

    @Test
    public void testVarSubNeverFail() {
        assertEquals("blableh",StringUtils.substituteVariables("bla${xxx}bleh", genVars()));
    }

    @Test
    public void testVarSubNeverFailBadArg() {
        assertEquals("blableh",StringUtils.substituteVariables("bla${s:fsd}bleh", genVars()));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testVarSubFail() {
        StringUtils.substituteVariables("bla${xxx}bleh", genVars(),false);
    }

    @Test
    public void testVarSubSuffix() {
        assertEquals("bla-bar",StringUtils.substituteVariables("bla${s:-:foo}", genVars()));
    }

    @Test
    public void testVarSubSuffixComma() {
        assertEquals("bla:bar",StringUtils.substituteVariables("bla${s::::foo}", genVars()));
    }

    @Test
    public void testVarSubPrefix() {
        assertEquals("bar-bla",StringUtils.substituteVariables("${p:-:foo}bla", genVars()));
    }

    @Test
    public void testVarSubPrefixComman() {
        assertEquals("bar:bla",StringUtils.substituteVariables("${p::::foo}bla", genVars()));
    }

    @Test
    public void testVarSubSuffixEmpty() {
        assertEquals("bla",StringUtils.substituteVariables("bla${s:-:xxx}", genVars()));
    }

    @Test
    public void testVarSubUpperCase() {
        assertEquals("blaBAR",StringUtils.substituteVariables("bla${u:foo}", genVars()));
    }

    @Test
    public void testVarSubLowerCase() {
        assertEquals("blamoo",StringUtils.substituteVariables("bla${l:boo}", genVars()));
    }

    @Test
    public void testVarCapitalize() {
        assertEquals("blaBar",StringUtils.substituteVariables("bla${c:foo}", genVars()));
    }

    @Test
    public void testVarString() {
        assertEquals("blaBl:~ a",StringUtils.substituteVariables("bla${t:Bl:~ a}", genVars()));
    }

    @Test
    public void testVarInVar() {
        assertEquals("blabarbleh",StringUtils.substituteVariables("bla${${alias}}bleh", genVars()));
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
                    new URL("http://localhost:" + port + path).openStream();
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

    @NotNull
    private HashMap<String, String> genVars() {
        HashMap<String,String> vars = new HashMap<String, String>();
        vars.put("foo","bar");
        vars.put("boo","MOO");
        vars.put("alias","foo");
        return vars;
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
