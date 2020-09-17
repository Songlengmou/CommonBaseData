package com.anningtex.commonbasedata.ui.act.presenter;

import android.content.Context;

import com.anningtex.commonbasedata.ui.act.contract.LoginContract;
import com.anningtex.commonbasedata.ui.act.contract.LoginContractData;

/**
 * @Author Song
 */
public class LoginPresenter {
    private Context context;
    private LoginContract.LoginView loginView;
    private LoginContractData loginModelData;

    public LoginPresenter(Context context, LoginContract.LoginView loginView) {
        this.context = context;
        this.loginView = loginView;
        loginModelData = new LoginContractData();
    }

    public void login() {
        String userName = loginView.getName();
        String pwd = loginView.getPwd();
        loginModelData.loginData(userName, pwd, data -> loginView.moveToIndex(data));
    }
}
