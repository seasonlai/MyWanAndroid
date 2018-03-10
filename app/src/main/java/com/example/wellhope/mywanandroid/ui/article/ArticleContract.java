package com.example.wellhope.mywanandroid.ui.article;

import com.example.wellhope.mywanandroid.base.BaseView;

/**
 * Created by Wellhope on 2018/3/5.
 */

public class ArticleContract {

    public interface View extends BaseView{
        void loadArticle(ArticlePresenter.Article article);
    }

    interface Presenter {
        void loadData(Object o);
    }

}
