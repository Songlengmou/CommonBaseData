package com.example.twonetworkframework.one;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * @Author Song
 * @Desc:1. 直接使用
 */
public class OkHttpUtil {

    public static void sendOkHttpRequest(String headKey, String headValue, String address, RequestBody requestBody, Callback callback) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .addHeader(headKey, headValue)
                .url(address)
                .post(requestBody)
                .build();
        client.newCall(request).enqueue(callback);
    }
}
