package com.anningtex.commonbasedata.ui.act.contract;

import android.content.Context;
import android.util.Log;

import com.anningtex.commonbasedata.data.api.ApiManager;
import com.anningtex.commonbasedata.data.base.BaseResponse;
import com.anningtex.commonbasedata.data.rx.rx.RxDisposeManager;
import com.anningtex.commonbasedata.data.rx.rx.RxNet;
import com.anningtex.commonbasedata.data.rx.rx.RxNetCallBack;
import com.anningtex.commonbasedata.dialog.LoadingView;
import com.anningtex.commonbasedata.entity.LoginBean;
import com.anningtex.commonbasedata.utils.ToastUtils;

import io.reactivex.Observable;

/**
 * @Author Song
 */
public class LoginContractData implements LoginContract.LoginModel {
    private Context context;
    private LoadingView loadingView;

    @Override
    public void setLogin(Context context, String userName, String pwd, LoginContract.OnClickListener listener) {
        this.context = context;
        loadingView = new LoadingView(context);
        loginData(userName, pwd, listener);
    }

    public void loginData(String userName, String pwd, LoginContract.OnClickListener listener) {
        Observable<BaseResponse<LoginBean>> login = ApiManager.getInstance().getLogin(userName, pwd, "0");
        RxDisposeManager.get().add(getClass().getName(), RxNet.request(login,
                new RxNetCallBack<LoginBean>() {
                    @Override
                    public void onStart() {
                        loadingView.show();
                    }

                    @Override
                    public void onSuccess(String msg, LoginBean data) {
                        listener.moveToIndex(data);
                        Log.e("TAG", "onSuccess: " + data.getRole());
                    }

                    @Override
                    public void onFailure(String msg) {
                        ToastUtils.showShortToast(context, msg);
                        Log.e("TAG", "onFailure: " + msg);
                    }
                }));
    }
}
