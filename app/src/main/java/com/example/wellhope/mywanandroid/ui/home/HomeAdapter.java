package com.example.wellhope.mywanandroid.ui.home;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.wellhope.mywanandroid.R;
import com.example.wellhope.mywanandroid.bean.ArticlePageBean;

import java.util.List;

/**
 * Created by Wellhope on 2018/3/3.
 */

public class HomeAdapter extends BaseQuickAdapter<ArticlePageBean.ItemBean,BaseViewHolder>{

    public HomeAdapter(@Nullable List<ArticlePageBean.ItemBean> data) {
        super(R.layout.item_article,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ArticlePageBean.ItemBean item) {
        helper.setText(R.id.article_author,item.getAuthor());
        helper.setText(R.id.article_title,item.getTitle());
        helper.setText(R.id.article_chapter,item.getChapterName());
    }
}
