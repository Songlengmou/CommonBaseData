package com.anningtex.commonbasedata.data.manger;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.StrictMode;

import androidx.multidex.MultiDex;

import com.lzy.okgo.OkGo;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author Song
 */
public class MainApplication extends Application {
    @SuppressLint("StaticFieldLeak")
    private static Context context;
    public static String COOKIE = "";
    private static List<Activity> activities;

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

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
//        new Handler(getMainLooper()).post(() -> {
//            while (true) {
//                try {
//                    Looper.loop();//try-catch主线程的所有异常；Looper.loop()内部是一个死循环，出现异常时才会退出，所以这里使用while(true)。
//                } catch (Throwable e) {
//                    Log.d(TAG, "Looper.loop(): " + e.getMessage());
//                }
//            }
//        });
//        //try-catch子线程的所有异常。
//        Thread.setDefaultUncaughtExceptionHandler((t, e) -> {
//            Log.d(TAG, "UncaughtExceptionHandler: " + e.getMessage());
//        });
    }

    public static void addActivity(Activity activity) {
        if (activities == null) {
            activities = new ArrayList<>();
        }
        if (activity != null && !activities.contains(activity)) {
            activities.add(activity);
        }
    }

    public static void deleteActivity(Activity activity) {
        if (activities != null && activities.size() > 0 && activity != null) {
            activities.remove(activity);
        }
    }

    public static void clearActivities() {
        if (activities != null && activities.size() > 0) {
            for (Activity activity : activities) {
                activity.finish();
            }
            activities.clear();
        }
    }
}
