package com.example.wellhope.mywanandroid.di;

import com.example.wellhope.mywanandroid.MainActivity;
import com.example.wellhope.mywanandroid.ui.article.ArticleActivity;
import com.example.wellhope.mywanandroid.ui.home.HomeFragment;
import com.example.wellhope.mywanandroid.ui.login.LoginActivity;
import com.example.wellhope.mywanandroid.ui.personal.MyFavorActivity;
import com.example.wellhope.mywanandroid.ui.personal.PersonalFragment;
import com.example.wellhope.mywanandroid.ui.search.SearchActivity;
import com.example.wellhope.mywanandroid.ui.search.recommend.SearchRecommendFragment;
import com.example.wellhope.mywanandroid.ui.search.result.SearchResultFragment;
import com.example.wellhope.mywanandroid.ui.system.SystemFragment;
import com.example.wellhope.mywanandroid.ui.system.articles.ArticleListFragment;

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
    abstract ArticleListFragment articlesFragment();

    @ContributesAndroidInjector(modules = ViewModule.class)
    abstract LoginActivity loginActivity();

    @ContributesAndroidInjector()
    abstract PersonalFragment personalFragment();

    @ContributesAndroidInjector(modules = ViewModule.class)
    abstract SearchRecommendFragment recommendFragment();

    @ContributesAndroidInjector(modules = ViewModule.class)
    abstract SearchResultFragment resultFragment();

    @ContributesAndroidInjector(modules = ViewModule.class)
    abstract MyFavorActivity myFavorActivity();

}
