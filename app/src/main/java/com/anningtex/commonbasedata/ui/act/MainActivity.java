package com.anningtex.commonbasedata.ui.act;

import android.app.Application;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.anningtex.commonbasedata.data.manger.MainApplication;
import com.anningtex.commonbasedata.R;
import com.anningtex.commonbasedata.data.base.BaseActivity;
import com.anningtex.commonbasedata.data.manger.AppManager;
import com.anningtex.commonbasedata.ui.fragment.OneFragment;
import com.anningtex.commonbasedata.ui.fragment.ThreeFragment;
import com.anningtex.commonbasedata.ui.fragment.TwoFragment;
import com.anningtex.commonbasedata.weight.pager.CustomViewPager;
import com.anningtex.commonbasedata.weight.pager.TabPagerAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheEntity;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.cookie.CookieJarImpl;
import com.lzy.okgo.cookie.store.MemoryCookieStore;
import com.lzy.okgo.https.HttpsUtils;
import com.lzy.okgo.interceptor.HttpLoggingInterceptor;
import com.lzy.okgo.model.HttpHeaders;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import butterknife.BindView;
import okhttp3.OkHttpClient;

import static com.google.android.material.bottomnavigation.LabelVisibilityMode.LABEL_VISIBILITY_LABELED;

/**
 * @author Song
 */
public class MainActivity extends BaseActivity {
    @BindView(R.id.main_bottom_nav)
    BottomNavigationView mMainBottomNav;
    @BindView(R.id.main_fragment_container)
    CustomViewPager mainFragmentContainer;

    private List<Fragment> fragmentList = new ArrayList<>();
    private List<String> mTitleList = new ArrayList<>();
    private long time = 0;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_main;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        initOkGo();

        // BottomNavigationView 3个以上图标不显示文字解决方法
        mMainBottomNav.setLabelVisibilityMode(LABEL_VISIBILITY_LABELED);
        fragmentList.add(new OneFragment());
        fragmentList.add(new TwoFragment());
        fragmentList.add(new ThreeFragment());
        setupBottomNavigationView();
        TabPagerAdapter pagerAdapter = new TabPagerAdapter(getSupportFragmentManager(), mTitleList, fragmentList);
        mainFragmentContainer.setAdapter(pagerAdapter);
        //设置默认选中页
        mainFragmentContainer.setCurrentItem(0);
        mainFragmentContainer.setOffscreenPageLimit(3);
    }

    private void initOkGo() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        //log相关
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor("OkGo");
        //log打印级别，决定了log显示的详细程度
        loggingInterceptor.setPrintLevel(HttpLoggingInterceptor.Level.BODY);
        //log颜色级别，决定了log在控制台显示的颜色
        loggingInterceptor.setColorLevel(Level.INFO);
        //添加OkGo默认debug日志
        builder.addInterceptor(loggingInterceptor);

        //超时时间设置，默认60秒
        //全局的读取超时时间
        builder.readTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);
        //全局的写入超时时间
        builder.writeTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);
        //全局的连接超时时间
        builder.connectTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);

        builder.cookieJar(new CookieJarImpl(new MemoryCookieStore()));

        HttpsUtils.SSLParams sslParams1 = HttpsUtils.getSslSocketFactory();
        builder.sslSocketFactory(sslParams1.sSLSocketFactory, sslParams1.trustManager);

        HttpHeaders headers = new HttpHeaders();
        headers.put("Cookie", MainApplication.COOKIE);
        //必须调用初始化
        OkGo.getInstance().init((Application) MainApplication.getContext())
                //建议设置OkHttpClient，不设置会使用默认的
                .setOkHttpClient(builder.build())
                //全局统一缓存模式，默认不使用缓存，可以不传
                .setCacheMode(CacheMode.NO_CACHE)
                //全局统一缓存时间，默认永不过期，可以不传
                .setCacheTime(CacheEntity.CACHE_NEVER_EXPIRE)
                //全局统一超时重连次数，默认为三次，那么最差的情况会请求4次(一次原始请求，三次重连请求)，不需要可以设置为0
                .setRetryCount(3)
                .addCommonHeaders(headers);
    }

    private void setupBottomNavigationView() {
        mMainBottomNav.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.one:
                    mainFragmentContainer.setCurrentItem(0);
                    break;
                case R.id.two:
                    mainFragmentContainer.setCurrentItem(1);
                    break;
                case R.id.three:
                    mainFragmentContainer.setCurrentItem(2);
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + item.getItemId());
            }
            return true;
        });
    }


    @Override
    public void onBackPressed() {
        long current = System.currentTimeMillis();
        if (current - time > 2000) {
            time = current;
            showToast("再按一次退出");
        } else {
            super.onBackPressed();
            AppManager.getAppManager().AppExit(this);
        }
    }
}