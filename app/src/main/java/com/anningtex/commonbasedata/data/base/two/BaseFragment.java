package com.anningtex.commonbasedata.data.base.two;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewbinding.ViewBinding;

import com.anningtex.commonbasedata.data.base.BaseView;
import com.anningtex.commonbasedata.dialog.LoadingView;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import butterknife.ButterKnife;

/**
 * @author Song
 */
public abstract class BaseFragment<T extends ViewBinding> extends Fragment implements BaseView {
    public Context mContext;
    public View mView;
    private LoadingView mLoadingView;
    public T binding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Type type = getClass().getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            Log.e("TAG", "type is true");
            try {
                Class<T> clazz = (Class<T>) ((ParameterizedType) type).getActualTypeArguments()[0];
                Method method = clazz.getMethod("inflate", LayoutInflater.class);
                binding = (T) method.invoke(null, getLayoutInflater());
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            View root = binding.getRoot();
            ButterKnife.bind(this, root);
            return root;
        } else {
            mView = LayoutInflater.from(getActivity()).inflate(getLayoutResId(), container, false);
            ButterKnife.bind(this, mView);
            return mView;
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mContext = getActivity();
        mLoadingView = new LoadingView(mContext);
        initData();
    }

    protected int getLayoutResId() {
        return 0;
    }

    protected abstract void initData();

    public void $startActivity(Class<?> cls) {
        Intent intent = new Intent(getContext(), cls);
        startActivity(intent);
    }

    public void $startActivity(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent(getContext(), cls);
        intent.putExtras(bundle);
        startActivity(intent);
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
        new Thread(() -> {
            Looper.prepare();
            Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
            Looper.loop();
        }).start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
