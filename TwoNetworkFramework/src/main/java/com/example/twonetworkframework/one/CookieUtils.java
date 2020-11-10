package com.example.twonetworkframework.one;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;

/**
 * @author Song
 */

public class CookieUtils {
    /**
     * 将cookie同步到WebView
     *
     * @param url    WebView要加载的url
     * @param cookie 要同步的cookie
     * @return true 同步cookie成功，false同步cookie失败
     * @Author JPH
     */
    public static boolean syncCookie(String url, String cookie, Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            CookieSyncManager.createInstance(context.getApplicationContext());
        }
        CookieManager cookieManager = CookieManager.getInstance();
        //如果没有特殊需求，这里只需要将session id以"key=value"形式作为cookie即可
        cookieManager.setCookie(url, cookie);
        String newCookie = cookieManager.getCookie(url);
        return TextUtils.isEmpty(newCookie) ? false : true;
    }
}
