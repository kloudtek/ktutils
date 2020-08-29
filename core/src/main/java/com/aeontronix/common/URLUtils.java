/*
 * Copyright (c) 2014 Kloudtek Ltd
 */

package com.aeontronix.common;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * Various URL/URI related utility functions
 */
public class URLUtils {
    public static String encodeUTF8(String text) {
        try {
            return URLEncoder.encode(text, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new UnexpectedException(e);
        }
    }

    public static String decodeUTF8(String text) {
        try {
            return URLDecoder.decode(text, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new UnexpectedException(e);
        }
    }

    public static URL newUrl(String url) {
        try {
            return new URL(url);
        } catch (MalformedURLException e) {
            throw new UnexpectedException(e);
        }
    }

    public static String buildUrl(String address, int port, int securePort, boolean secure) {
        StringBuilder url = new StringBuilder("http");
        if (secure) {
            url.append('s');
        }
        url.append("://").append(address);
        if ((secure && securePort != 443) || (!secure && port != 80)) {
            url.append(':').append(secure ? securePort : port);
        }
        return url.toString();
    }

    public static String buildUrl(String scheme, String address, int port, String path) {
        StringBuilder url = new StringBuilder(scheme).append("://").append(address);
        if (!(scheme.equals("http") && port == 80) && !(scheme.equals("https") && port == 443)) {
            url.append(':').append(port);
        }
        if (!path.startsWith("/")) {
            url.append("/");
        }
        url.append(path);
        return url.toString();
    }

    public static String buildUrl(String baseUrl, String... pathElements) {
        final URLBuilder url = new URLBuilder(baseUrl);
        for (String element : pathElements) {
            url.path(element);
        }
        return url.toString();
    }

    /**
     * Concatenate various paths elements, making sure there is only one slash '/' between each one
     *
     * @param paths Path elements
     * @return concatenated path elements
     */
    public static String concatPaths(String... paths) {
        StringBuilder tmp = new StringBuilder();
        for (String p : paths) {
            if (p == null || p.length() == 0) {
                continue;
            }
            final int len = tmp.length();
            if (len == 0) {
                tmp.append(p);
            } else {
                final char c = tmp.charAt(len - 1);
                final char pc = p.charAt(0);
                if (c == '/' && pc == '/') {
                    tmp.append(p.substring(1, p.length()));
                } else if (c != '/' && pc != '/') {
                    tmp.append('/').append(p);
                } else {
                    tmp.append(p);
                }
            }
        }
        return tmp.toString();
    }
}
