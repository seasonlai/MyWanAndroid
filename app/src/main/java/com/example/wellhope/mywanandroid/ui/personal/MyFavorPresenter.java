package com.example.wellhope.mywanandroid.ui.personal;

import com.blankj.utilcode.util.ToastUtils;
import com.example.wellhope.mywanandroid.base.BaseObserver;
import com.example.wellhope.mywanandroid.base.BasePresenter;
import com.example.wellhope.mywanandroid.bean.ArticlePageBean;
import com.example.wellhope.mywanandroid.bean.MsgBean;
import com.example.wellhope.mywanandroid.net.RxSchedulers;
import com.example.wellhope.mywanandroid.net.WanAndroidApi;
import com.example.wellhope.mywanandroid.ui.search.result.SearchResultContract;

import javax.inject.Inject;

import io.reactivex.functions.Function;

/**
 * Created by Wellhope on 2018/3/9.
 */

public class MyFavorPresenter extends BasePresenter<MyFavorContract.View> implements MyFavorContract.Presenter {

    WanAndroidApi mApi;


    @Inject
    public MyFavorPresenter(WanAndroidApi api, MyFavorContract.View view) {
        super(null);
        mApi = api;
        this.mView = view;
    }

    @Override
    public void getFavor(final int pageNum) {
        if (mView == null)
            return;
        mApi.getFavor(pageNum)
                .compose(RxSchedulers.<MsgBean<ArticlePageBean>>switchSchedulers())
                .map(RxSchedulers.<ArticlePageBean>mapResult())
                .subscribe(new BaseObserver<ArticlePageBean>() {
                    @Override
                    protected void success(ArticlePageBean articlePageBean) {
                        if (pageNum == 0) {
                            mView.loadResult(articlePageBean);

                        } else
                            mView.loadMoreResult(articlePageBean);
                    }

                    @Override
                    protected void fail(Throwable e) {

                    }
                });
    }

    @Override
    public void unCollectArticle(int id, final int position) {
        mApi.unCollectArticle(id)
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
                        ToastUtils.showShort(e.getMessage());
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
                        ToastUtils.showShort(e.getMessage());
                    }
                });
    }
}
