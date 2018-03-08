package com.example.wellhope.mywanandroid.ui.login;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.example.wellhope.mywanandroid.R;
import com.example.wellhope.mywanandroid.base.BaseActivity;
import com.example.wellhope.mywanandroid.constant.Constant;
import com.example.wellhope.mywanandroid.event.LoginEvent;
import com.example.wellhope.mywanandroid.event.RxBus;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity<LoginPresenter> implements LoginContract.View {

    @BindView(R.id.userName)
    EditText mUser;

    @BindView(R.id.password)
    EditText mPwd;

    @BindView(R.id.password2)
    EditText mPwd2;

    @BindView(R.id.loginOrRegister)
    TextView mLoginOrRegister;

    @BindView(R.id.switchMode)
    TextView mSwitchMode;

    boolean isLoginStatus;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected void bindView(@Nullable Bundle savedInstanceState) {
        isLoginStatus = true;
    }

    @OnClick(R.id.loginOrRegister)
    public void loginOrRegister() {
        if (isLoginStatus) {
            mPresenter.login(mUser.getText().toString(), mPwd.getText().toString());
        } else {
            mPresenter.register(mUser.getText().toString(),
                    mPwd.getText().toString(),
                    mPwd2.getText().toString());
        }
    }

    public static void launch(Context context) {
        context.startActivity(new Intent(context, LoginActivity.class));
    }

    @OnClick(R.id.switchMode)
    public void switchMode() {
        if (isLoginStatus) {
            mPwd2.setVisibility(View.VISIBLE);
            mSwitchMode.setText("登录");
            mLoginOrRegister.setText("注册");
        } else {
            mPwd2.setVisibility(View.GONE);
            mSwitchMode.setText("注册");
            mLoginOrRegister.setText("登录");
        }
        isLoginStatus = !isLoginStatus;
    }

    @Override
    public void login(boolean success, String... errorMsg) {
        if (success) {
            SPUtils.getInstance(Constant.SHARED_NAME).put(Constant.LOGIN_KEY, true);
//            SPUtils.getInstance(Constant.SHARED_NAME).put(Constant.USERNAME_KEY, user.getUsername());
//            SPUtils.getInstance(Constant.SHARED_NAME).put(Constant.PASSWORD_KEY, user.getPassword());
            /**登陆成功通知其他界面刷新*/
            RxBus.getInstance().post(new LoginEvent());
            finish();
        } else {
            if (errorMsg != null && errorMsg.length > 0)
                ToastUtils.showShort(errorMsg[0]);
        }
    }

    @Override
    public void register(boolean success, String... errorMsg) {
        if (success) {
            SPUtils.getInstance(Constant.SHARED_NAME).put(Constant.LOGIN_KEY, true);
            RxBus.getInstance().post(new LoginEvent());
            finish();
        } else {
            if (errorMsg != null && errorMsg.length > 0)
                ToastUtils.showShort(errorMsg[0]);
        }
    }
}
