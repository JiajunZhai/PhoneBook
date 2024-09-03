package com.gewuyou.addressbookmanagementsystem.activity.interfaces;

public interface APPActivity {
    /**
     * 初始化数据
     */
    void initView();

    /**
     * 刷新数据
     *
     * @param isRefresh 是否需要刷新
     */
    void finishViewData(boolean isRefresh);
}
