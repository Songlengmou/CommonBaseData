package com.example.twonetworkframework.two.rx;

import com.example.twonetworkframework.two.okhttp.ExceptionCode;
import com.google.gson.Gson;

/**
 * @Author Song
 */
public class RxJsonHttpHandler<T> implements RxHttpHandler {
    private Class<T> clazz;

    public RxJsonHttpHandler(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onSuccess(String s) {
        T t = new Gson().fromJson(s, clazz);
        if (t != null) {
            onSuccess(t);
        } else {
            onError(ExceptionCode.CODE_DATA_FORMAT);
        }
    }

    public void onSuccess(T t) {

    }

    @Override
    public void onError(int code) {

    }

    @Override
    public void onFinish() {

    }
}
