package com.anningtex.commonbasedata.data.rx;

import android.content.Context;
import android.util.Log;

import com.anningtex.commonbasedata.data.manger.MainApplication;

import java.io.IOException;
import java.util.HashSet;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @Author: Song
 * @Desc:AddCookie
 */
public class AddCookiesInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder builder = chain.request().newBuilder();
        HashSet<String> preferences = (HashSet) MainApplication.getContext().getSharedPreferences("config",
                Context.MODE_PRIVATE).getStringSet("cookie", null);
        if (preferences != null) {
            for (String cookie : preferences) {
                Log.e("OkHttp", "Adding Header: " + cookie);
                // This is done so I know which headers are being added; this interceptor is used after the normal logging of OkHttp
            }
        }
        Log.e("OkHttp", "Adding Header: " + MainApplication.COOKIE);
        builder.addHeader("Cookie", MainApplication.COOKIE);
        return chain.proceed(builder.build());
    }
}
