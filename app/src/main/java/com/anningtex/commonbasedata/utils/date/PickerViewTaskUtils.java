package com.anningtex.commonbasedata.utils.date;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;

import com.anningtex.commonbasedata.R;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.view.OptionsPickerView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Song
 * desc:时间选择器
 */
public class PickerViewTaskUtils {
    private List<String> list;
    private Context context;
    private OptionsPickerView pv;
    private List<String> firstList = new ArrayList<>();
    private String title;
    private int index = 0;
    private SelectedListener selectedListener;

    /**
     * @param list  数据源
     * @param title 标题文字
     * @param index 上次选中位置
     */
    public PickerViewTaskUtils(List<String> list, Context context, String title, int index) {
        this.list = list;
        this.context = context;
        this.title = title;
        this.index = index;
    }

    public void showPickerView(int position) {
        firstList.add("");
        pv = new OptionsPickerBuilder(context, (options1, options2, options3, v) -> {
            Log.e("选择1", list.get(options2));

            if (selectedListener != null) {
                selectedListener.selected(list.get(options2), options2);
            }
        })
                .setTitleText(title)
                //确定按钮文字
                .setSubmitText("True")
                //取消按钮文字
                .setCancelText("Cancel")
                .setDividerColor(Color.WHITE)
                //标题背景颜色 Night mode
                .setTitleBgColor(Color.WHITE)
                //滚轮背景颜色 Night mode
                .setBgColor(Color.WHITE)
                //滚轮文字大小
                .setContentTextSize(16)
                .setTextColorCenter(context.getResources().getColor(R.color.colorPrimary))
                //设置是否联动，默认true
                //.setLinkage(false)
                //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .isCenterLabel(false)
                //循环与否
                .setCyclic(false, false, false)
                //设置默认选中项
                .setSelectOptions(0, position, 0)
                //点击外部dismiss default true
                .setOutSideCancelable(true)
                //是否显示为对话框样式
                .isDialog(false)
                .build();
        pv.setNPicker(firstList, list, firstList);
        pv.show();
    }

    public void setSelectedListener(SelectedListener selectedListener) {
        this.selectedListener = selectedListener;
    }

    public interface SelectedListener {
        void selected(String text, int p);
    }

    public void setList(List<String> list) {
        this.list.clear();
        this.list = list;
    }
}
