/*
 * Copyright (c) 2015 Kloudtek Ltd
 */

package com.aeontronix.commons;

import org.jetbrains.annotations.Nullable;

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

    public static String urlPathEncode(String path) {
        StringBuilder buffer = new StringBuilder();
        for (char c : path.toCharArray()) {
            if (UNSAFE_URLPATH.indexOf(c) >= 0) {
                buffer.append('%');
                buffer.append(toHex(c / 16));
                buffer.append(toHex(c % 16));
            } else if (c < 32 && c > 128) {
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

    public static boolean containsVariableSubstitution( String str ) {
        char[] chars = str.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];
            if( c == '\\' ) {
                i++;
            } else if( c == '$' ) {
                if( nextChar(chars,i) == '{' ) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Same as calling {@link #substituteVariables(String, Map)} with neverFail set to true
     */
    public static String substituteVariables(String str, Map<String, String> variables) throws IllegalArgumentException {
        return substituteVariables(str, variables, true);
    }

    /**
     * Substitute variables in a string.
     *
     * @param str       String to substitute variables from
     * @param variables Variables
     * @param neverFail If set to true, this will suppress any errors and instead return empty string in the invalid part of the variable substituation.
     * @return String with variables substituted
     * @throws IllegalArgumentException If an invalid variable substituation is found and neverFail is set to false
     */
    public static String substituteVariables(String str, Map<String, String> variables, boolean neverFail) throws IllegalArgumentException {
        if (str == null) {
            return null;
        }
        char[] chars = str.toCharArray();
        int nested = 0;
        StringWriter newStr = new StringWriter();
        StringWriter varStr = new StringWriter();
        StringWriter cStr = newStr;
        boolean varParse = false;
        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];
            if (c == '\\') {
                i++;
                cStr.append(chars[i]);
            } else if (c == '$') {
                char nc = nextChar(chars, i);
                if (nc == '{') {
                    i++;
                    if (!varParse) {
                        varParse = true;
                        cStr = varStr;
                    } else {
                        nested++;
                        varStr.append("${");
                    }
                }
            } else if (varParse && c == '}') {
                if (nested > 0) {
                    nested--;
                    varStr.append('}');
                } else {
                    varParse = false;
                    cStr = newStr;
                    newStr.append(resolveVarSub(varStr.toString(), variables, neverFail));
                    varStr = new StringWriter();
                }
            } else {
                cStr.append(c);
            }
        }
        return newStr.toString();
    }

    private static char nextChar(char[] chars, int idx) {
        int newIdx = idx + 1;
        if (idx < chars.length) {
            return chars[newIdx];
        } else {
            return 0;
        }
    }

    private static String resolveVarSub(String exp, Map<String, String> provisioningParams, boolean neverFail) throws IllegalArgumentException {
        if( containsVariableSubstitution(exp) ) {
            exp = substituteVariables(exp,provisioningParams,neverFail);
        }
        Pattern pattern = getVarSubFuncPattern();
        Matcher m = pattern.matcher(exp);
        if (m.find()) {
            String functionName = m.group(1).toLowerCase();
            String functionParams = m.group(2);
            boolean prefixFunc = functionName.equals("p");
            boolean suffixFunc = functionName.equals("s");
            if (prefixFunc || suffixFunc) {
                String[] args = splitTwoArgFunction(functionParams, neverFail);
                if (args != null) {
                    String xfix = args[0];
                    String val = resolveVarSub(args[1],provisioningParams, neverFail);
                    if (StringUtils.isNotBlank(val)) {
                        return suffixFunc ? xfix + val : val + xfix;
                    } else {
                        return val;
                    }
                } else {
                    resolveVarSubFail(exp, neverFail);
                }
            } else if (functionName.equals("u")) {
                return resolveVarSub(functionParams,provisioningParams, neverFail).toUpperCase();
            } else if (functionName.equals("l")) {
                return resolveVarSub(functionParams,provisioningParams, neverFail).toLowerCase();
            } else if (functionName.equals("c")) {
                return capitalize(resolveVarSub(functionParams,provisioningParams, neverFail));
            } else if (functionName.equals("eb64")) {
                return base64Encode(utf8(resolveVarSub(functionParams,provisioningParams, neverFail)));
            } else if (functionName.equals("db64")) {
                return utf8(base64Decode(resolveVarSub(functionParams,provisioningParams, neverFail)));
            } else if (functionName.equals("t")) {
                return functionParams;
            } else {
                resolveVarSubFail(exp, neverFail);
            }
        } else {
            String val = provisioningParams.get(exp);
            if (val == null) {
                if (!neverFail) {
                    throw new IllegalArgumentException("Variable not found: " + exp);
                }
            } else {
                return val;
            }
        }
        return "";
    }

    private static String resolveVarSubFail(String exp, boolean neverFail) {
        if (!neverFail) {
            throw new IllegalArgumentException("Invalid variable substitution expression: " + exp);
        } else {
            return "";
        }
    }

    private static synchronized Pattern getVarSubFuncPattern() {
        if (varSubFuncPattern == null) {
            varSubFuncPattern = Pattern.compile("([a-zA-Z]*?):(.*)");
        }
        return varSubFuncPattern;
    }

    @Nullable
    private static String[] splitTwoArgFunction(String str, boolean neverFail) {
        StringWriter arg1 = new StringWriter();
        StringWriter arg2 = new StringWriter();
        boolean potentialMatch = false;
        boolean match = false;
        for (char c : str.toCharArray()) {
            if (match) {
                arg2.append(c);
            } else if (c == ':') {
                if (potentialMatch) {
                    arg1.append(':');
                }
                potentialMatch = !potentialMatch;
            } else if (potentialMatch) {
                match = true;
                arg2.append(c);
            } else {
                arg1.append(c);
            }
        }
        if (match) {
            return new String[]{arg1.toString(), arg2.toString()};
        } else {
            resolveVarSubFail(str, neverFail);
            return null;
        }
    }
}
