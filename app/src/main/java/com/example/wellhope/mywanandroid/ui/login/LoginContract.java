package com.example.wellhope.mywanandroid.ui.login;

import com.example.wellhope.mywanandroid.base.BaseView;

/**
 * Created by Wellhope on 2018/3/8.
 */

public class LoginContract {

    public interface View extends BaseView{

        void login(boolean success,String... errorMsg);

        void register(boolean success,String... errorMsg);

    }

    interface Presenter{

        void login(String username,String password);

        void register(String username,String password,String rePassword);
    }

}
