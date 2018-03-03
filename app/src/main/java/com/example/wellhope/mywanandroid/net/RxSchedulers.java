package com.example.wellhope.mywanandroid.net;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Wellhope on 2018/3/3.
 */

public class RxSchedulers {

    static final ObservableTransformer transformer = new ObservableTransformer() {
        @Override
        public ObservableSource apply(Observable upstream) {
            return upstream.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        }

    };

    public static <T> ObservableTransformer<T,T> applySchedulers(){
        return (ObservableTransformer<T,T>)transformer;
    }
}
