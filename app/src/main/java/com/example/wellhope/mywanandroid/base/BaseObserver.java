package com.example.wellhope.mywanandroid.base;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by Wellhope on 2018/3/7.
 */

public abstract class BaseObserver<T> implements Observer<T> {

    protected abstract void success(T t);

    protected abstract void fail(Throwable e);

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(T t) {
        success(t);
    }

    @Override
    public void onError(Throwable e) {
        fail(e);
    }

    @Override
    public void onComplete() {

    }
}
