package com.anningtex.commonbasedata.test.seven.fragment;

import com.anningtex.commonbasedata.data.base.two.BaseLazyFragment;
import com.anningtex.commonbasedata.databinding.FragmentSevenCommonBinding;

/**
 * @Author Song
 */
public class SevenTwoFragment extends BaseLazyFragment<FragmentSevenCommonBinding> {

    @Override
    public void onFragmentFirst() {
        super.onFragmentFirst();
        binding.tvData.setText("TwoPostData");
    }

    @Override
    protected void initData() {
        binding.tvCs.setText("Two");
    }
}
