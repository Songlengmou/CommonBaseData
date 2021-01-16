package com.anningtex.commonbasedata.test.two;

import android.os.Bundle;
import android.text.TextUtils;
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
import com.example.twonetworkframework.two.okhttp.ExceptionCode;
import com.example.twonetworkframework.two.rx.RxHttp;
import com.example.twonetworkframework.two.rx.RxJsonHttpHandler;
import com.syp.library.BaseRecycleAdapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * @author Song
 * @desc: 2.下方TwoNetworkFramework里的two的调用
 */
public class TestTwoActivity extends BaseActivity {
    @BindView(R.id.title)
    TitleBar title;
    @BindView(R.id.rv_recent)
    RecyclerView rvRecent;

    private BaseRecycleAdapter recycleAdapter;

    private RxHttp rxHttp;
    private String url = AppConstants.Base_Url_Test + "api/v1/getWarehouseDelivernoWithOrderList2";

    @Override
    protected int getLayoutResource() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        return R.layout.activity_test_two;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        title.setLeftListener(v -> {
            onBackPressed();
        });

        rxHttp = new RxHttp();
        getShowData();
    }

    private void getShowData() {
        Map<String, String> map = new HashMap<>();
        map.put("supplier_delivery_no", "YD-AN2006");
        rxHttp.post("Cookie", MainApplication.COOKIE, url, map, new RxJsonHttpHandler<TestOneBean>(TestOneBean.class) {
            @Override
            public void onStart() {
                super.onStart();
                showLoading();
            }

            @Override
            public void onSuccess(TestOneBean testOneBean) {
                super.onSuccess(testOneBean);
                hideLoading();

                if (testOneBean.getCode() == 1) {
                    List<TestOneBean.DataBean> data = testOneBean.getData();
                    if (data != null && data.size() > 0) {
                        recycleAdapter = new BaseRecycleAdapter(R.layout.item_one, data);
                        rvRecent.setLayoutManager(new LinearLayoutManager(TestTwoActivity.this));
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
            public void onError(int code) {
                switch (code) {
                    case ExceptionCode.CODE_REQUEST_FAIL:
                        showToast("请求失败");
                        break;
                    case ExceptionCode.CODE_NET_ERROR:
                        showToast("网络异常");
                        break;
                    case ExceptionCode.CODE_DATA_FORMAT:
                        showToast("请求数据格式不正确");
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onFinish() {
                super.onFinish();
                hideLoading();
            }
        });
    }
}