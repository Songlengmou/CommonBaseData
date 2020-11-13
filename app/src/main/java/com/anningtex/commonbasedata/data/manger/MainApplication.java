package com.anningtex.commonbasedata.data.manger;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.StrictMode;

import androidx.multidex.MultiDex;

import com.lzy.okgo.OkGo;

/**
 * @Author Song
 */
public class MainApplication extends Application {
    @SuppressLint("StaticFieldLeak")
    private static Context context;
    public static String COOKIE = "";

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        //OkGo
        OkGo.getInstance().init(this);
        // Android 7.0系统解决拍照的问题
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            builder.detectFileUriExposure();
        }
        //MultiDex
        MultiDex.install(this);
    }

    public static Context getContext() {
        return context;
    }

    /**
     * 获取应用程序名称
     */
    public String getApplicationName() {
        PackageManager packageManager = null;
        ApplicationInfo applicationInfo;
        try {
            packageManager = getApplicationContext().getPackageManager();
            applicationInfo = packageManager.getApplicationInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            applicationInfo = null;
        }
        String applicationName = (String) packageManager.getApplicationLabel(applicationInfo);
        return applicationName;
    }
}
