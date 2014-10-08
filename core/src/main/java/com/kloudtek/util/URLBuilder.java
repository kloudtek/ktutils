/*
 * Copyright (c) 2014 Kloudtek Ltd
 */

package com.kloudtek.util;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class URLBuilder {
    private String baseUrl;
    private final List<Param> parameters = new ArrayList<Param>();

    public URLBuilder(String url) {
        if (url == null) {
            throw new IllegalArgumentException("url mustn't be null");
        }
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
        boolean urlSlash = baseUrl.endsWith("/");
        boolean pathSlash = path.startsWith("/");
        if (!urlSlash && !pathSlash) {
            url.append("/");
        } else if (urlSlash && pathSlash) {
            if (path.length() == 1) {
                return this;
            }
            path = path.substring(1);
        }
        url.append(extractParams(path));
        baseUrl = url.toString();
        return this;
    }

    public URLBuilder add(String key, String value) {
        addEncoded(StringUtils.urlEncode(key), StringUtils.urlEncode(value));
        return this;
    }

    public URLBuilder add(String key, long value) {
        addEncoded(StringUtils.urlEncode(key), Long.toString(value));
        return this;
    }

    public URLBuilder add(String key, int value) {
        addEncoded(StringUtils.urlEncode(key), Integer.toString(value));
        return this;
    }

    public URLBuilder add(String key, byte value) {
        addEncoded(StringUtils.urlEncode(key), Byte.toString(value));
        return this;
    }

    public URLBuilder add(String key, boolean value) {
        addEncoded(StringUtils.urlEncode(key), Boolean.toString(value));
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
