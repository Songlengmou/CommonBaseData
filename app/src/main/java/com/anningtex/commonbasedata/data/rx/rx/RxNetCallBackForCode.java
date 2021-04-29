package com.anningtex.commonbasedata.data.rx.rx;

/**
 * @author Song
 * @Desc:处理数据返回的接口(携带错误码)
 */

public interface RxNetCallBackForCode<T> {
    /**
     * 数据请求成功
     *
     * @param data 请求到的数据
     */
    void onSuccess(T data);

    /**
     * 数据请求失败
     */
    void onFailure(String code, String msg);
}
