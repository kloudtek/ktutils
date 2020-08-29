package com.aeontronix.common.httpclient;

import java.util.Map;

public interface HttpUtilsImpl {
    byte[] postJson(String url, String json);
    byte[] postJson(String url, String json, Map<String,Object> headers);
    byte[] post(String url, String json, Map<String,Object> headers);
}
