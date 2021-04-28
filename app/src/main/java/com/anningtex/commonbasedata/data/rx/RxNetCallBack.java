package com.anningtex.commonbasedata.data.rx;

/**
 * @author Song
 */
public interface RxNetCallBack<T> {
    void onStart();

    /**
     * 数据请求成功
     */
    void onSuccess(String msg, T data);

    /**
     * 数据请求失败
     */
    void onFailure(String msg);
}
