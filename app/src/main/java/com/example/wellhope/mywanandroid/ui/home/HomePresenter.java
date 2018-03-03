package com.example.wellhope.mywanandroid.ui.home;

import android.support.annotation.IntRange;

import com.example.wellhope.mywanandroid.base.BasePresenter;
import com.example.wellhope.mywanandroid.bean.ArticlePageBean;
import com.example.wellhope.mywanandroid.bean.BannerBean;
import com.example.wellhope.mywanandroid.bean.MsgBean;
import com.example.wellhope.mywanandroid.net.RxSchedulers;
import com.example.wellhope.mywanandroid.net.WanAndroidApi;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * Created by season on 2018/3/3.
 */

public class HomePresenter extends BasePresenter<HomeContract.View> implements HomeContract.Presenter{

    private WanAndroidApi wanAndroidApi;

    @Inject
    public HomePresenter(WanAndroidApi wanAndroidApi,HomeContract.View view) {
        this.wanAndroidApi = wanAndroidApi;
        this.mView = view;
    }


    @Override
    public void getBanner() {
        if(wanAndroidApi==null)
            return;
        wanAndroidApi.getBannerList()
                .compose(RxSchedulers.<MsgBean<List<BannerBean>>>applySchedulers())
                .map(new Function<MsgBean<List<BannerBean>>, List<BannerBean>>() {
                    @Override
                    public List<BannerBean> apply(MsgBean<List<BannerBean>> msgBean) throws Exception {
                        return msgBean.getData();
                    }
                })
                .subscribe(new Consumer<List<BannerBean>>() {
                    @Override
                    public void accept(List<BannerBean> bannerBeans) throws Exception {
                        mView.loadBanner(bannerBeans);
                    }
                });
    }

    @Override
    public void getPageArticle(@IntRange(from = 0) int pageNum) {
        if(wanAndroidApi==null)
            return;
        wanAndroidApi.getPageArticle(pageNum)
                .compose(RxSchedulers.<MsgBean<ArticlePageBean>>applySchedulers())
                .map(new Function<MsgBean<ArticlePageBean>, List<ArticlePageBean.ItemBean>>() {
                    @Override
                    public List<ArticlePageBean.ItemBean> apply(MsgBean<ArticlePageBean> articlePageBeanMsgBean) throws Exception {
                        return articlePageBeanMsgBean.getData().getDatas();
                    }
                })
                .subscribe(new Consumer<List<ArticlePageBean.ItemBean>>() {
                    @Override
                    public void accept(List<ArticlePageBean.ItemBean> itemBeans) throws Exception {
                        mView.loadArticle(itemBeans);
                    }
                });
    }
}
