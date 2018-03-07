package com.example.wellhope.mywanandroid.ui.system;

import com.example.wellhope.mywanandroid.base.BaseObserver;
import com.example.wellhope.mywanandroid.base.BasePresenter;
import com.example.wellhope.mywanandroid.bean.MsgBean;
import com.example.wellhope.mywanandroid.bean.SystemBean;
import com.example.wellhope.mywanandroid.net.RxSchedulers;
import com.example.wellhope.mywanandroid.net.WanAndroidApi;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.functions.Function;

/**
 * Created by Wellhope on 2018/3/7.
 */

public class SystemPresenter extends BasePresenter<SystemContract.View> implements SystemContract.Presenter {

    WanAndroidApi mApi;

    @Inject
    public SystemPresenter(WanAndroidApi api,SystemContract.View view) {
        mApi = api;
        mView = view;
    }

    @Override
    public void getSystemKind() {
        mApi.getSystem()
                .compose(RxSchedulers.<MsgBean<List<SystemBean>>>switchSchedulers())
                .map(new Function<MsgBean<List<SystemBean>>, List<SystemBean>>() {
                    @Override
                    public List<SystemBean> apply(MsgBean<List<SystemBean>> listMsgBean) throws Exception {
                        return listMsgBean.getData();
                    }
                })
                .subscribe(new BaseObserver<List<SystemBean>>() {
                    @Override
                    protected void success(List<SystemBean> systemBeans) {
                        mView.loadFirstKind(systemBeans);
                    }

                    @Override
                    protected void fail(Throwable e) {

                    }
                });
    }

}
