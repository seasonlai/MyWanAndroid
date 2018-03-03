package com.example.wellhope.mywanandroid.di;

import com.example.wellhope.mywanandroid.constant.Constant;
import com.example.wellhope.mywanandroid.net.WanAndroidApi;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Wellhope on 2018/3/3.
 */
@Module
public class HttpModule {

    @Provides
    public WanAndroidApi providerNetApis() {
        return new Retrofit.Builder()
                .baseUrl(Constant.API_WANANDROID)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(WanAndroidApi.class);
    }

}
