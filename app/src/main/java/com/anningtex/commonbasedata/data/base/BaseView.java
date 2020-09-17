package com.anningtex.commonbasedata.data.base;

/**
 * @Author Song
 */
public interface BaseView {
    /**
     * 显示正在加载view
     */
    void showLoading();

    /**
     * 显示正在加载view
     */
    void showLoading(String msg);

    /**
     * 关闭正在加载view
     */
    void hideLoading();

    /**
     * 显示提示
     */
    void showToast(String msg);
}
