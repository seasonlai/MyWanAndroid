package com.example.wellhope.mywanandroid.ui.search.result;

import com.blankj.utilcode.util.ToastUtils;
import com.example.wellhope.mywanandroid.base.BaseObserver;
import com.example.wellhope.mywanandroid.base.BasePresenter;
import com.example.wellhope.mywanandroid.bean.ArticlePageBean;
import com.example.wellhope.mywanandroid.bean.MsgBean;
import com.example.wellhope.mywanandroid.net.RxSchedulers;
import com.example.wellhope.mywanandroid.net.WanAndroidApi;

import javax.inject.Inject;

import io.reactivex.functions.Function;

/**
 * Created by Wellhope on 2018/3/9.
 */

public class SearchResultPresenter extends BasePresenter<SearchResultContract.View> implements SearchResultContract.Presenter {

    WanAndroidApi mApi;

    @Inject
    public SearchResultPresenter(WanAndroidApi api,SearchResultContract.View view) {
        mApi = api;
        this.mView = view;
    }

    @Override
    public void search(String key, int pageNum) {
        mApi.search(pageNum,key)
                .compose(RxSchedulers.<MsgBean<ArticlePageBean>>switchSchedulers())
                .map(RxSchedulers.<ArticlePageBean>mapResult())
                .subscribe(new BaseObserver<ArticlePageBean>() {
                    @Override
                    protected void success(ArticlePageBean articlePageBean) {
                        mView.loadResult(articlePageBean);
                    }

                    @Override
                    protected void fail(Throwable e) {
                        ToastUtils.showShort(e.getMessage());
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
                        ToastUtils.showShort(e.getMessage());
                    }
                });
    }
}
