package com.anningtex.commonbasedata.data.rx;

import android.net.ParseException;
import android.util.Log;

import com.google.gson.JsonParseException;

import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONException;

import java.io.FileNotFoundException;
import java.net.ConnectException;

import retrofit2.HttpException;

/**
 * @author Song
 */
public class ExceptionHandle {
    private static final int UNAUTHORIZED = 401;
    private static final int FORBIDDEN = 403;
    private static final int NOT_FOUND = 404;
    private static final int REQUEST_TIMEOUT = 408;
    private static final int INTERNAL_SERVER_ERROR = 500;
    private static final int BAD_GATEWAY = 502;
    private static final int SERVICE_UNAVAILABLE = 503;
    private static final int GATEWAY_TIMEOUT = 504;

    public static String handleException(Throwable e) {
        String errorMsg;
        if (e instanceof HttpException) {
            HttpException httpException = (HttpException) e;
            switch (httpException.code()) {
                case UNAUTHORIZED:
                case FORBIDDEN:
                case NOT_FOUND:
                case REQUEST_TIMEOUT:
                case GATEWAY_TIMEOUT:
                case INTERNAL_SERVER_ERROR:
                case BAD_GATEWAY:
                case SERVICE_UNAVAILABLE:
                default:
                    errorMsg = "网络错误";
                    break;
            }
            return errorMsg + ":" + httpException.code();
        } else if (e instanceof JsonParseException || e instanceof JSONException || e instanceof ParseException) {
            return "解析错误";
        } else if (e instanceof ConnectException) {
            return "连接失败";
        } else if (e instanceof javax.net.ssl.SSLHandshakeException) {
            return "证书验证失败";
        } else if (e instanceof ConnectTimeoutException) {
            return "连接超时";
        } else if (e instanceof java.net.SocketTimeoutException) {
            return "连接超时";
        } else if (e instanceof FileNotFoundException) {
            return "FileNotFoundException";
        } else {
            Log.e("TAG~Log********", "handleException: " + e.toString());
            return "未知错误";
        }
    }
}
