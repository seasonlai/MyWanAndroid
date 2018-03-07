package com.example.wellhope.mywanandroid.ui.system;

import com.example.wellhope.mywanandroid.base.BaseView;
import com.example.wellhope.mywanandroid.bean.SystemBean;

import java.util.List;

/**
 * Created by Wellhope on 2018/3/7.
 */

public class SystemContract {

    public interface View extends BaseView{
        void loadFirstKind(List<SystemBean> systemBeans);
        void loadSecondKind(List<SystemBean.SystemChild> systemChildren);
    }

    interface Presenter{
        void getSystemKind();
    }

}
