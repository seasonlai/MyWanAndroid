package com.example.wellhope.mywanandroid.di;


import com.example.wellhope.mywanandroid.MyApp;

import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

/**
 * Created by Wellhope on 2018/2/9.
 */

@Component(modules = {
        AndroidSupportInjectionModule.class,
        ActivityOrFragmentModule.class,
        BaseModule.class
})
public interface AppComponent extends AndroidInjector<MyApp> {


    @Component.Builder
    abstract class Bulider extends Builder<MyApp> {
    }



}
