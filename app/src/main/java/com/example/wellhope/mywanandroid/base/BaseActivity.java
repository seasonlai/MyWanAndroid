package com.example.wellhope.mywanandroid.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Wellhope on 2018/3/2.
 */

public abstract class BaseActivity<T extends BasePresenter> extends SupportActivity implements BaseView{

    @Inject
    @Nullable
    protected T mPresenter;

    Unbinder unbinder;

    protected abstract int getContentLayout();

    protected abstract void bindView(@Nullable Bundle savedInstanceState);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentLayout());
        unbinder = ButterKnife.bind(this);
        bindView(savedInstanceState);
        loadData();
    }

    protected void loadData(){

    }

    @Override
    protected void onDestroy() {
        unbinder.unbind();
        super.onDestroy();
    }


    @Override
    public void showLoading() {

    }

    @Override
    public void showSuccess() {

    }

    @Override
    public void showFailed() {

    }

    @Override
    public void showNoNet() {

    }

    @Override
    public void onRetry() {

    }

}
