package com.example.wellhope.mywanandroid.di;

import com.example.wellhope.mywanandroid.MyApp;
import com.example.wellhope.mywanandroid.constant.Constant;
import com.example.wellhope.mywanandroid.net.CookiesManager;
import com.example.wellhope.mywanandroid.net.RetrofitConfig;
import com.example.wellhope.mywanandroid.net.WanAndroidApi;

import java.io.File;
import java.util.concurrent.TimeUnit;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Wellhope on 2018/3/3.
 */
@Module
public class HttpModule {

    OkHttpClient.Builder mBuilder;

    @Provides
    public OkHttpClient.Builder provideOkHttpClient(){
        if(mBuilder!=null)
            return mBuilder;
        // 指定缓存路径,缓存大小100Mb
        Cache cache = new Cache(new File(MyApp.getContext().getCacheDir(), "HttpCache"),
                1024 * 1024 * 10);
        return mBuilder=new OkHttpClient.Builder()
                .retryOnConnectionFailure(true)
                .cache(cache)
                .cookieJar(new CookiesManager())
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
