package com.example.wellhope.mywanandroid.ui.home;

import com.example.wellhope.mywanandroid.base.BasePresenter;

import javax.inject.Inject;

/**
 * Created by season on 2018/3/3.
 */

public class HomePresenter extends BasePresenter<HomeContract.View> implements HomeContract.Presenter{

    @Inject
    public HomePresenter(HomeContract.View view) {
        this.mView = view;
    }


    @Override
    public void getData() {

    }
}
