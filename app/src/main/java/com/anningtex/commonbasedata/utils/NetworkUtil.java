package com.anningtex.commonbasedata.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * @Author: Song
 */
public class NetworkUtil {

    /**
     * 判断网络是否可用
     *
     * @param context
     * @return
     */
    public static boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                //这种方法也可以
                //return mNetworkInfo .getState()== NetworkInfo.State.CONNECTED
                return mNetworkInfo.isAvailable();

            }
        }
        return false;
    }
}
