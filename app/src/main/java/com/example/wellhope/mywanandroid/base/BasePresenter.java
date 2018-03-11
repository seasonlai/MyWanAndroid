package com.example.wellhope.mywanandroid.base;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by season on 2018/2/25.
 */

public abstract class BasePresenter<V extends BaseView> implements BaseContract.Presenter<V> {

    private CompositeDisposable mCompositeDisposable;

    protected V mView;

    protected BasePresenter(CompositeDisposable compositeDisposable) {
        mCompositeDisposable = compositeDisposable == null ?
                new CompositeDisposable() : compositeDisposable;
    }

    public void subscribe() {

    }

    public void unSubscribe() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.clear();
        }
    }

    public void addDisposable(Disposable disposable) {
        mCompositeDisposable.add(disposable);
    }

    public void detachView() {
        if (mView != null) {
            mView = null;
        }
    }
}
