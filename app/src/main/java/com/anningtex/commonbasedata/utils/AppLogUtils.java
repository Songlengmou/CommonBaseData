package com.anningtex.commonbasedata.utils;

import android.util.Log;

/**
 * @author Song
 */
public class AppLogUtils {
    private static boolean IsAppLog = true;

    private AppLogUtils() {
    }

    public static void printV(String msg) {
        if (IsAppLog) {
            Log.v("TAGLog", msg);
        }
    }

    public static void printV(String tag, String msg) {
        if (IsAppLog) {
            Log.v(tag, msg);
        }
    }

    public static void printE(String msg) {
        if (IsAppLog) {
            Log.e("TAGLog", msg);
        }
    }

    public static void printE(String tag, String msg) {
        if (IsAppLog) {
            Log.e(tag, msg);
        }
    }
}
