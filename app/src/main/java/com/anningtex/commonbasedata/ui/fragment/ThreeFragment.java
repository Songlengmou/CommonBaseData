package com.anningtex.commonbasedata.ui.fragment;

import android.view.View;

import com.anningtex.commonbasedata.R;
import com.anningtex.commonbasedata.data.base.BaseFragment;
import com.anningtex.commonbasedata.test.five.TestFiveActivity;
import com.anningtex.commonbasedata.test.four.TestFourActivity;
import com.anningtex.commonbasedata.test.one.TestOneActivity;
import com.anningtex.commonbasedata.test.three.TestThreeActivity;
import com.anningtex.commonbasedata.test.two.TestTwoActivity;

import butterknife.OnClick;

/**
 * @Author Song
 */
public class ThreeFragment extends BaseFragment {

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_three;
    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.btn_one, R.id.btn_two, R.id.btn_three, R.id.btn_four, R.id.btn_five})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_one:
                $startActivity(TestOneActivity.class);
                break;
            case R.id.btn_two:
                $startActivity(TestTwoActivity.class);
                break;
            case R.id.btn_three:
                $startActivity(TestThreeActivity.class);
                break;
            case R.id.btn_four:
                $startActivity(TestFourActivity.class);
                break;
            case R.id.btn_five:
                $startActivity(TestFiveActivity.class);
                break;
            default:
                break;
        }
    }
}
