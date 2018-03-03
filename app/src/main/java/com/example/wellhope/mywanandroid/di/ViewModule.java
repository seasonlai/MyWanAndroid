package com.example.wellhope.mywanandroid.di;

import com.example.wellhope.mywanandroid.ui.home.HomeContract;
import com.example.wellhope.mywanandroid.ui.home.HomeFragment;

import dagger.Binds;
import dagger.Module;

/**
 * Created by Wellhope on 2018/3/3.
 */
@Module
public abstract class ViewModule {

    @Binds
    abstract HomeContract.View homeView(HomeFragment fragment);
}
