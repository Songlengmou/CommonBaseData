package com.example.twonetworkframework.two.okhttp;

import java.util.Map;

import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * @Author Song
 * @Desc:请求
 */
public class HttpUtil {
    private OkHttpClient client;

    public HttpUtil() {
        client = new OkHttpClient();
    }

    public void get(String headKey, String headValue, String url, Callback callback) {
        Request request = new Request.Builder().addHeader(headKey, headValue).url(url).build();
        client.newCall(request).enqueue(callback);
    }

    public void post(String headKey, String headValue, String url, Map<String, String> requestParams, Callback callback) {
        FormBody.Builder bodyBuilder = new FormBody.Builder();
        for (Map.Entry<String, String> entry : requestParams.entrySet()) {
            bodyBuilder.add(entry.getKey(), entry.getValue());
        }
        Request request = new Request.Builder()
                .addHeader(headKey, headValue)
                .url(url)
                .post(bodyBuilder.build())
                .build();
        client.newCall(request).enqueue(callback);
    }
}
