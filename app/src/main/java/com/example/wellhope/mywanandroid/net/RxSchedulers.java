package com.example.wellhope.mywanandroid.net;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

/**
 * Created by Wellhope on 2018/3/3.
 */

public class RxSchedulers {

    public static <T> ObservableTransformer<Response<T>, T> sTransformer() {
        return new ObservableTransformer<Response<T>, T>() {
            @Override
            public ObservableSource<T> apply(Observable<Response<T>> upstream) {
                return upstream.map(new Function<Response<T>, T>() {
                    @Override
                    public T apply(Response<T> tResponse) throws Exception {
                        if(!tResponse.isSuccessful())
                            throw new RuntimeException(tResponse.message());
                        return tResponse.body();
                    }
                });
            }
        };
    }

    static final ObservableTransformer switchTransformer = new ObservableTransformer() {
        @Override
        public ObservableSource apply(Observable upstream) {
            return upstream.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        }

    };

    public static <T> ObservableTransformer<T,T> switchSchedulers(){
        return (ObservableTransformer<T,T>) switchTransformer;
    }


}
