package com.example.wellhope.mywanandroid.ui.login;

import android.text.TextUtils;

import com.example.wellhope.mywanandroid.base.BaseObserver;
import com.example.wellhope.mywanandroid.base.BasePresenter;
import com.example.wellhope.mywanandroid.bean.MsgBean;
import com.example.wellhope.mywanandroid.net.RxSchedulers;
import com.example.wellhope.mywanandroid.net.WanAndroidApi;

import javax.inject.Inject;

import io.reactivex.functions.Function;

/**
 * Created by Wellhope on 2018/3/8.
 */

public class LoginPresenter extends BasePresenter<LoginContract.View> implements LoginContract.Presenter {

    WanAndroidApi mApi;

    @Inject
    public LoginPresenter(WanAndroidApi api,LoginContract.View view) {
        mApi = api;
        mView = view;
    }

    @Override
    public void login(String username, String password) {
        if(TextUtils.isEmpty(username)){
            mView.login(false,"用户名不能为空");
            return;
        }
        if(TextUtils.isEmpty(password)){
            mView.login(false,"密码不能为空");
            return;
        }
        mApi.login(username,password)
                .compose(RxSchedulers.<MsgBean>switchSchedulers())
                .subscribe(new BaseObserver<MsgBean>() {
                    @Override
                    protected void success(MsgBean msgBean) {
                        if(msgBean.getErrorCode()==0){
                            mView.login(true);
                        }else {
                            mView.login(false,msgBean.getErrorMsg());
                        }
                    }

                    @Override
                    protected void fail(Throwable e) {
                        mView.login(false,e.getMessage());
                    }
                });
    }

    @Override
    public void register(String username, String password, String rePassword) {
        if(TextUtils.isEmpty(username)){
            mView.register(false,"用户名不能为空");
            return;
        }
        if(TextUtils.isEmpty(password)){
            mView.register(false,"密码不能为空");
            return;
        }
        if(!TextUtils.equals(password,rePassword)){
            mView.register(false,"密码不一致");
            return;
        }
        mApi.register(username,password,rePassword)
                .compose(RxSchedulers.<MsgBean>switchSchedulers())
                .subscribe(new BaseObserver<MsgBean>() {
                    @Override
                    protected void success(MsgBean msgBean) {
                        if(msgBean.getErrorCode()==0){
                            mView.register(true);
                        }else {
                            mView.register(false,msgBean.getErrorMsg());
                        }
                    }

                    @Override
                    protected void fail(Throwable e) {
                        mView.register(false,e.getMessage());
                    }
                });
    }
}
