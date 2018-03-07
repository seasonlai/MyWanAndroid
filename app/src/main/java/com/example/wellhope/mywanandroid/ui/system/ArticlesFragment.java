package com.example.wellhope.mywanandroid.ui.system;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.example.wellhope.mywanandroid.R;
import com.example.wellhope.mywanandroid.base.BaseFragment;

import butterknife.BindView;

/**
 * Created by Wellhope on 2018/3/7.
 */

public class ArticlesFragment extends BaseFragment {

    @Override
    protected int getLayoutID() {
        return R.layout.fragment_articles;
    }

    @BindView(R.id.webView)
    WebView mWebView;

    @BindView(R.id.pb_progress)
    ProgressBar mPbProgress;

    int downCount = 0;

    static final String CID = "CID_ARTICLE";

    @Override
    protected void viewCreated(View view, @Nullable Bundle savedInstanceState) {

    }

    public static ArticlesFragment newInstance(int cid) {
        ArticlesFragment fragment = new ArticlesFragment();
        Bundle b = new Bundle();
        b.putInt(CID, cid);
        fragment.setArguments(b);
        return fragment;
    }

    @Override
    protected void lazyInit() {
        Bundle b = getArguments();
        if (b == null) {
            return;
        }
        getSetting(mWebView);
        int cid = b.getInt(CID);
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return super.shouldOverrideUrlLoading(view, request);
            }
        });
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (mPbProgress == null) {
                    return;
                }
                if (newProgress == 100) {
                    mPbProgress.setVisibility(View.GONE);
                } else {
                    mPbProgress.setVisibility(View.VISIBLE);
                    mPbProgress.setProgress(newProgress);
                }
            }
        });
        //http://www.wanandroid.com/article/list/0?cid=60
        mWebView.loadUrl(String.format("http://www.wanandroid.com/article/list/%d?cid=%d",downCount,cid));
    }

    private void getSetting(WebView webview) {
        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setSupportZoom(true);
        webview.getSettings().setBuiltInZoomControls(true);
        webview.getSettings().setDisplayZoomControls(false);
        webview.getSettings().setUseWideViewPort(false);
        webview.getSettings().setAppCacheEnabled(true);
        webview.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webview.getSettings().setAllowFileAccessFromFileURLs(true);
        //LOAD_CACHE_ELSE_NETWORK模式下，无论是否有网络，只要本地有缓存，都使用缓存。本地没有缓存时才从网络上获取
        webview.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webview.getSettings().setDomStorageEnabled(true);
        webview.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                return true;
            }
        });
    }
}
