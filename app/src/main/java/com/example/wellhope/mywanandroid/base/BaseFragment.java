package com.example.wellhope.mywanandroid.base;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.wellhope.mywanandroid.MyApp;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Wellhope on 2018/2/28.
 */

public abstract class BaseFragment<T1 extends BasePresenter> extends SupportFragment implements BaseView{

    @Inject
    @Nullable
    protected T1 mPresenter;

//    @Nullable
//    @BindView(R.id.SimpleMultiStateView)
//    SimpleMultiStateView mSimpleMultiStateView;

    protected View mRootView;

    Unbinder unbinder;

    protected Dialog mLoadingDialog = null;

    protected abstract int getLayoutID();

    protected abstract void viewCreated(View view, @Nullable Bundle savedInstanceState);

    protected abstract void lazyInit();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRootView != null) {
            ViewGroup parent = (ViewGroup) mRootView.getParent();
            if (parent != null) {
                parent.removeView(mRootView);
            }
        } else {
            mRootView = inflater.inflate(getLayoutID(), container, false);
            unbinder = ButterKnife.bind(this, mRootView);
        }
//        initStateView();
//        mLoadingDialog = DialogHelper.getLoadingDialog(getActivity());
        return mRootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewCreated(view,savedInstanceState);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        lazyInit();
    }
//
//    protected void toast(String string) {
//        T.showShort(MyApp.getContext(), string);
//    }

//    @Override
//    public void onResume() {
//        super.onResume();
//        if(mPresenter!=null) {
//            mPresenter.subscribe();
//        }
//    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(unbinder!=null) {
            unbinder.unbind();
        }
        if(mPresenter!=null){
            mPresenter.detachView();
            mPresenter.unSubscribe();
        }
    }
//
//    private void initStateView() {
//        if (mSimpleMultiStateView == null) return;
//        mSimpleMultiStateView.setEmptyResource(R.layout.view_empty)
//                .setRetryResource(R.layout.view_retry)
//                .setLoadingResource(R.layout.view_loading)
//                .setNoNetResource(R.layout.view_nonet)
//                .build()
//                .setonReLoadlistener(new MultiStateView.onReLoadlistener() {
//                    @Override
//                    public void onReload() {
//                        onRetry();
//                    }
//                });
//    }


    protected void hideLoadingDialog() {
        if (mLoadingDialog != null && mLoadingDialog.isShowing())
            mLoadingDialog.dismiss();
    }

    @Override
    public void showLoading() {
//        if (mSimpleMultiStateView != null) {
//            mSimpleMultiStateView.showLoadingView();
//        }
    }

    @Override
    public void showSuccess() {
        hideLoadingDialog();
//        if (mSimpleMultiStateView != null) {
//            mSimpleMultiStateView.showContent();
//        }
    }

    @Override
    public void showFailed() {
//        if (mSimpleMultiStateView != null) {
//            mSimpleMultiStateView.showErrorView();
//        }
    }

    @Override
    public void showNoNet() {
//        if (mSimpleMultiStateView != null) {
//            mSimpleMultiStateView.showNoNetView();
//        }
    }

    @Override
    public void onRetry() {

    }

}
