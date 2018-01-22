package com.kloudtek.util.httpclient;

public class HttpUtils {
    public static HttpUtilsImpl create() {
        return new HttpClientHC31();
    }
}
