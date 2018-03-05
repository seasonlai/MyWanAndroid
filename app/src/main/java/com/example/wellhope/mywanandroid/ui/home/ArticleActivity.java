package com.example.wellhope.mywanandroid.ui.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wellhope.mywanandroid.R;
import com.example.wellhope.mywanandroid.base.BaseActivity;
import com.example.wellhope.mywanandroid.bean.ParcelableBean;
import com.example.wellhope.mywanandroid.utils.StatusBarUtil;

import butterknife.BindView;
import butterknife.OnClick;

public class ArticleActivity extends BaseActivity<ArticlePresenter> implements ArticleContract.View {

    public static final String ARTICLE = "ArticleActivity_article";

    @Override
    protected int getContentLayout() {
        return R.layout.activity_article;
    }

    public static void launch(Context context,ParcelableBean data){
        Intent intent = new Intent(context,ArticleActivity.class);
        intent.putExtra(ARTICLE,data);
        context.startActivity(intent);
    }

    @BindView(R.id.webView)
    WebView mWebView;

    @BindView(R.id.pb_progress)
    ProgressBar mPbProgress;

    @BindView(R.id.tv_title)
    TextView mTitle;

    @Override
    protected void bindView(@Nullable Bundle savedInstanceState) {
        StatusBarUtil.setColor(this, ContextCompat.getColor(this,R.color.colorPrimary));
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
//        toolbar.setNavigationIcon(R.drawable.ic_back);

//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(ArticleActivity.this,"xxx",Toast.LENGTH_SHORT).show();
//                ArticleActivity.this.finish();
//            }
//        });
        setSupportActionBar(toolbar);

        webviewSetting(mWebView);
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
    }

    @Override
    protected void loadData() {
        Intent intent = getIntent();
        Parcelable article = intent.getParcelableExtra(ARTICLE);
        this.mPesenter.loadData(article);
    }

    @Override
    public void loadArticle(ArticlePresenter.Article article) {
        if(!TextUtils.isEmpty(article.url)) {
            mWebView.loadUrl(article.url);
//            getSupportActionBar().setSubtitle(TextUtils.isEmpty(article.url)?"":article.title);
            mTitle.setText(TextUtils.isEmpty(article.url)?"":article.title);
        }
    }

    @OnClick(R.id.iv_back)
    public void backward(){
        finish();
    }

    private void webviewSetting(WebView webview) {
        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setSupportZoom(true);
        webview.getSettings().setBuiltInZoomControls(true);
        webview.getSettings().setDisplayZoomControls(false);
        webview.getSettings().setUseWideViewPort(false);
        webview.getSettings().setAppCacheEnabled(true);
        webview.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webview.getSettings().setAllowFileAccessFromFileURLs(true);
        webview.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);//LOAD_CACHE_ELSE_NETWORK模式下，无论是否有网络，只要本地有缓存，都使用缓存。本地没有缓存时才从网络上获取
        webview.getSettings().setDomStorageEnabled(true);
        webview.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                return true;
            }
        });
    }

}
