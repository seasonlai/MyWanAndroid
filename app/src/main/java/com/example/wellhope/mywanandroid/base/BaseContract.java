package com.example.wellhope.mywanandroid.base;

/**
 * Created by Wellhope on 2018/2/28.
 */

public class BaseContract {

    interface View{

    }

    interface Presenter<V extends View>{

        void unSubscribe();

        void subscribe();

        void detachView();
    }
}
