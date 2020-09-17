package com.anningtex.commonbasedata.ui.act.contract;

import android.content.Context;

import com.anningtex.commonbasedata.entity.LoginBean;

/**
 * @Author Song
 */
public interface LoginContract {
    interface LoginModel {
        void setLogin(Context context, String userName, String pwd, OnClickListener listener);
    }

    interface LoginView {
        String getName();

        String getPwd();

        void moveToIndex(LoginBean data);
    }

    interface OnClickListener {
        void moveToIndex(LoginBean data);
    }
}
