package com.example.wellhope.mywanandroid.base;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by season on 2018/2/25.
 */

public abstract class BasePresenter<V extends BaseView> implements BaseContract.Presenter<V>{

    @Inject
    protected CompositeDisposable mCompositeDisposable;

    protected V mView;

    public void subscribe(){

    }

    public void unSubscribe(){
        if(mCompositeDisposable!=null){
            mCompositeDisposable.clear();
        }
    }

    public void detachView(){
        if(mView!=null){
            mView = null;
        }
    }

}
