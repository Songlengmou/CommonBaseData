package com.anningtex.commonbasedata.data.base.two;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewbinding.ViewBinding;

import java.util.List;

/**
 * @Author: Song
 */
public abstract class BaseLazyFragment<T extends ViewBinding> extends BaseFragment<T> {
    /**
     * 当前Fragment是否首次可见，默认是首次可见
     **/
    private boolean mIsFirstVisible = true;
    /**
     * 当前Fragment的View是否已经创建
     **/
    private boolean isViewCreated = false;
    /**
     * 当前Fragment的可见状态，一种当前可见，一种当前不可见
     **/
    private boolean currentVisibleState = false;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        isViewCreated = true;
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isViewCreated) {
            if (isVisibleToUser && !currentVisibleState) {
                disPatchFragment(true);
            } else if (!isVisibleToUser && currentVisibleState) {
                disPatchFragment(false);
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!isHidden() && getUserVisibleHint()) {
            disPatchFragment(true);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!mIsFirstVisible) {
            if (!isHidden() && !getUserVisibleHint() && currentVisibleState) {
                disPatchFragment(true);
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (!isHidden() && getUserVisibleHint()) {
            disPatchFragment(false);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isViewCreated = false;
        mIsFirstVisible = true;
    }

    /**
     * @param visible Fragment当前是否可见，然后调用相关方法
     */
    public void disPatchFragment(boolean visible) {
        String aa = getClass().getSimpleName();
        if (visible && isParentFragmentVisible()) {
            return;
        }
        if (currentVisibleState == visible) {
            return;
        }
        currentVisibleState = visible;
        if (visible) {
            if (mIsFirstVisible) {
                mIsFirstVisible = false;
                onFragmentFirst();
            }
            onFragmentVisible();
            dispatchChildFragmentVisibleState(true);
        } else {
            onFragmentInVisible();
            dispatchChildFragmentVisibleState(false);
        }
    }

    /**
     * 重新分发给子Fragment
     */
    private void dispatchChildFragmentVisibleState(boolean visible) {
        FragmentManager childFragmentManager = getChildFragmentManager();
        @SuppressLint("RestrictedApi") List<Fragment> fragments = childFragmentManager.getFragments();
        if (fragments != null) {
            if (!fragments.isEmpty()) {
                for (Fragment child : fragments) {
                    if (child instanceof BaseLazyFragment && !child.isHidden() && child.getUserVisibleHint()) {
                        disPatchFragment(visible);
                    }
                }
            }
        }
    }

    /**
     * Fragment首次可见的方法
     */
    public void onFragmentFirst() {
        Log.e(getClass().getSimpleName(), "首次可见");
    }

    /**
     * Fragment可见的方法
     */
    public void onFragmentVisible() {
        //子Fragment调用次方法，执行可见操作
        Log.e(getClass().getSimpleName(), "可见");
    }

    /**
     * Fragment不可见的方法
     */
    public void onFragmentInVisible() {
        Log.e(getClass().getSimpleName(), "不可见");
    }

    /**
     * 判断多层嵌套的父Fragment是否显示
     */
    private boolean isParentFragmentVisible() {
        BaseLazyFragment fragment = (BaseLazyFragment) getParentFragment();
        return fragment != null && !fragment.getCurrentVisibleState();
    }

    private boolean getCurrentVisibleState() {
        return currentVisibleState;
    }
}
