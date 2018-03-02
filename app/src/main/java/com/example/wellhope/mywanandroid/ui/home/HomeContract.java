package com.example.wellhope.mywanandroid.ui.home;

import com.example.wellhope.mywanandroid.base.BaseView;
import com.example.wellhope.mywanandroid.bean.BannerBean;
import com.youth.banner.Banner;

import java.util.List;

/**
 * Created by season on 2018/3/3.
 */

public class HomeContract {

    interface View extends BaseView{
        void loadBanner(List<BannerBean> bannerList);
    }

    interface Presenter{
        void getData();
    }

}
