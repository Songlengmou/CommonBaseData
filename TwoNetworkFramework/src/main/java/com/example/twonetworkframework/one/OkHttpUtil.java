package com.example.twonetworkframework.one;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * @Author Song
 * @Desc:1. 直接使用OkHttp
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

    /**
     * 注：一般配置会用这个  直接用MainApplication.SESSION即可
     */
    public static void sendOkHttpRequest(String address, RequestBody requestBody, Callback callback) {
        OkHttpClient client = new OkHttpClient();
        Request.Builder builder = new Request.Builder()
                .url(address)
                .post(requestBody);
        if (MainApplication.SESSION != null && !MainApplication.SESSION.isEmpty()) {
            builder.addHeader("Cookie", MainApplication.SESSION);
        }
        Request request = builder.build();
        client.newCall(request).enqueue(callback);
    }
}
