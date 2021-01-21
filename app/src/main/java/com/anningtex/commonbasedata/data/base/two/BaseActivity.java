package com.anningtex.commonbasedata.data.base.two;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.os.LocaleList;
import android.os.Looper;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewbinding.ViewBinding;

import com.anningtex.commonbasedata.data.base.BaseView;
import com.anningtex.commonbasedata.data.manger.MainApplication;
import com.anningtex.commonbasedata.dialog.LoadingView;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Locale;

import butterknife.ButterKnife;

/**
 * @author Song
 */
public abstract class BaseActivity<T extends ViewBinding> extends AppCompatActivity implements BaseView {
    public Context mContext;
    public LoadingView mLoadingView;
    protected T binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (isNoActionBar()) {
            initActionBar();
        }
        bindContentView();
        MainApplication.addActivity(this);
        mLoadingView = new LoadingView(this);
        setLanguage();
        closeWebViewDocumentDraw();
        mContext = this;
        ButterKnife.bind(this);
        initViews(savedInstanceState);
    }

    private void initActionBar() {
        Window window = getWindow();
        //隐藏标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //隐藏状态栏
        if (isFullscreen()) {
            //定义全屏参数
            int flag = WindowManager.LayoutParams.FLAG_FULLSCREEN;
            //设置当前窗体为全屏显示
            window.setFlags(flag, flag);
        }
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
    }

    protected boolean isNoActionBar() {
        return false;
    }

    protected boolean isFullscreen() {
        return false;
    }

    private void bindContentView() {
        Type type = getClass().getGenericSuperclass();
        if (type instanceof ParameterizedType) {
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
            setContentView(binding.getRoot());
        } else {
            setContentView(getLayoutResId());
        }
    }

    protected int getLayoutResId() {
        return 0;
    }

    /**
     * 初始化控件，设置控件事件
     */
    protected abstract void initViews(Bundle savedInstanceState);

    /**
     * 跟随手机系统判断转换中英文
     */
    public void setLanguage() {
        Locale locale;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            locale = LocaleList.getDefault().get(0);
        } else {
            locale = Locale.getDefault();
        }
        String language = locale.getLanguage();
        Resources resources = getResources();
        Configuration config = resources.getConfiguration();
        DisplayMetrics dm = resources.getDisplayMetrics();
        if (language.equals("zh")) {
            config.locale = Locale.SIMPLIFIED_CHINESE;
        } else {
            config.locale = Locale.ENGLISH;
        }
        resources.updateConfiguration(config, dm);
    }

    public void closeWebViewDocumentDraw() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            WebView.enableSlowWholeDocumentDraw();
        }
    }

    protected String getName() {
        return getName();
    }

    /**
     * 跳转
     */
    public void $startActivity(Class<?> cls) {
        Intent intent = new Intent(this, cls);
        startActivity(intent);
    }

    /**
     * 携带数据跳转
     */
    public void $startActivity(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent(this, cls);
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
            Toast.makeText(BaseActivity.this, msg, Toast.LENGTH_SHORT).show();
            Looper.loop();
        }).start();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
