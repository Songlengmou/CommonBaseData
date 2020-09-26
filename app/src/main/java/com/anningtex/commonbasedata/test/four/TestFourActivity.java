package com.anningtex.commonbasedata.test.four;

import android.os.Bundle;
import android.text.TextUtils;
import android.text.format.Time;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.anningtex.commonbasedata.R;
import com.anningtex.commonbasedata.data.base.BaseActivity;
import com.anningtex.commonbasedata.utils.date.DateUtils;
import com.anningtex.commonbasedata.utils.TransInformation;
import com.anningtex.commonbasedata.utils.date.ChooseCommonData;
import com.anningtex.commonbasedata.weight.actionbar.TitleBar;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Song
 * @desc: 4. 日期展示
 */
public class TestFourActivity extends BaseActivity {
    @BindView(R.id.title)
    TitleBar title;
    @BindView(R.id.et_one)
    EditText etOne;
    @BindView(R.id.et_two)
    EditText etTwo;
    @BindView(R.id.et_three)
    EditText etThree;
    @BindView(R.id.tv_year)
    TextView tvYear;
    @BindView(R.id.tv_mouth)
    TextView tvMouth;
    @BindView(R.id.tv_day)
    TextView tvDay;

    private String now;
    private ChooseCommonData chooseCommonData;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_test_four;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        title.setLeftListener(v -> {
            onBackPressed();
        });
        initView();
    }

    private void initView() {
        etOne.setText(DateUtils.today());

        Time time = new Time("GMT+8");
        time.setToNow();
        int year = time.year;
        int month = time.month;
        int day = time.monthDay;
        etTwo.setText(DateUtils.beforeYearDate(year, month, day));

        etThree.setText("YD-AN" + DateUtils.getCurrentDateString());
        etThree.setTransformationMethod(new TransInformation());
        etThree.setSelection(etThree.length());

        chooseCommonData = new ChooseCommonData(TestFourActivity.this);
        now = DateUtils.now();
        tvYear.setText(now.substring(0, 4) + "年");
        tvMouth.setText(now.substring(4, 6));

        chooseCommonData.data(tvYear, tvDay);
        chooseCommonData.onLongClickMap(tvMouth, tvDay);
    }

    @OnClick({R.id.et_one, R.id.et_two, R.id.et_three, R.id.tv_year, R.id.tv_mouth, R.id.tv_day})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.et_one:
                DateUtils.showDateCommonPickerDialog(TestFourActivity.this, 0, etOne, Calendar.getInstance());
                break;
            case R.id.et_two:
                DateUtils.showDateCommonPickerDialog(TestFourActivity.this, 0, etTwo, Calendar.getInstance());
                break;
            case R.id.et_three:
                DateUtils.showDatePickerDialog(TestFourActivity.this, 0, etThree, Calendar.getInstance());
                break;
            case R.id.tv_year:
                chooseCommonData.selectDate(tvMouth, tvDay, tvYear, "yyyy年", true, false, false);
                break;
            case R.id.tv_mouth:
                if (!TextUtils.isEmpty(tvYear.getText().toString())) {
                    chooseCommonData.tvMmData();
                } else {
                    showToast("请选择年!");
                }
                break;
            case R.id.tv_day:
                if (!TextUtils.isEmpty(tvMouth.getText().toString())) {
                    chooseCommonData.tvDdData(tvMouth);
                } else {
                    showToast("请选择年!");
                }
                break;
            default:
                break;
        }
    }

    public String getDate() {
        String yy = tvYear.getText().toString().replace("年", "");
        String mm = tvMouth.getText().toString().replace("月", "");
        String dd = tvDay.getText().toString().replace("日", "");
        return yy + mm + dd;
    }
}