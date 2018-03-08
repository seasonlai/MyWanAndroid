package com.example.wellhope.mywanandroid.di;

import com.example.wellhope.mywanandroid.MainActivity;
import com.example.wellhope.mywanandroid.ui.home.ArticleActivity;
import com.example.wellhope.mywanandroid.ui.home.HomeFragment;
import com.example.wellhope.mywanandroid.ui.login.LoginActivity;
import com.example.wellhope.mywanandroid.ui.personal.PersonalFragment;
import com.example.wellhope.mywanandroid.ui.search.SearchActivity;
import com.example.wellhope.mywanandroid.ui.system.ArticlesFragment;
import com.example.wellhope.mywanandroid.ui.system.SystemFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityOrFragmentModule {

    @ContributesAndroidInjector()
    abstract MainActivity mainActivity();

    @ContributesAndroidInjector(modules = ViewModule.class)
    abstract HomeFragment homeFragment();

    @ContributesAndroidInjector(modules = ViewModule.class)
    abstract ArticleActivity articleActivity();

    @ContributesAndroidInjector(modules = ViewModule.class)
    abstract SearchActivity searchActivity();

    @ContributesAndroidInjector(modules = ViewModule.class)
    abstract SystemFragment systemFragment();

    @ContributesAndroidInjector(modules = ViewModule.class)
    abstract ArticlesFragment articlesFragment();

    @ContributesAndroidInjector(modules = ViewModule.class)
    abstract LoginActivity loginActivity();

    @ContributesAndroidInjector()
    abstract PersonalFragment personalFragment();

}
