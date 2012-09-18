/*
 * Copyright (c) Kloudtek Ltd 2012.
 */

package com.kloudtek.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

public class StringUtils {
    public static boolean isEmpty(String txt) {
        return txt == null || txt.trim().isEmpty();
    }

    public static boolean isNotEmpty(String txt) {
        return !isEmpty(txt);
    }

    /**
     * URL encode a string using UTF-8
     *
     * @param txt String to encode.
     * @return URL encoded string.
     */
    public static String urlEncode(String txt) {
        try {
            return URLEncoder.encode(txt, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * URL decode a string using UTF-8
     *
     * @param txt String to decode.
     * @return Decoded string.
     */
    public static String urlDecode(String txt) {
        try {
            return URLDecoder.decode(txt, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public static String capitalize(String txt) {
        int len;
        if (txt == null || (len = txt.length()) == 0) {
            return txt;
        }
        final StringBuilder tmp = new StringBuilder(len);
        tmp.append(Character.toTitleCase(txt.charAt(0)));
        tmp.append(txt.substring(1));
        return tmp.toString();
    }
}
