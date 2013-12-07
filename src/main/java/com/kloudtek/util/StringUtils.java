/*
 * Copyright (c) 2013 KloudTek Ltd
 */

package com.kloudtek.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * <p>Various string manipulation utility functions</p>
 * <p>Note: The base64 functions is copied from commons-codec. this was done to avoid problems on android
 * which ships with a pre-historic version of commons-codec and conflicts with having a newer version (bad google!)</p>
 */
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

    public static String base64Encode(byte[] data) {
        return new Base64().encodeToString(data);
    }

    public static byte[] base64Decode(String data) {
        return new Base64().decode(data);
    }

    public static String base64Encode(byte[] data, boolean urlSafe) {
        return new Base64(urlSafe).encodeToString(data);
    }

    public static byte[] base64Decode(String data, boolean urlSafe) {
        return new Base64(urlSafe).decode(data);
    }

    public static String base32Encode(byte[] data) {
        return new Base32().encodeToString(data);
    }

    public static byte[] base32Decode(String data) {
        return new Base32().decode(data);
    }

    public static String base32Encode(byte[] data, boolean urlSafe) {
        return new Base32(urlSafe).encodeToString(data);
    }

    public static byte[] base32Decode(String data, boolean urlSafe) {
        return new Base32(urlSafe).decode(data);
    }

    /**
     * Convert string to an UTF-8 encoded byte array
     *
     * @param str String to convert.
     * @return UTF-8 characters byte array
     */
    public static byte[] toUTF8(String str) {
        try {
            return str.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new UnexpectedException(e);
        }
    }

    /**
     * Convert UTF-8 encoded byte arrays to a string
     *
     * @param utf8Chars UTF-8 characters byte array
     * @return Converted string.
     */
    public static String fromUTF8(byte[] utf8Chars) {
        try {
            return new String(utf8Chars, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new UnexpectedException(e);
        }
    }
}
