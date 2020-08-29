package com.aeontronix.common.httpclient;

public class HttpUtils {
    public static HttpUtilsImpl create() {
        return new HttpClientHC31();
    }
}
