/*
 * Copyright (c) 2015 Kloudtek Ltd
 */

package com.kloudtek.util;

import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Character.isWhitespace;
import static java.lang.Character.toTitleCase;

/**
 * <p>Various string manipulation utility functions</p>
 */
public class StringUtils {
    public static Pattern varSubFuncPattern;
    public static final String UNSAFE_URLPATH = " %$&+,/:;=?@<>#%";

    public static boolean isEmpty(String txt) {
        return txt == null || txt.isEmpty();
    }

    public static boolean isNotEmpty(String txt) {
        return !isEmpty(txt);
    }

    public static boolean isBlank(String txt) {
        int strLen;
        if (txt == null || (strLen = txt.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if ((!isWhitespace(txt.charAt(i)))) {
                return false;
            }
        }
        return true;
    }

    public static boolean isNotBlank(String txt) {
        return !isBlank(txt);
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
        tmp.append(toTitleCase(txt.charAt(0)));
        tmp.append(txt.substring(1));
        return tmp.toString();
    }

    public static String base64Encode(byte[] data) {
        return new Base64(0, Base64.CHUNK_SEPARATOR).encodeToString(data);
    }

    public static byte[] base64Decode(String data) {
        return new Base64(0, Base64.CHUNK_SEPARATOR).decode(data);
    }

    public static String base64Encode(byte[] data, boolean urlSafe) {
        return new Base64(0, Base64.CHUNK_SEPARATOR, urlSafe).encodeToString(data);
    }

    public static byte[] base64Decode(String data, boolean urlSafe) {
        return new Base64(0, Base64.CHUNK_SEPARATOR, urlSafe).decode(data);
    }

    public static String base32Encode(byte[] data) {
        return new Base32(0, Base64.CHUNK_SEPARATOR).encodeToString(data);
    }

    public static byte[] base32Decode(String data) {
        return new Base32(0, Base64.CHUNK_SEPARATOR).decode(data);
    }

    public static String base32Encode(byte[] data, boolean useHex) {
        return new Base32(0, Base64.CHUNK_SEPARATOR, useHex).encodeToString(data);
    }

    public static byte[] base32Decode(String data, boolean useHex) {
        return new Base32(0, Base64.CHUNK_SEPARATOR, useHex).decode(data);
    }

    public static String urlPathEncode( String path ) {
        StringBuilder buffer = new StringBuilder();
        for (char c : path.toCharArray()) {
            if (UNSAFE_URLPATH.indexOf(c) >= 0) {
                buffer.append('%');
                buffer.append(toHex(c / 16));
                buffer.append(toHex(c % 16));
            } else if( c < 32 && c > 128 ) {
                buffer.append(urlEncode(Character.toString(c)));
            } else {
                buffer.append(c);
            }
        }
        return buffer.toString();
    }


    private static char toHex(int ch) {
        return (char) (ch < 10 ? '0' + ch : 'A' + ch - 10);
    }

    /**
     * Convert string to an UTF-8 encoded byte array
     *
     * @param str String to convert.
     * @return UTF-8 characters byte array
     */
    public static byte[] utf8(String str) {
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
    public static String utf8(byte[] utf8Chars) {
        try {
            return new String(utf8Chars, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new UnexpectedException(e);
        }
    }

    /**
     * Substitute variables in a string.
     * @param str String to substitute variables from
     * @param variables Variables
     * @return String with variables substituted
     */
    public static String substituteVariables( String str, Map<String,String> variables ) throws IllegalArgumentException {
        synchronized (StringUtils.class) {
            if( varSubFuncPattern == null ) {
                varSubFuncPattern = Pattern.compile("([a-zA-Z]*?):(.*)");
            }
        }
        if (str == null) {
            return null;
        }
        StringWriter result = new StringWriter();
        StringWriter v = null;
        VarSubState state = VarSubState.NORMAL;
        for (char c : str.toCharArray()) {
            switch (state) {
                case NORMAL:
                    if (c == '$') {
                        state = VarSubState.STARTPARSE;
                        v = new StringWriter();
                    } else {
                        result.append(c);
                    }
                    break;
                case STARTPARSE:
                    if (c == '{') {
                        state = VarSubState.PARSE;
                    } else {
                        if (c != '$') {
                            result.append('$');
                        }
                        result.append(c);
                        state = VarSubState.NORMAL;
                    }
                    break;
                case PARSE:
                    if (c == '}') {
                        result.append(resolveVarSub(v.toString(), variables));
                        v = null;
                        state = VarSubState.NORMAL;
                    } else {
                        v.append(c);
                    }
            }
        }
        return result.toString();
    }

    private static String resolveVarSub(String exp, Map<String, String> provisioningParams) throws IllegalArgumentException {
        Matcher m = varSubFuncPattern.matcher(exp);
        if( m.find() ) {
            String functionName = m.group(0).toLowerCase();
            String functionParams = m.group(1);
            if( functionName.equals("p") ) {

            } else {
                throw new IllegalArgumentException("Invalid variable substitution variable name: "+functionName+" in variable substitution string "+functionName);
            }
        } else {
            String val = provisioningParams.get(exp);
            return val != null ? val : "";
        }
    }

    public enum VarSubState {
        NORMAL, STARTPARSE, PARSE
    }
}
