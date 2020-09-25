package com.example.twonetworkframework.two.rx;

/**
 * @Author Song
 */
public interface RxHttpHandler {
    void onStart();

    void onSuccess(String s);

    void onError(int code);

    void onFinish();
}
