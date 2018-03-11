package com.example.wellhope.mywanandroid.ui.home;

import android.support.annotation.IntRange;
import android.util.Log;

import com.example.wellhope.mywanandroid.base.BaseObserver;
import com.example.wellhope.mywanandroid.base.BasePresenter;
import com.example.wellhope.mywanandroid.bean.ArticlePageBean;
import com.example.wellhope.mywanandroid.bean.BannerBean;
import com.example.wellhope.mywanandroid.bean.MsgBean;
import com.example.wellhope.mywanandroid.net.RxSchedulers;
import com.example.wellhope.mywanandroid.net.WanAndroidApi;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import retrofit2.Response;

/**
 * Created by season on 2018/3/3.
 */

public class HomePresenter extends BasePresenter<HomeContract.View> implements HomeContract.Presenter {

    private WanAndroidApi wanAndroidApi;

    public static final String TAG = HomePresenter.class.getSimpleName();

    @Inject
    public HomePresenter(WanAndroidApi wanAndroidApi, HomeContract.View view) {
        super(null);
        this.wanAndroidApi = wanAndroidApi;
        this.mView = view;
    }


    @Override
    public void getBanner() {
        if (wanAndroidApi == null)
            return;
        wanAndroidApi.getBannerList()
                .compose(RxSchedulers.<MsgBean<List<BannerBean>>>switchSchedulers())
                .map(new Function<MsgBean<List<BannerBean>>, List<BannerBean>>() {
                    @Override
                    public List<BannerBean> apply(MsgBean<List<BannerBean>> msgBean) throws Exception {
                        return msgBean.getData();
                    }
                })
                .subscribe(new BaseObserver<List<BannerBean>>() {
                    @Override
                    protected void success(List<BannerBean> bannerBeans) {
                        mView.loadBanner(bannerBeans);
                    }

                    @Override
                    protected void fail(Throwable e) {

                    }
                });
    }

    @Override
    public void getPageArticle(@IntRange(from = 0) final int pageNum) {
        if (wanAndroidApi == null)
            return;
        wanAndroidApi.getPageArticle(pageNum)
                .compose(RxSchedulers.<MsgBean<ArticlePageBean>>switchSchedulers())
                .map(new Function<MsgBean<ArticlePageBean>, List<ArticlePageBean.ItemBean>>() {
                    @Override
                    public List<ArticlePageBean.ItemBean> apply(MsgBean<ArticlePageBean> articlePageBeanMsgBean) throws Exception {
                        return articlePageBeanMsgBean.getData().getDatas();
                    }
                })
                .subscribe(new BaseObserver<List<ArticlePageBean.ItemBean>>() {
                    @Override
                    protected void success(List<ArticlePageBean.ItemBean> itemBeans) {
                        if (pageNum == 0)
                            mView.loadArticle(itemBeans);
                        else
                            mView.loadMoreArticle(itemBeans);
                    }

                    @Override
                    protected void fail(Throwable e) {

                    }
                });
    }

    @Override
    public void collectArticle(int id, final int position) {
        wanAndroidApi.collectArticle(id)
                .compose(RxSchedulers.<MsgBean>switchSchedulers())
                .map(new Function<MsgBean, Boolean>() {
                    @Override
                    public Boolean apply(MsgBean msgBean) throws Exception {
                        return msgBean.getErrorCode() >= 0;
                    }
                })
                .subscribe(new BaseObserver<Boolean>() {
                    @Override
                    protected void success(Boolean success) {
                        if (success) {
                            mView.collectArticle(position);
                        }
                    }

                    @Override
                    protected void fail(Throwable e) {

                    }
                });
    }

    @Override
    public void unCollectArticle(int id, final int position) {
        wanAndroidApi.unCollectArticle(id)
                .compose(RxSchedulers.<MsgBean>switchSchedulers())
                .map(new Function<MsgBean, Boolean>() {
                    @Override
                    public Boolean apply(MsgBean msgBean) throws Exception {
                        return msgBean.getErrorCode() >= 0;
                    }
                })
                .subscribe(new BaseObserver<Boolean>() {
                    @Override
                    protected void success(Boolean success) {
                        if (success) {
                            mView.unCollectArticle(position);
                        }
                    }

                    @Override
                    protected void fail(Throwable e) {

                    }
                });
    }
}
