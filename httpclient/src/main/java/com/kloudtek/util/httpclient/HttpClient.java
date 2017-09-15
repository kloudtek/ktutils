package com.kloudtek.util.httpclient;

import java.util.Map;

public interface HttpClient {
    byte[] postJson(String url, String json);
    byte[] postJson(String url, String json, Map<String,Object> headers);
    byte[] post(String url, String json, Map<String,Object> headers);
}
