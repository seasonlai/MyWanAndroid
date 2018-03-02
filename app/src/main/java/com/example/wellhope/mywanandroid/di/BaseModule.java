package com.example.wellhope.mywanandroid.di;

import com.example.wellhope.mywanandroid.base.BasePresenter;

import javax.annotation.Nullable;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by season on 2018/2/25.
 */

@Module
public class BaseModule {

    @Provides
    @Nullable
    BasePresenter providerBasePresenter(){return null;}

    @Provides
    CompositeDisposable providerCompositeDisposable() {
        return new CompositeDisposable();
    }
}
