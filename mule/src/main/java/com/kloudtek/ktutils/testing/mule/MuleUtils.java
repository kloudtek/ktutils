package com.kloudtek.aeon-commons.testing.mule;

import org.mule.DefaultMuleMessage;
import org.mule.api.MuleContext;
import org.mule.api.MuleException;
import org.mule.api.MuleMessage;
import org.mule.api.client.MuleClient;
import org.mule.module.http.api.client.HttpRequestOptionsBuilder;

import java.util.HashMap;
import java.util.Map;

/**
 * Various utility classes for Mule ESB
 */
public class MuleUtils {
    public static MuleMessage sendHttpJsonPost(MuleContext ctx, String url, String json) throws MuleException {
        HashMap<String, Object> headers = new HashMap<String, Object>();
        headers.put("Content-Type","application/json");
        return sendHttp(ctx, url, "POST", json, headers);
    }

    public static MuleMessage sendHttpPost(MuleContext ctx, String url, Object payload) throws MuleException {
        return sendHttp(ctx, url, "POST", payload, new HashMap<String, Object>());
    }

    public static MuleMessage sendHttp(MuleContext ctx, String url, String method, Object payload, Map<String, Object> headers) throws MuleException {
        MuleClient client = ctx.getClient();
        headers.put("http.method", method);
        DefaultMuleMessage msg = new DefaultMuleMessage(payload, headers, ctx);
        return client.send(url, msg, HttpRequestOptionsBuilder.newOptions().method("POST").build());
    }
}
