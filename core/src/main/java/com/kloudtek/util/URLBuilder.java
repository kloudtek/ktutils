/*
 * Copyright (c) 2014 Kloudtek Ltd
 */

package com.kloudtek.util;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.*;

import static com.kloudtek.util.StringUtils.isNotEmpty;

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
        URI u = URI.create(url);
        protocol = u.getScheme();
        userInfo = u.getUserInfo();
        host = u.getHost();
        port = u.getPort();
        path = new StringBuilder();
        String uPath = u.getPath();
        if (uPath != null) {
            if (!uPath.startsWith("/")) {
                path.append('/');
            }
            this.path.append(uPath);
        }
        if (isNotEmpty(u.getQuery())) {
            parseQueryParams(u.getQuery());
        }
        ref = u.getFragment();
    }

    private void parseQueryParams(String query) {
        StringTokenizer tok = new StringTokenizer(query, "&");
        while (tok.hasMoreElements()) {
            String[] kv = tok.nextToken().split("=");
            if (kv.length != 2) {
                throw new IllegalArgumentException("Invalid URL query params: " + Arrays.toString(kv));
            }
            parameters.add(new Param(kv[0], kv[1]));
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
     * @param path path
     * @return URLBuilder
     */
    public URLBuilder path(String path) {
        return path(path, false);
    }

    /**
     * Add a path element to the URL being built separate by a /. It will also parse and add any query parameters present.
     *
     * @param path   path
     * @param encode If true then the any path will be encoded, query parameters won't be parsed and a starting / will not be remove should there be a slash at end of current path.
     * @return URLBuilder
     */
    public URLBuilder path(String path, boolean encode) {
        boolean leftHasSlash = this.path.length() > 0 && this.path.charAt(this.path.length() - 1) == '/';
        if (encode) {
            if (!leftHasSlash) {
                this.path.append('/');
            }
            this.path.append(StringUtils.urlPathEncode(path));
        } else {
            int qidx = path.indexOf("?");
            if (qidx != -1) {
                parseQueryParams(path.substring(qidx + 1, path.length()));
                path = path.substring(0, qidx);
            }
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

    public URLBuilder setUserInfo(String userInfo) {
        this.userInfo = StringUtils.urlEncode(userInfo);
        return this;
    }

    public URLBuilder setProtocol( String protocol ) {
        this.protocol = protocol;
        return this;
    }

    public URLBuilder setRef(String ref) {
        this.ref = StringUtils.urlEncode(ref);
        return this;
    }

    public URLBuilder setHost(String host) {
        this.host = host;
        return this;
    }

    public URLBuilder setPort(int port) {
        this.port = port;
        return this;
    }

    public URL toUrl() {
        try {
            return new URL(toString());
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public URI toUri() {
        return URI.create(toString());
    }

    @Override
    public String toString() {
        StringBuilder url = new StringBuilder();
        if (isNotEmpty(protocol)) {
            url.append(protocol).append("://");
        }
        if (isNotEmpty(userInfo)) {
            url.append(StringUtils.urlEncode(userInfo)).append('@');
        }
        if (isNotEmpty(host)) {
            url.append(host);
        }
        if (port != -1) {
            url.append(':').append(port);
        }
        url.append(path.toString());
        if (!parameters.isEmpty()) {
            url.append('?');
            Iterator<Param> i = parameters.iterator();
            while (i.hasNext()) {
                Param p = i.next();
                url.append(p.key).append("=").append(p.value);
                if (i.hasNext()) {
                    url.append("&");
                }
            }
        }
        if( ref != null ) {
            url.append('#').append(ref);
        }
        return url.toString();
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
