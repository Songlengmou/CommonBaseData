package com.anningtex.commonbasedata.data.api;

import com.anningtex.commonbasedata.data.rx.interceptor.AddCookiesInterceptor;
import com.anningtex.commonbasedata.data.rx.interceptor.CookieInterceptor;
import com.anningtex.commonbasedata.data.rx.interceptor.HttpLoggingInterceptor;
import com.anningtex.commonbasedata.utils.AppLogUtils;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author Song
 */
public class ApiManager {
    private Retrofit client;
    private static volatile AppApi INSTANCE;

    private ApiManager() {
        client = new Retrofit.Builder()
                .baseUrl(AppConstants.Base_Url_Test)
                .client(initClient())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static AppApi getInstance() {
        if (INSTANCE == null) {
            synchronized (ApiManager.class) {
                if (INSTANCE == null) {
                    INSTANCE = new ApiManager().getAppApi();
                }
            }
        }
        return INSTANCE;
    }

    private AppApi getAppApi() {
        return client.create(AppApi.class);
    }

    private static OkHttpClient initClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        //声明日志类
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(message -> AppLogUtils.printE(message));
        //设定日志级别
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        //延时
        builder.addInterceptor(httpLoggingInterceptor)
                //设置参数加密
                //.addInterceptor(new EncryptParamInterceptor())
                .addInterceptor(new AddCookiesInterceptor())
                .addInterceptor(new CookieInterceptor())
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS);
        return builder.build();
    }
}
