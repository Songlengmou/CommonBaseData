package com.anningtex.commonbasedata.data.base;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.anningtex.commonbasedata.data.rx.RxDisposeManager;
import com.anningtex.commonbasedata.dialog.LoadingView;

import butterknife.ButterKnife;
import io.reactivex.disposables.Disposable;

/**
 * @Author Song
 */
@SuppressLint("Registered")
public abstract class BaseFragment extends Fragment implements BaseView {
    protected Context mContext;
    protected LoadingView mLoadingView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mView = inflater.inflate(getLayoutResource(), container, false);
        mContext = getActivity();
        ButterKnife.bind(this, mView);
        mLoadingView = new LoadingView(mContext);
        initData();
        return mView;
    }

    protected abstract int getLayoutResource();

    protected abstract void initData();

    public void addDispose(Disposable disposable) {
        RxDisposeManager.get().add(getClass().getName(), disposable);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RxDisposeManager.get().cancel(getClass().getName());
    }

    @Override
    public void showLoading() {
        if (!mLoadingView.isShowing()) {
            mLoadingView.show();
        }
    }

    @Override
    public void showLoading(String msg) {
        if (!mLoadingView.isShowing()) {
            mLoadingView.setMessage(msg);
            mLoadingView.show();
        }
    }

    @Override
    public void hideLoading() {
        if (mLoadingView.isShowing()) {
            mLoadingView.dismiss();
        }
    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
    }

    public void $startActivity(Class<?> cls) {
        Intent intent = new Intent(getContext(), cls);
        startActivity(intent);
    }

    public void $startActivity(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent(getContext(), cls);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
