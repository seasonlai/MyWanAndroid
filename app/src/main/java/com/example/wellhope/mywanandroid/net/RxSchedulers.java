package com.example.wellhope.mywanandroid.net;

import com.example.wellhope.mywanandroid.bean.MsgBean;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

/**
 * Created by Wellhope on 2018/3/3.
 */

public class RxSchedulers {

//    public static <T> ObservableTransformer<MsgBean<T>, MsgBean<T>> resultTransformer() {
//        return new ObservableTransformer<MsgBean<T>, MsgBean<T>>() {
//            @Override
//            public ObservableSource<MsgBean<T>> apply(Observable<MsgBean<T>> upstream) {
//                return null;
//            }
//        };
//    };

    public static <T> Function<MsgBean<T>,T> mapResult(){
        return new Function<MsgBean<T>, T>() {
            @Override
            public T apply(MsgBean<T> tMsgBean) throws Exception {
                if(tMsgBean.getErrorCode()!=0)
                    throw new RuntimeException(tMsgBean.getErrorMsg());
                return tMsgBean.getData();
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
