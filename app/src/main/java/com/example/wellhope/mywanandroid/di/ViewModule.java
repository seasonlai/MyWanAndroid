package com.example.wellhope.mywanandroid.di;

import com.example.wellhope.mywanandroid.ui.article.ArticleActivity;
import com.example.wellhope.mywanandroid.ui.article.ArticleContract;
import com.example.wellhope.mywanandroid.ui.home.HomeContract;
import com.example.wellhope.mywanandroid.ui.home.HomeFragment;
import com.example.wellhope.mywanandroid.ui.login.LoginActivity;
import com.example.wellhope.mywanandroid.ui.login.LoginContract;
import com.example.wellhope.mywanandroid.ui.personal.MyFavorActivity;
import com.example.wellhope.mywanandroid.ui.personal.MyFavorContract;
import com.example.wellhope.mywanandroid.ui.search.recommend.SearchRecommendContract;
import com.example.wellhope.mywanandroid.ui.search.recommend.SearchRecommendFragment;
import com.example.wellhope.mywanandroid.ui.search.result.SearchResultContract;
import com.example.wellhope.mywanandroid.ui.search.result.SearchResultFragment;
import com.example.wellhope.mywanandroid.ui.system.SystemContract;
import com.example.wellhope.mywanandroid.ui.system.SystemFragment;
import com.example.wellhope.mywanandroid.ui.system.articles.ArticleListContract;
import com.example.wellhope.mywanandroid.ui.system.articles.ArticleListFragment;

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
    abstract SystemContract.View systemView(SystemFragment fragment);

    @Binds
    abstract LoginContract.View loginView(LoginActivity activity);

    @Binds
    abstract SearchRecommendContract.View recommendView(SearchRecommendFragment fragment);

    @Binds
    abstract SearchResultContract.View resultView(SearchResultFragment fragment);

    @Binds
    abstract ArticleListContract.View articlesView(ArticleListFragment fragment);

    @Binds
    abstract MyFavorContract.View favorView(MyFavorActivity activity);
}
