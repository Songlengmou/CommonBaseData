package com.anningtex.commonbasedata.data.rx;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.anningtex.commonbasedata.data.manger.MainApplication;
import com.anningtex.commonbasedata.data.api.AppConstants;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * @Author: Song
 * @desc: Cookie
 */
public class CookieInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Response originalResponse = chain.proceed(chain.request());
        if (!originalResponse.headers("Set-Cookie").isEmpty()) {
            HashSet<String> cookies = new HashSet<>();
            final List<String> headers = originalResponse.headers("Set-Cookie");
            for (int i = 0; i < headers.size(); i++) {
                AppConstants.COOKIE += headers.get(i) + " ; ";
            }

            for (String header : originalResponse.headers("Set-Cookie")) {
                cookies.add(header);
            }
            Log.e("cookie size", cookies.toString() + cookies.size());
            SharedPreferences.Editor config = MainApplication.getContext().getSharedPreferences("config", Context.MODE_PRIVATE).edit();
            config.putStringSet("cookie", cookies);
            config.commit();
        }
        return originalResponse;
    }
}
