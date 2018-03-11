package com.example.wellhope.mywanandroid.ui.article;

import com.example.wellhope.mywanandroid.base.BasePresenter;
import com.example.wellhope.mywanandroid.bean.ArticlePageBean;
import com.example.wellhope.mywanandroid.bean.BannerBean;
import com.example.wellhope.mywanandroid.bean.RecommendBean;

import javax.inject.Inject;

/**
 * Created by Wellhope on 2018/3/5.
 */

public class ArticlePresenter extends BasePresenter<ArticleContract.View> implements ArticleContract.Presenter {

    @Inject
    public ArticlePresenter(ArticleContract.View view) {
        super(null);
        this.mView = view;
    }

    @Override
    public void loadData(Object o) {
        if(o==null)
            return;

        if(o instanceof BannerBean){
            BannerBean bean = (BannerBean) o;
            this.mView.loadArticle(new Article(bean.getUrl(),bean.getTitle()));
        }
        else if(o instanceof ArticlePageBean.ItemBean){
            ArticlePageBean.ItemBean bean = (ArticlePageBean.ItemBean) o;
            this.mView.loadArticle(new Article(bean.getLink(),bean.getTitle()));
        }else if(o instanceof RecommendBean.StarWebBean){
            RecommendBean.StarWebBean bean = (RecommendBean.StarWebBean) o;
            this.mView.loadArticle(new Article(bean.getLink(),bean.getName()));
        }
    }

    public static class Article{
        public String url;
        public String title;

        public Article(String url, String title) {
            this.url = url;
            this.title = title;
        }
    }
}
