package com.example.twonetworkframework.one;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

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
     * 异步
     * 注：一般配置会用这个  直接用MainApplication.SESSION即可
     */
    public static void sendOkHttpRequest(String address, RequestBody requestBody, Callback callback) {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .addInterceptor(new HttpLoggingInterceptor())
                .build();
        Request.Builder builder = new Request.Builder()
                .url(address)
                .post(requestBody);
        if (MainApplication.SESSION != null && !MainApplication.SESSION.isEmpty()) {
            builder.addHeader("Cookie", MainApplication.SESSION);
        }
        Request request = builder.build();
        client.newCall(request).enqueue(callback);
    }

    /**
     * 同步
     */
    private static Response response;

    public static Response sendOkHttpRequestSynchronize(String address, RequestBody requestBody) {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .addInterceptor(new HttpLoggingInterceptor())
                .build();
        Request.Builder builder = new Request.Builder()
                .url(address)
                .post(requestBody);
        if (MainApplication.SESSION != null && !MainApplication.SESSION.isEmpty()) {
            builder.addHeader("Cookie", MainApplication.SESSION);
        }
        Request request = builder.build();
        Call call = client.newCall(request);
        try {
            response = call.execute();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return response;
    }

    //同步的调用示例
//    private void eg() {
//        new Thread(() -> {
//            FormBody.Builder requestBuild = new FormBody.Builder();
//            RequestBody requestBody = requestBuild
//                    .add("key", "value")
//                    .build();
//            Response response = OkHttpUtil.sendOkHttpRequestSynchronize(URL, requestBody);
//            try {
//                if (response.body() != null) {
//                    String result = response.body().string();
//                    Log.e("result_Success : ", result);
//                    //start write
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }).start();
//    }
}
