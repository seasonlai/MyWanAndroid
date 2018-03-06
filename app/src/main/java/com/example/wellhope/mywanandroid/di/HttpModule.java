package com.example.wellhope.mywanandroid.di;

import com.example.wellhope.mywanandroid.constant.Constant;
import com.example.wellhope.mywanandroid.net.RetrofitConfig;
import com.example.wellhope.mywanandroid.net.WanAndroidApi;

import java.util.concurrent.TimeUnit;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Wellhope on 2018/3/3.
 */
@Module
public class HttpModule {

    @Provides
    public OkHttpClient.Builder provideOkHttpClient(){
        return new OkHttpClient.Builder()
                .retryOnConnectionFailure(true)
                .addInterceptor(RetrofitConfig.sRewriteCacheInterceptor)
                .addNetworkInterceptor(RetrofitConfig.sRewriteCacheInterceptor)
                .connectTimeout(10, TimeUnit.SECONDS);
    }


    @Provides
    public WanAndroidApi provideNetApis(OkHttpClient.Builder builder) {
        return new Retrofit.Builder()
                .client(builder.build())
                .baseUrl(Constant.API_WANANDROID)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(WanAndroidApi.class);
    }

}
