package com.example.twonetworkframework.one.test;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.twonetworkframework.one.CookieUtils;
import com.example.twonetworkframework.one.MainApplication;
import com.example.twonetworkframework.one.OkHttpUtil;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.RequestBody;

/**
 * @Author Song
 * @Desc:在Activity中单纯的对此方法进行测试
 */
public class TestLoginActivity extends AppCompatActivity {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }

    private void loginReturnData() {
        String url = "";
        FormBody.Builder requestBuild = new FormBody.Builder();
        RequestBody requestBody = requestBuild
                .add("", "")
                .build();
        OkHttpUtil.sendOkHttpRequest(url, requestBody, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
//                Log.e("Fail : ", e.toString());
            }

            @Override
            public void onResponse(Call call, okhttp3.Response response) throws IOException {
                String result = response.body().string();
                if (response.body() != null) {
                    response.body().close();
                }
//                Log.e("Success : ", result);
                MainApplication.SESSION = response.headers().get("Set-Cookie");
                CookieUtils.syncCookie(url, MainApplication.SESSION, TestLoginActivity.this);
                Log.e("SESSION : ", MainApplication.SESSION);

                runOnUiThread(() -> {
                    //解析设置数据
                });
            }
        });
    }
}
