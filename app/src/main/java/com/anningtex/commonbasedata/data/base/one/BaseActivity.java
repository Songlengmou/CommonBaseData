package com.anningtex.commonbasedata.data.base.one;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.os.LocaleList;
import android.util.DisplayMetrics;
import android.webkit.WebView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.anningtex.commonbasedata.data.base.BaseView;
import com.anningtex.commonbasedata.data.manger.AppManager;
import com.anningtex.commonbasedata.data.rx.RxDisposeManager;
import com.anningtex.commonbasedata.dialog.LoadingView;

import java.util.Locale;

import butterknife.ButterKnife;
import io.reactivex.disposables.Disposable;

/**
 * @author Song
 */
@SuppressLint("Registered")
public abstract class BaseActivity extends AppCompatActivity implements BaseView {
    public Context mContext;
    protected LoadingView mLoadingView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLoadingView = new LoadingView(this);
        setLanguage();
        closeWebViewDocumentDraw();
        setContentView(getLayoutResource());
        mContext = this;
        ButterKnife.bind(this);
        initViews(savedInstanceState);
        AppManager.getAppManager().addActivity(this);
    }

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

    /**
     * 加载layout布局文件
     */
    protected abstract int getLayoutResource();

    /**
     * 初始化控件，设置控件事件
     */
    protected abstract void initViews(Bundle savedInstanceState);

    public void addDispose(Disposable disposable) {
        RxDisposeManager.get().add(getClass().getName(), disposable);
    }

    @Override
    protected void onDestroy() {
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
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
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
    public void onBackPressed() {
        super.onBackPressed();
        AppManager.getAppManager().finishCurrentActivity();
    }
}
