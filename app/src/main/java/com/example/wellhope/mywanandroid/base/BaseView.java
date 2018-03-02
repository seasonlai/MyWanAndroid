package com.example.wellhope.mywanandroid.base;

/**
 * Created by season on 2018/2/25.
 */

public interface BaseView extends BaseContract.View{

    //显示进度中
    void showLoading();

    //显示请求成功
    void showSuccess();

    //失败重试
    void showFailed();

    //显示当前网络不可用
    void showNoNet();

    //重试
    void onRetry();
}
