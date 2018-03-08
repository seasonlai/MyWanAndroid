package com.example.wellhope.mywanandroid.ui.personal;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.SPUtils;
import com.example.wellhope.mywanandroid.MyApp;
import com.example.wellhope.mywanandroid.R;
import com.example.wellhope.mywanandroid.base.BaseFragment;
import com.example.wellhope.mywanandroid.constant.Constant;
import com.example.wellhope.mywanandroid.event.LoginEvent;
import com.example.wellhope.mywanandroid.event.RxBus;
import com.example.wellhope.mywanandroid.net.CookiesManager;
import com.example.wellhope.mywanandroid.ui.login.LoginActivity;

import javax.inject.Inject;
import javax.inject.Singleton;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;

/**
 * A simple {@link Fragment} subclass.
 */
public class PersonalFragment extends BaseFragment {

    @BindView(R.id.exit_login)
    TextView exitLogin;

    @BindView(R.id.headView)
    ImageView headView;

    @BindView(R.id.tv_tip)
    TextView loginTip;

    @Singleton
    @Inject
    CookiesManager mCookiesManager;

    public static PersonalFragment newInstance() {
        return new PersonalFragment();
    }


    @Override
    protected int getLayoutID() {
        return R.layout.fragment_personal;
    }

    @Override
    protected void viewCreated(View view, @Nullable Bundle savedInstanceState) {
        changeStatus(MyApp.isLogin());

        RxBus.getInstance().toFlowable(LoginEvent.class)
                .subscribe(new Consumer<LoginEvent>() {
                    @Override
                    public void accept(LoginEvent loginEvent) throws Exception {
                        changeStatus(MyApp.isLogin());
                    }
                });
    }

    private void changeStatus(boolean isLogin) {
        if (MyApp.isLogin()) {
            headView.setImageResource(R.drawable.head_login);
            loginTip.setVisibility(View.GONE);
            exitLogin.setVisibility(View.VISIBLE);
        } else {
            headView.setImageResource(R.drawable.head_def);
            loginTip.setVisibility(View.VISIBLE);
            exitLogin.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.exit_login)
    public void exitLogin() {
        if (MyApp.isLogin()) {
            mCookiesManager.clearAllCookies();
            SPUtils.getInstance(Constant.SHARED_NAME).clear();
            RxBus.getInstance().post(new LoginEvent());
        } else {
            exitLogin.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.myFavor)
    public void myFavor() {

    }

    @OnClick(R.id.headView)
    public void toLogin() {
        if (!MyApp.isLogin()) {
            LoginActivity.launch(getContext());
        }
    }

    @Override
    protected void lazyInit() {

    }

}
