package com.anningtex.commonbasedata.test.seven.act;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.anningtex.commonbasedata.R;
import com.anningtex.commonbasedata.data.base.two.BaseActivity;
import com.anningtex.commonbasedata.databinding.ActivityTestSevenBinding;
import com.anningtex.commonbasedata.test.seven.fragment.SevenOneFragment;
import com.anningtex.commonbasedata.test.seven.fragment.SevenTwoFragment;
import com.anningtex.commonbasedata.weight.pager.TabPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import static com.google.android.material.bottomnavigation.LabelVisibilityMode.LABEL_VISIBILITY_LABELED;

/**
 * @author Song
 * desc:测试Binding
 */
public class SevenActivity extends BaseActivity<ActivityTestSevenBinding> {
    private List<Fragment> fragmentList = new ArrayList<>();
    private List<String> mTitleList = new ArrayList<>();

    @Override
    protected void initViews(Bundle savedInstanceState) {
        binding.mainBottomNav.setLabelVisibilityMode(LABEL_VISIBILITY_LABELED);
        fragmentList.add(new SevenOneFragment());
        fragmentList.add(new SevenTwoFragment());
        setupBottomNavigationView();
        TabPagerAdapter pagerAdapter = new TabPagerAdapter(getSupportFragmentManager(), mTitleList, fragmentList);
        binding.mainFragmentContainer.setAdapter(pagerAdapter);
    }

    private void setupBottomNavigationView() {
        binding.mainBottomNav.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.one:
                    binding.mainFragmentContainer.setCurrentItem(0);
                    break;
                case R.id.two:
                    binding.mainFragmentContainer.setCurrentItem(1);
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + item.getItemId());
            }
            return true;
        });
    }
}