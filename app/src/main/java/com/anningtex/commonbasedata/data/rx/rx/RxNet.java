package com.anningtex.commonbasedata.data.rx.rx;

import com.anningtex.commonbasedata.data.base.BaseResponse;
import com.anningtex.commonbasedata.utils.AppLogUtils;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author Song
 */
public class RxNet {
    /**
     * 返回数据带有body
     */
    public static <T> Disposable request(Observable<BaseResponse<T>> observable, final RxNetCallBack<T> callBack) {
        return observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorReturn(throwable -> {
                    callBack.onFailure(ExceptionHandle.handleException(throwable));
                    return null;
                })
                .subscribe(tBaseResponse -> {
                    if (tBaseResponse.getCode().equals("1")) {
                        callBack.onSuccess(tBaseResponse.getMsg(), tBaseResponse.getData());
                    } else {
                        callBack.onFailure(tBaseResponse.getMsg());
                    }
                }, throwable -> AppLogUtils.printE("请求错误:" + throwable.getMessage()));
    }

    /**
     * 返回数据没有body
     */
    public static Disposable requestWithoutBody(Observable<BaseResponse> observable, final RxNetCallBack<String> callBack) {
        return observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorReturn(throwable -> {
                    callBack.onFailure(ExceptionHandle.handleException(throwable));
                    return null;
                })
                .subscribe(baseResponse -> {
                    if (baseResponse.getCode().equals("1")) {
                        callBack.onSuccess(baseResponse.getMsg(), baseResponse.getMsg());
                    } else {
                        callBack.onFailure(baseResponse.getMsg());
                    }
                }, throwable -> AppLogUtils.printE("请求错误:" + throwable.getMessage()));
    }

    /**
     * 请求返回错误请求码
     * 0表示请求失败
     */
    public static <T> Disposable requestForCode(Observable<BaseResponse<T>> observable, final RxNetCallBackForCode<T> callBack) {
        return observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorReturn(throwable -> {
                    // 0表示请求失败
                    callBack.onFailure("0", ExceptionHandle.handleException(throwable));
                    return null;
                })
                .subscribe(tBaseResponse -> {
                    if (tBaseResponse.getCode().equals("1")) {
                        callBack.onSuccess(tBaseResponse.getData());
                    } else {
                        callBack.onFailure(tBaseResponse.getCode(), tBaseResponse.getMsg());
                    }
                }, throwable -> AppLogUtils.printE("请求错误:" + throwable.getMessage()));
    }
}
