package com.anningtex.commonbasedata.ui.fragment;

import android.util.Log;
import android.widget.TextView;

import com.anningtex.commonbasedata.R;
import com.anningtex.commonbasedata.data.api.AppConstants;
import com.anningtex.commonbasedata.data.base.BaseFragment;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;

import butterknife.BindView;

/**
 * @Author Song
 */
public class TwoFragment extends BaseFragment {
    @BindView(R.id.tv_read)
    TextView tvRead;

    private String url = AppConstants.Base_Url_Test + "api/v1/getWarehouseDelivernoWithOrderList2";

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_two;
    }

    @Override
    protected void initData() {
        OkGo.<String>post(url)
                .headers("Cookie", AppConstants.COOKIE)
                .params("supplier_delivery_no", "YD-AN2006")
                .execute(new StringCallback() {
                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
                        showLoading();
                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        hideLoading();
                        String body = response.body();
                        Log.e("666TwoFragment", "onSuccess: " + body);
                        tvRead.setText(body);
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        hideLoading();
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        hideLoading();
                        String body = response.body();
                        Log.e("666TwoFragment", "onError: " + body);
                    }
                });
    }
}
