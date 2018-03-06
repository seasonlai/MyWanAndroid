package com.example.wellhope.mywanandroid.net;

import com.example.wellhope.mywanandroid.bean.ArticlePageBean;
import com.example.wellhope.mywanandroid.bean.BannerBean;
import com.example.wellhope.mywanandroid.bean.HotWordBean;
import com.example.wellhope.mywanandroid.bean.MsgBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Wellhope on 2018/3/3.
 */

public interface WanAndroidApi {

    @GET("/banner/json")
    Observable<MsgBean<List<BannerBean>>> getBannerList();


    @GET("/article/list/{num}/json")
    Observable<MsgBean<ArticlePageBean>> getPageArticle(@Path("num") Integer num);

    @GET("/hotkey/json")
    Observable<MsgBean<List<HotWordBean>>> getHotWord();

}
