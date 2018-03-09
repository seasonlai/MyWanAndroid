package com.example.wellhope.mywanandroid.net;

import com.example.wellhope.mywanandroid.bean.ArticlePageBean;
import com.example.wellhope.mywanandroid.bean.BannerBean;
import com.example.wellhope.mywanandroid.bean.MsgBean;
import com.example.wellhope.mywanandroid.bean.RecommendBean;
import com.example.wellhope.mywanandroid.bean.SystemBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by Wellhope on 2018/3/3.
 */

public interface WanAndroidApi {

    @GET("/banner/json")
    Observable<MsgBean<List<BannerBean>>> getBannerList();


    @GET("/article/list/{num}/json")
    Observable<MsgBean<ArticlePageBean>> getPageArticle(@Path("num") int num);

    @GET("/hotkey/json")
    Observable<MsgBean<List<RecommendBean.HotWordBean>>> getHotWord();

    @GET("/friend/json")
    Observable<MsgBean<List<RecommendBean.StarWebBean>>> getStarWeb();

    @GET("/tree/json")
    Observable<MsgBean<List<SystemBean>>> getSystem();

    @POST("/lg/collect/{id}/json")
    Observable<MsgBean> collectArticle(@Path("id")int id);

    @POST("/lg/uncollect_originId/{id}/json")
    Observable<MsgBean> unCollectArticle(@Path("id")int id);

    @POST("/user/login")
    @FormUrlEncoded
    Observable<MsgBean> login(@Field("username") String userName, @Field("password")String pwd);

    @POST("/user/register")
    @FormUrlEncoded
    Observable<MsgBean> register(@Field("username") String userName, @Field("password")String pwd, @Field("repassword")String repwd);

    @POST("/article/query/{num}/json")
    @FormUrlEncoded
    Observable<MsgBean<ArticlePageBean>> search(@Path("num") int num,@Field("k")String key);
}
