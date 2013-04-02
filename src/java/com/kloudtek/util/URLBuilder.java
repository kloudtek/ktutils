/*
 * Copyright (c) Kloudtek Ltd 2013.
 */

package com.kloudtek.util;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import static com.kloudtek.util.StringUtils.urlEncode;

public class URLBuilder {
    private String baseUrl;
    private final List<Param> parameters = new ArrayList<Param>();

    public URLBuilder(String url) {
        this.baseUrl = extractParams(url);
    }

    public URLBuilder(String baseUrl, String... path) {
        this(baseUrl);
        for (String p : path) {
            addPath(p);
        }
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public List<Param> getParameters() {
        return parameters;
    }

    /**
     * Adds a path to the URL being built.
     * It will first strip out any parameters that might be included in the path, and then append it to the current URL,
     * Inserting a slash sign between the two, if neither the old url doesn't end with one, or the path starts with one.
     *
     * @param path Path to append.
     * @return URLBuilder instance.
     */
    public URLBuilder addPath(String path) {
        StringBuilder url = new StringBuilder(baseUrl);
        if (!baseUrl.endsWith("/") && !path.startsWith("/")) {
            url.append("/");
        }
        url.append(extractParams(path));
        baseUrl = url.toString();
        return this;
    }

    public URLBuilder add(String key, String value) {
        addEncoded(urlEncode(key), urlEncode(value));
        return this;
    }

    public URLBuilder add(String key, long value) {
        addEncoded(urlEncode(key), Long.toString(value));
        return this;
    }

    public URLBuilder add(String key, int value) {
        addEncoded(urlEncode(key), Integer.toString(value));
        return this;
    }

    public URLBuilder add(String key, byte value) {
        addEncoded(urlEncode(key), Byte.toString(value));
        return this;
    }

    public URLBuilder add(String key, boolean value) {
        addEncoded(urlEncode(key), Boolean.toString(value));
        return this;
    }

    public URLBuilder addEncoded(String key, String value) {
        parameters.add(new Param(key, value));
        return this;
    }

    public URL toUrl() {
        try {
            return new URL(toString());
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    public URI toUri() {
        return URI.create(toString());
    }

    @Override
    public String toString() {
        StringBuilder url = new StringBuilder(baseUrl);
        boolean first = true;
        for (Param parameter : parameters) {
            if (first) {
                first = false;
                url.append("?");
            } else {
                url.append("&");
            }
            url.append(parameter.key).append("=").append(parameter.value);
        }
        return url.toString();
    }

    private String extractParams(String baseUrl) {
        int idx = baseUrl.indexOf('?');
        if (idx == -1) {
            return baseUrl;
        } else {
            StringTokenizer tok = new StringTokenizer(baseUrl.substring(idx + 1), "&");
            while (tok.hasMoreTokens()) {
                String p = tok.nextToken();
                int tIdx = p.indexOf("=");
                if (tIdx == -1) {
                    parameters.add(new Param(p, ""));
                } else {
                    parameters.add(new Param(p.substring(0, tIdx), p.substring(tIdx + 1, p.length())));
                }
            }
            return baseUrl.substring(0, idx);
        }
    }

    public class Param {
        private String key;
        private String value;

        public Param(String key, String value) {
            this.key = key;
            this.value = value;
        }
    }
}
