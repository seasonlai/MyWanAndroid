package com.example.wellhope.mywanandroid.di;

import com.example.wellhope.mywanandroid.ui.home.ArticleActivity;
import com.example.wellhope.mywanandroid.ui.home.ArticleContract;
import com.example.wellhope.mywanandroid.ui.home.HomeContract;
import com.example.wellhope.mywanandroid.ui.home.HomeFragment;
import com.example.wellhope.mywanandroid.ui.login.LoginActivity;
import com.example.wellhope.mywanandroid.ui.login.LoginContract;
import com.example.wellhope.mywanandroid.ui.search.SearchActivity;
import com.example.wellhope.mywanandroid.ui.search.SearchContract;
import com.example.wellhope.mywanandroid.ui.system.SystemContract;
import com.example.wellhope.mywanandroid.ui.system.SystemFragment;

import dagger.Binds;
import dagger.Module;

/**
 * Created by Wellhope on 2018/3/3.
 */
@Module
public abstract class ViewModule {

    @Binds
    abstract HomeContract.View homeView(HomeFragment fragment);
    @Binds
    abstract ArticleContract.View articleView(ArticleActivity activity);
    @Binds
    abstract SearchContract.View searchView(SearchActivity activity);
    @Binds
    abstract SystemContract.View systemView(SystemFragment fragment);
    @Binds
    abstract LoginContract.View loginView(LoginActivity activity);
}
