package com.anningtex.commonbasedata.utils.date;

import android.content.Context;
import android.widget.TextView;

import com.anningtex.commonbasedata.R;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.view.TimePickerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author Song
 * @Desc:选择日期
 */
public class ChooseCommonData {
    private Context context;
    private PickerViewTaskUtils pickerViewTaskUtils, pickerViewTaskUtils1;
    private List<String> stringList;
    private int lastSelectmIndex = 0, lastSelectdIndex = 0;

    public ChooseCommonData(Context context) {
        this.context = context;
    }

    public void data(TextView tvMm, TextView tvDd) {
        List<String> monthData = getMonthData();
        stringList = getDayData(tvMm.getText().toString().replace("月", ""));
        pickerViewTaskUtils = new PickerViewTaskUtils(monthData, context, "", lastSelectmIndex);
        pickerViewTaskUtils1 = new PickerViewTaskUtils(stringList, context, "", lastSelectdIndex);
        pickerViewTaskUtils.setSelectedListener((text, p) -> {
            tvMm.setText(text);
            lastSelectmIndex = p;
        });
        pickerViewTaskUtils1.setSelectedListener((text, p) -> {
            tvDd.setText(text);
            lastSelectdIndex = p;
        });
    }

    public void tvMmData() {
        pickerViewTaskUtils.showPickerView(lastSelectmIndex);
    }

    public void tvDdData(TextView tvMm) {
        stringList = getDayData(tvMm.getText().toString().replace("月", ""));
        pickerViewTaskUtils1.setList(stringList);
        pickerViewTaskUtils1.showPickerView(lastSelectdIndex);
    }

    public void selectDate(final TextView tvMm, final TextView tvDd, final TextView tvYy, final String dateFormat, final boolean y, final boolean m, final boolean d) {
        TimePickerView pvTime = new TimePickerBuilder(context, (date, v) -> {
            if (y) {
                tvYy.setText("年");
            }
            if (m) {
                tvMm.setText("月");
            }
            if (d) {
                tvDd.setText("日");
            }
            tvYy.setText(new SimpleDateFormat(dateFormat).format(date));
        }).setType(new boolean[]{y, m, d, false, false, false}).build();
        pvTime.show();
    }


    public List<String> getDayData(String month) {
        List<String> d = new ArrayList<>();
        if (month.equals("02")) {
            for (int i = 1; i < 30; i++) {
                if (i < 10) {
                    d.add("0" + i);
                } else {
                    d.add(i + "");
                }
            }
        } else {
            for (int i = 1; i < 31; i++) {
                if (i < 10) {
                    d.add("0" + i + "");
                } else {
                    d.add(i + "");
                }
            }
            if (month.equals("01") || month.equals("03") || month.equals("05")
                    || month.equals("07") || month.equals("08") || month.equals("10") || month.equals("12")) {
                d.add("31");
            }
        }
        return d;
    }

    public List<String> getMonthData() {
        List<String> m = new ArrayList<>();
        for (int i = 1; i < 13; i++) {
            if (i < 10) {
                m.add("0" + i);
            } else {
                m.add(i + "");
            }
        }
        return m;
    }

    /**
     * 长按
     */
    public void onLongClickMap(TextView tvMm, TextView tvDd) {
        tvMm.setOnLongClickListener(v -> {
            tvMm.setText("月");
            tvDd.setText("日");
            return true;
        });
        tvDd.setOnLongClickListener(v -> {
            tvDd.setText("日");
            return true;
        });
    }
}
