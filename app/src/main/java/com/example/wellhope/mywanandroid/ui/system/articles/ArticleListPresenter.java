package com.example.wellhope.mywanandroid.ui.system.articles;

import com.example.wellhope.mywanandroid.base.BaseObserver;
import com.example.wellhope.mywanandroid.base.BasePresenter;
import com.example.wellhope.mywanandroid.bean.ArticlePageBean;
import com.example.wellhope.mywanandroid.bean.MsgBean;
import com.example.wellhope.mywanandroid.bean.SystemBean;
import com.example.wellhope.mywanandroid.net.RxSchedulers;
import com.example.wellhope.mywanandroid.net.WanAndroidApi;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.functions.Function;

/**
 * Created by Wellhope on 2018/3/10.
 */

public class ArticleListPresenter extends BasePresenter<ArticleListContract.View> implements ArticleListContract.Presenter {

    WanAndroidApi mApi;

    @Inject
    public ArticleListPresenter(WanAndroidApi api, ArticleListContract.View view) {
        super(null);
        mApi = api;
        mView = view;
    }

    @Override
    public void getArticles(int cid, final int pageNum) {
        mApi.getSystemArticles(pageNum,cid)
                .compose(RxSchedulers.<MsgBean<ArticlePageBean>>switchSchedulers())
                .map(RxSchedulers.<ArticlePageBean>mapResult())
                .subscribe(new BaseObserver<ArticlePageBean>() {
                    @Override
                    protected void success(ArticlePageBean articlePageBean) {
                        if (mView == null) {
                            return;
                        }
                        if (pageNum == 0) {
                            mView.loadData(articlePageBean);
                        } else {
                            mView.loadMoreData(articlePageBean);
                        }
                    }
                    @Override
                    protected void fail(Throwable e) {

                    }
                });
    }


    @Override
    public void collectArticle(int id, final int position) {
        mApi.collectArticle(id)
                .compose(RxSchedulers.<MsgBean>switchSchedulers())
                .map(new Function<MsgBean, Boolean>() {
                    @Override
                    public Boolean apply(MsgBean msgBean) throws Exception {
                        return msgBean.getErrorCode()>=0;
                    }
                })
                .subscribe(new BaseObserver<Boolean>() {
                    @Override
                    protected void success(Boolean success) {
                        if(success){
                            mView.collectArticle(position);
                        }
                    }
                    @Override
                    protected void fail(Throwable e) {

                    }
                });
    }

    @Override
    public void unCollectArticle(int id,final int position) {
        mApi.unCollectArticle(id)
                .compose(RxSchedulers.<MsgBean>switchSchedulers())
                .map(new Function<MsgBean, Boolean>() {
                    @Override
                    public Boolean apply(MsgBean msgBean) throws Exception {
                        return msgBean.getErrorCode()>=0;
                    }
                })
                .subscribe(new BaseObserver<Boolean>() {
                    @Override
                    protected void success(Boolean success) {
                        if(success){
                            mView.unCollectArticle(position);
                        }
                    }
                    @Override
                    protected void fail(Throwable e) {

                    }
                });
    }
}
