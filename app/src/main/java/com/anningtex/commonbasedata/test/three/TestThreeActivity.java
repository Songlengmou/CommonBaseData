package com.anningtex.commonbasedata.test.three;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.anningtex.commonbasedata.R;
import com.anningtex.commonbasedata.data.api.AppConstants;
import com.anningtex.commonbasedata.data.base.one.BaseActivity;
import com.anningtex.commonbasedata.data.manger.MainApplication;
import com.anningtex.commonbasedata.entity.TestOneBean;
import com.anningtex.commonbasedata.weight.actionbar.TitleBar;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.syp.library.BaseRecycleAdapter;

import java.util.List;

import butterknife.BindView;
import cz.msebera.android.httpclient.Header;

/**
 * @author Song
 * @Desc: 3. AsyncHttpClient
 */
public class TestThreeActivity extends BaseActivity {
    @BindView(R.id.title)
    TitleBar title;
    @BindView(R.id.rv_recent)
    RecyclerView rvRecent;

    private BaseRecycleAdapter recycleAdapter;
    private String url = AppConstants.Base_Url_Test + "api/v1/getWarehouseDelivernoWithOrderList2";

    @Override
    protected int getLayoutResource() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        return R.layout.activity_test_three;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        title.setLeftListener(v -> {
            onBackPressed();
        });
        getShowData();
    }

    private void getShowData() {
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        asyncHttpClient.addHeader("Cookie", MainApplication.COOKIE);
        asyncHttpClient.setTimeout(90000);
        asyncHttpClient.setConnectTimeout(90000);
        asyncHttpClient.setResponseTimeout(90000);
        RequestParams params = new RequestParams();
        params.put("supplier_delivery_no", "YD-AN2006");
        asyncHttpClient.post(url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();
                showLoading();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                hideLoading();
                String response = new String(responseBody);
                Log.e("Three_Success", response);
                TestOneBean testOneBean = new Gson().fromJson(response, TestOneBean.class);
                if (testOneBean.getCode() == 1) {
                    List<TestOneBean.DataBean> data = testOneBean.getData();
                    if (data != null && data.size() > 0) {
                        recycleAdapter = new BaseRecycleAdapter(R.layout.item_one, data);
                        rvRecent.setLayoutManager(new LinearLayoutManager(TestThreeActivity.this));
                        rvRecent.setAdapter(recycleAdapter);
                        recycleAdapter.setOnDataToViewListener((helper, item, position) -> {
                            TestOneBean.DataBean dataBean = (TestOneBean.DataBean) item;
                            TextView name = helper.getView(R.id.tv_supplier_name);
                            TextView order = helper.getView(R.id.tv_order_all);
                            helper.setText(R.id.tv_supplier_delivery_no, dataBean.getSupplier_delivery_no());
                            List<String> orderNoList = dataBean.getOrder_no_list();
                            List<String> supplierNameList = dataBean.getSupplier_name_list();
                            int totalSum = 0;
                            int cleanSum = 0;
                            List<TestOneBean.DataBean.DeliverListDetailBean> list = dataBean.getDeliver_list_detail();
                            if (list != null) {
                                for (int i = 0; i < list.size(); i++) {
                                    int checked = list.get(i).getChecked();
                                    if (checked == 1) {
                                        helper.setText(R.id.tv_checked, "清点完成");
                                    } else {
                                        helper.setText(R.id.tv_checked, "");
                                    }

                                    int balesQuantity = list.get(i).getBales_quantity();
                                    totalSum += balesQuantity;
                                    int warehouseCount = list.get(i).getWarehouse_count();
                                    cleanSum += warehouseCount;
                                }
                                helper.setText(R.id.tv_totalBales, "发出: " + totalSum + " 包");
                                helper.setText(R.id.tv_input, "已入: " + cleanSum + " 包");
                            }

                            String tempOrder = "";
                            String tempName = "";
                            for (int i = 0; i < orderNoList.size(); i++) {
                                tempOrder += orderNoList.get(i) + " , ";
                            }

                            for (int i = 0; i < supplierNameList.size(); i++) {
                                tempName += supplierNameList.get(i);
                            }

                            if (TextUtils.isEmpty(tempOrder)) {
                                order.setVisibility(View.GONE);
                            } else {
                                order.setVisibility(View.VISIBLE);
                                order.setText(tempOrder);
                            }

                            if (TextUtils.isEmpty(tempName)) {
                                name.setVisibility(View.GONE);
                            } else {
                                name.setVisibility(View.VISIBLE);
                                name.setText(tempName);
                            }
                        });
                    }
                } else {
                    showToast(testOneBean.getMsg());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                hideLoading();
                Log.e("error", error.getMessage());
                Log.e("statusCode", statusCode + "");
            }
        });
    }
}