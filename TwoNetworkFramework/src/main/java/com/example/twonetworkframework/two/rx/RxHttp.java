package com.example.twonetworkframework.two.rx;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.twonetworkframework.two.okhttp.ExceptionCode;
import com.example.twonetworkframework.two.okhttp.HttpUtil;
import com.example.twonetworkframework.two.okhttp.OkHttpException;

import java.io.IOException;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * @Author Song
 * @Desc: 请求类型 以及 返回Log日志
 */
public class RxHttp {
    private HttpUtil httpUtil = new HttpUtil();

    /**
     * GET
     */
    public Observable<String> get(final String headKey, final String headValue, final String url) {
        return Observable.create(emitter -> httpUtil.get(headKey, headValue, url, new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                emitter.onError(new OkHttpException(ExceptionCode.CODE_NET_ERROR));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String body = response.body().string();
                    Log.e("getResponseJsonSuccess", body);
                    emitter.onNext(body);
                    emitter.onComplete();
                } else {
                    emitter.onError(new OkHttpException(ExceptionCode.CODE_REQUEST_FAIL));
                }
            }
        }));
    }

    public void get(final String headKey, final String headValue, String url, final RxHttpHandler handler) {
        get(headKey, headValue, url)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable disposable) {
                        handler.onStart();
                    }

                    @Override
                    public void onNext(@NonNull String s) {
                        handler.onSuccess(s);
                    }

                    @Override
                    public void onError(@NonNull Throwable throwable) {
                        //强转为自定义的 OkHttpException，onError中传入异常类型
                        if (throwable.getClass().equals(OkHttpException.class)) {
                            int code = ((OkHttpException) throwable).getCode();
                            handler.onError(code);
                            handler.onFinish();
                        }
                    }

                    @Override
                    public void onComplete() {
                        handler.onFinish();
                    }
                });
    }

    /**
     * POST
     */
    public Observable<String> post(final String headKey, final String headValue, final String url, final Map<String, String> requestParams) {
        return Observable.create(emitter -> httpUtil.post(headKey, headValue, url, requestParams, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                emitter.onError(new OkHttpException(ExceptionCode.CODE_NET_ERROR));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String body = response.body().string();
                    Log.e("postResponseJsonSuccess", body);
                    emitter.onNext(body);
                    emitter.onComplete();
                } else {
                    emitter.onError(new OkHttpException(ExceptionCode.CODE_REQUEST_FAIL));
                }
            }
        }));
    }

    public void post(final String headKey, final String headValue, final String url, final Map<String, String> requestParams, final RxHttpHandler handler) {
        post(headKey, headValue, url, requestParams)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable disposable) {
                        handler.onStart();
                    }

                    @Override
                    public void onNext(@NonNull String s) {
                        handler.onSuccess(s);
                    }

                    @Override
                    public void onError(@NonNull Throwable throwable) {
                        //强转为自定义的 OkHttpException，onError中传入异常类型
                        if (throwable.getClass().equals(OkHttpException.class)) {
                            int code = ((OkHttpException) throwable).getCode();
                            handler.onError(code);
                            handler.onFinish();
                        }
                    }

                    @Override
                    public void onComplete() {
                        handler.onFinish();
                    }
                });
    }
}
