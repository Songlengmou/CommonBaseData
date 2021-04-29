package com.anningtex.commonbasedata.ui.fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.anningtex.commonbasedata.R;
import com.anningtex.commonbasedata.data.api.ApiManager;
import com.anningtex.commonbasedata.data.base.one.BaseFragment;
import com.anningtex.commonbasedata.data.rx.rx.RxNet;
import com.anningtex.commonbasedata.data.rx.rx.RxNetCallBack;
import com.anningtex.commonbasedata.entity.RecentBean;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.syp.library.BaseRecycleAdapter;

import java.util.List;

import butterknife.BindView;

/**
 * @Author Song
 */
public class OneFragment extends BaseFragment {
    @BindView(R.id.common_recycler)
    RecyclerView rvRecent;
    @BindView(R.id.common_refresh)
    SmartRefreshLayout commonRefresh;
    @BindView(R.id.tv_nothing)
    TextView tvNothing;

    private BaseRecycleAdapter recycleAdapter;
    private String date = "YD-AN2006";

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_one;
    }

    @Override
    protected void initData() {
        commonRefresh.setEnableLoadMore(true);
        commonRefresh.finishLoadMoreWithNoMoreData();
        commonRefresh.setOnRefreshListener(refreshLayout -> {
            recycleAdapter.getData().clear();
            getData();
            recycleAdapter.notifyDataSetChanged();
        });

        getData();
    }

    private void getData() {
        addDispose(RxNet.request(ApiManager.getInstance().getOneFrag(date), new RxNetCallBack<List<RecentBean>>() {
            @Override
            public void onStart() {
                showLoading();
            }

            @Override
            public void onSuccess(String msg, List<RecentBean> data) {
                hideLoading();
                commonRefresh.finishRefresh();
                Log.e("666OneFragment", "onSuccess: " + data.size());
                recycleAdapter = new BaseRecycleAdapter(R.layout.item_one, data);
                rvRecent.setLayoutManager(new LinearLayoutManager(getContext()));
                rvRecent.setAdapter(recycleAdapter);
                recycleAdapter.setOnDataToViewListener((helper, item, position) -> {
                    RecentBean recentBean = (RecentBean) item;
                    TextView name = helper.getView(R.id.tv_supplier_name);
                    TextView order = helper.getView(R.id.tv_order_all);
                    helper.setText(R.id.tv_supplier_delivery_no, recentBean.getSupplier_delivery_no());
                    List<String> orderNoList = recentBean.getOrder_no_list();
                    List<String> supplierNameList = recentBean.getSupplier_name_list();
                    int totalSum = 0;
                    int cleanSum = 0;
                    List<RecentBean.DeliverListDetailBean> list = recentBean.getDeliver_list_detail();
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

            @Override
            public void onFailure(String msg) {
                commonRefresh.finishRefresh();
                hideLoading();
                showToast(msg);
                Log.e("666OneFragment", "onFailure: " + msg);
            }
        }));
    }
}
