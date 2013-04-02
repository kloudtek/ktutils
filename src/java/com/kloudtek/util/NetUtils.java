/*
 * Copyright (c) Kloudtek Ltd 2013.
 */

package com.kloudtek.util;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class NetUtils {
    /**
     * Calls {@link #waitForPort(String, int, long, long)} with a timeout of 25 seconds and pauseBetweenRetries of 1 second.
     *
     * @param address Address to connect to.
     * @param port    Port to connect to.
     * @return True if successful connection was possible, false otherwise
     */
    public static boolean waitForPort(final String address, final int port) {
        return waitForPort(address, port, 25000L, 1000L);
    }

    /**
     * Repeatedly attempts to connect to the specified address/port, until successful or timeout reached
     *
     * @param address             Address to connect to.
     * @param port                Port to connect to.
     * @param timeout             How long to keep attempting connections before giving up.
     * @param pauseBetweenRetries How long to wait between attempts (in milliseconds)
     * @return True if successful connection was possible, false otherwise
     */
    public static boolean waitForPort(final String address, final int port, final long timeout, long pauseBetweenRetries) {
        final long expire = System.currentTimeMillis() + timeout;
        while (System.currentTimeMillis() < expire) {
            try {
                final Socket socket = new Socket(address, port);
                socket.close();
                return true;
            } catch (UnknownHostException e) {
                return false;
            } catch (IOException e) {
                try {
                    Thread.sleep(pauseBetweenRetries);
                } catch (InterruptedException ex) {
                    return false;
                }
            }
        }
        return false;
    }
}
