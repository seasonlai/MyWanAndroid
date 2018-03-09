package com.example.wellhope.mywanandroid.ui.home;

import android.support.annotation.Nullable;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.wellhope.mywanandroid.R;
import com.example.wellhope.mywanandroid.bean.ArticlePageBean;

import java.util.List;

/**
 * Created by Wellhope on 2018/3/3.
 */

public class ArticleAdapter extends BaseQuickAdapter<ArticlePageBean.ItemBean,BaseViewHolder>{

    private View.OnClickListener mClickListener;

    public ArticleAdapter(@Nullable List<ArticlePageBean.ItemBean> data) {
        super(R.layout.item_article,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ArticlePageBean.ItemBean item) {
        helper.setText(R.id.article_author,item.getAuthor());
        helper.setText(R.id.article_title,item.getTitle());
        helper.setText(R.id.article_chapter,item.getChapterName());
        helper.setImageResource(R.id.article_favorite,item.isCollect()?
                R.drawable.ic_favor_sel:R.drawable.ic_favor_def);
        helper.addOnClickListener(R.id.article_favorite);
        helper.setText(R.id.article_time,item.getNiceDate());
    }

//    public View.OnClickListener getClickListener() {
//        if(mClickListener==null)
//            mClickListener=new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                }
//            };
//        return mClickListener;
//    }
}
