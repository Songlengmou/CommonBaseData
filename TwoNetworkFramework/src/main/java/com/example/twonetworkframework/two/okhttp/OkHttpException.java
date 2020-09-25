package com.example.twonetworkframework.two.okhttp;

/**
 * @Author Song
 * @Desc: 异常类型
 */
public class OkHttpException extends Throwable {
    private int code;

    public OkHttpException(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
