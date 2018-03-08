package com.example.wellhope.mywanandroid.ui.home;

import com.example.wellhope.mywanandroid.base.BaseView;
import com.example.wellhope.mywanandroid.bean.ArticlePageBean;
import com.example.wellhope.mywanandroid.bean.BannerBean;
import com.youth.banner.Banner;

import java.util.List;

/**
 * Created by season on 2018/3/3.
 */

public class HomeContract {

    public interface View extends BaseView{
        void loadBanner(List<BannerBean> bannerList);
        void loadArticle(List<ArticlePageBean.ItemBean> articleList);
        void collectArticle(int position);
        void unCollectArticle(int position);
    }

    interface Presenter{
        void getBanner();
        void getPageArticle(int pageNum);
//        void loadMoreArtic
        void collectArticle(int id,final int position);
        void unCollectArticle(int id,final int position);
    }

}
