package com.example.wellhope.mywanandroid;

import android.app.Application;
import android.support.v4.content.ContextCompat;


import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.blankj.utilcode.util.Utils;
import com.example.wellhope.mywanandroid.constant.Constant;
import com.example.wellhope.mywanandroid.di.DaggerAppComponent;

import org.litepal.LitePal;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;

/**
 * Created by Wellhope on 2018/2/9.
 */

public class MyApp extends DaggerApplication {

    private static Application mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        LitePal.initialize(this);
        Utils.init(this);
        mContext = this;

        ToastUtils.setBgColor(ContextCompat.getColor(this,R.color.toast_bg));
        ToastUtils.setMsgColor(ContextCompat.getColor(this,R.color.toast_txt));
    }

    public static Application getContext() {
        return mContext;
    }

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerAppComponent.builder().create(this);
    }

    public static boolean isLogin(){
        return SPUtils.getInstance(Constant.SHARED_NAME).getBoolean(Constant.LOGIN_KEY);
    }
}
