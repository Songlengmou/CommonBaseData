package com.example.twonetworkframework.one.test;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.fragment.app.Fragment;

import com.example.twonetworkframework.one.OkHttpUtil;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.RequestBody;

/**
 * @Author Song
 * desc:在Fragment中单纯的对此方法进行测试
 */
public class TestLoginFragment extends Fragment {

    private void loginReturnData() {
        String url = "";
        FormBody.Builder requestBuild = new FormBody.Builder();
        RequestBody requestBody = requestBuild
                .add("", "")
                .build();
        OkHttpUtil.sendOkHttpRequest(url, requestBody, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("Fail : ", e.getMessage());
            }

            @Override
            public void onResponse(Call call, okhttp3.Response response) throws IOException {
                String result = response.body().string();
                if (response.body() != null) {
                    response.body().close();
                }
                Log.e("Success : ", result);
                new Thread(() -> {
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(() -> {
                        // 解析设置数据
                    });
                }).start();
            }
        });
    }
}
