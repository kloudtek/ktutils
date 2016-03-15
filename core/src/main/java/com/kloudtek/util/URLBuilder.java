/*
 * Copyright (c) 2014 Kloudtek Ltd
 */

package com.kloudtek.util;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

public class URLBuilder {
    private String protocol;
    private String userInfo;
    private final List<Param> parameters = new ArrayList<Param>();
    private String host;
    private int port;
    private StringBuilder path;
    private String ref;

    public URLBuilder(String url) {
        if (url == null) {
            throw new IllegalArgumentException("url mustn't be null");
        }
        try {
            URL u = new URL(url);
            protocol = u.getProtocol();
            userInfo = u.getUserInfo();
            host = u.getHost();
            port = u.getPort();
            path = new StringBuilder(u.getPath());
            if (!StringUtils.isEmpty(u.getQuery())) {
                StringTokenizer tok = new StringTokenizer(u.getQuery(), "&");
                while (tok.hasMoreElements()) {
                    String[] kv = tok.nextToken().split("=");
                    if (kv.length != 2) {
                        throw new IllegalArgumentException("Invalid URL query params: " + url);
                    }
                    parameters.add(new Param(kv[0], kv[1]));
                }
            }
            ref = u.getRef();
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException("Invalid url");
        }
    }

    public URLBuilder(String baseUrl, String... path) {
        this(baseUrl);
        for (String p : path) {
            path(p);
        }
    }

    /**
     * Adds a path to the URL being built.
     * It will first strip out any parameters that might be included in the path, and then append it to the current URL,
     * Inserting a slash sign between the two, if neither the old url doesn't end with one, or the path starts with one.
     *
     * @param path Path to append.
     * @return URLBuilder instance.
     */
    @Deprecated
    public URLBuilder addPath(String path) {
        return path(path);
    }

    /**
     * Add a path element to the URL being built. It will add (or remove) / symbols as needed
     *
     * @param path   path
     * @return URLBuilder
     */
    public URLBuilder path(String path) {
        return path(path,false);
    }

    /**
     * Add a path element to the URL being built. It will add (or remove) / symbols as needed (unless encode is true)
     *
     * @param path   path
     * @param encode If true then the any path will be encoded
     * @return URLBuilder
     */
    public URLBuilder path(String path, boolean encode) {
        if (encode) {
            this.path.append(StringUtils.urlPathEncode(path));
        } else {
            boolean leftHasSlash = this.path.charAt(this.path.length() - 1) == '/';
            boolean rightHasSlash = path.startsWith("/");
            if (!leftHasSlash && !rightHasSlash) {
                this.path.append('/');
            }
            if (leftHasSlash && rightHasSlash) {
                this.path.append(path.substring(1, path.length()));
            } else {
                this.path.append(path);
            }
        }
        boolean leftHasSlash = this.path.length() > 0 && this.path.charAt(this.path.length() - 1) == '/';
        boolean rightHasSlash = path.startsWith("/");
        if (!leftHasSlash && !rightHasSlash) {
            this.path.append('/');
        }
        if (leftHasSlash && rightHasSlash) {
            this.path.append(path.substring(1, path.length()));
        } else {
            this.path.append(path);
        }
        return this;
    }

    @Deprecated
    public URLBuilder add(String key, String value) {
        return param(key, value);
    }

    @Deprecated
    public URLBuilder add(String key, long value) {
        return param(key, value);
    }

    @Deprecated
    public URLBuilder add(String key, int value) {
        return param(key, value);
    }

    @Deprecated
    public URLBuilder add(String key, byte value) {
        return param(key, value);
    }

    @Deprecated
    public URLBuilder add(String key, boolean value) {
        return param(key, value);
    }

    @Deprecated
    public URLBuilder addEncoded(String key, String value) {
        return param(key, value, true);
    }

    public URLBuilder param(String key, String value) {
        param(StringUtils.urlEncode(key), StringUtils.urlEncode(value), true);
        return this;
    }

    public URLBuilder param(String key, long value) {
        param(StringUtils.urlEncode(key), Long.toString(value), true);
        return this;
    }

    public URLBuilder param(String key, int value) {
        param(StringUtils.urlEncode(key), Integer.toString(value), true);
        return this;
    }

    public URLBuilder param(String key, byte value) {
        param(StringUtils.urlEncode(key), Byte.toString(value), true);
        return this;
    }

    public URLBuilder param(String key, boolean value) {
        param(StringUtils.urlEncode(key), Boolean.toString(value), true);
        return this;
    }

    public URLBuilder param(String key, String value, boolean encoded) {
        parameters.add(new Param(encoded ? key : StringUtils.urlEncode(key), encoded ? value : StringUtils.urlEncode(value)));
        return this;
    }

    public URL toUrl() {
        try {
            return toUri().toURL();
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException("Invalid url", e);
        }
    }

    public URI toUri() {
        StringBuilder query = new StringBuilder();
        Iterator<Param> i = parameters.iterator();
        while (i.hasNext()) {
            Param p = i.next();
            query.append(p.key).append("=").append(p.value);
            if (i.hasNext()) {
                query.append("&");
            }
        }
        try {
            return new URI(protocol, userInfo, host, port, path.toString(), query.toString(), ref);
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException("Invalid url", e);
        }
    }

    @Override
    public String toString() {
        return toUri().toString();
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
