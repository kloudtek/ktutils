package com.kloudtek.util.httpclient;

public class HttpClientFactory {
    public static HttpClient create() {
        return new HttpClientHC31();
    }
}
