package com.example.wellhope.mywanandroid.ui.system.articles;

import com.example.wellhope.mywanandroid.base.BaseView;
import com.example.wellhope.mywanandroid.bean.ArticlePageBean;

import java.util.List;

/**
 * Created by Wellhope on 2018/3/10.
 */

public class ArticleListContract {

    public interface View extends BaseView{

        void loadData(ArticlePageBean articlePage);

        void loadMoreData(ArticlePageBean articlePage);

        void collectArticle(int position);

        void unCollectArticle(int position);
    }


    interface Presenter{

        void getArticles(int cid,int pageNum);

        void collectArticle(int id,final int position);
        void unCollectArticle(int id,final int position);
    }
}
