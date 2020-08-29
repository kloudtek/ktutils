package com.aeontronix.commons.httpclient;

public class HttpUtils {
    public static HttpUtilsImpl create() {
        return new HttpClientHC31();
    }
}
