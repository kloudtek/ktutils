package com.kloudtek.util.httpclient;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class HttpClientHC31 implements HttpUtilsImpl {
    private org.apache.commons.httpclient.HttpClient client;

    public HttpClientHC31() {
        client = new org.apache.commons.httpclient.HttpClient();
    }

    @Override
    public byte[] postJson(String url, String json) {
        return postJson(url, json, null);
    }

    @Override
    public byte[] postJson(String url, String json, Map<String, Object> headers) {
        if( headers == null ) {
            headers = new HashMap<String, Object>();
        }
        headers.put("Content-Type","application/json");
        headers.put("Accept","application/json");
        return post(url, json, headers);
    }

    @Override
    public byte[] post(String url, String json, Map<String, Object> headers) {
        try {
            HttpPost post = new HttpPost(url);
            post.setEntity(new StringEntity(json));
            if( headers != null ) {
                for (Map.Entry<String, Object> headerEntry : headers.entrySet()) {
                    if( headerEntry instanceof Iterable ) {
                        for (String value : (Iterable<String>) headerEntry.getValue()) {
                            post.addHeader(headerEntry.getKey(),value);
                        }
                    } else {
                        post.addHeader(headerEntry.getKey(), headerEntry.getValue().toString());
                    }
                }
            }
            return new byte[0];
        } catch (UnsupportedEncodingException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
