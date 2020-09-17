package com.anningtex.commonbasedata.ui.act;

import android.os.Bundle;
import android.widget.EditText;

import com.anningtex.commonbasedata.R;
import com.anningtex.commonbasedata.data.base.BaseActivity;
import com.anningtex.commonbasedata.data.manger.AppManager;
import com.anningtex.commonbasedata.entity.LoginBean;
import com.anningtex.commonbasedata.ui.act.contract.LoginContract;
import com.anningtex.commonbasedata.ui.act.presenter.LoginPresenter;
import com.anningtex.commonbasedata.utils.MD5Util;


import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Song
 */
public class LoginActivity extends BaseActivity implements LoginContract.LoginView {
    @BindView(R.id.login_edit_username)
    EditText loginEditUsername;
    @BindView(R.id.login_edit_password)
    EditText loginEditPassword;

    private LoginPresenter loginPresenter;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_login;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        loginPresenter = new LoginPresenter(LoginActivity.this, this);
    }

    @OnClick(R.id.btn_login)
    public void onViewClicked() {
        //1. 第一种
        loginPresenter.login();

        //2. 第二种
//        loginData();
    }

    @Override
    public String getName() {
        return loginEditUsername.getText().toString().trim();
    }

    @Override
    public String getPwd() {
        return MD5Util.md5(loginEditPassword.getText().toString().trim());
    }

    @Override
    public void moveToIndex(LoginBean data) {
        if (data.getIsSimplePWD() == 0) {
            if ("义乌仓库".equals(data.getRole())) {
                $startActivity(MainActivity.class);
            }
        }
        AppManager.getAppManager().finishCurrentActivity();
    }

//-------------------------------------------第二种-----------------------------------------------------------------------
    //    private void loginData() {
//        showLoading();
//        Map<String, String> map = new ArrayMap<>();
//        map.put("username", loginEditUsername.getText().toString().trim());
//        String pwd = MD5Util.md5(loginEditPassword.getText().toString().trim());
//        map.put("password", pwd);
//        map.put("device_id", "0");
//        Log.e("登录params", "params: " + map.toString());
//        addDispose(RxNet.request(ApiManager.getInstance().getLogin(map), new RxNetCallBack<LoginBean>() {
//            @Override
//            public void onSuccess(LoginBean data) {
//                hideLoading();
//                Log.e("TAG", "IsSimplePWD: " + data.getIsSimplePWD() + " Role: " + data.getRole());
//
//                if (data.getIsSimplePWD() == 0) {
//                    if ("义乌仓库".equals(data.getRole())) {
//                        $startActivity(MainActivity.class);
//                    }
//                }
//                AppManager.getAppManager().finishCurrentActivity();
//            }
//
//            @Override
//            public void onFailure(String msg) {
//                hideLoading();
//                showToast(msg);
//                Log.e("TAG", "onFailure: " + msg);
//            }
//        }));
//    }
}