package com.example.wellhope.mywanandroid.ui.search.result;

import com.example.wellhope.mywanandroid.base.BaseView;
import com.example.wellhope.mywanandroid.bean.ArticlePageBean;

/**
 * Created by Wellhope on 2018/3/9.
 */

public class SearchResultContract {

    public interface View extends BaseView{
        void loadResult(ArticlePageBean articlePage);
        void loadMoreResult(ArticlePageBean articlePage);
        void unCollectArticle(int position);
        void collectArticle(int position);
    }

    interface Presenter {
        void search(String key,int pageNum);
        void unCollectArticle(int id, int position);
        void collectArticle(int id, int position);
    }

}
