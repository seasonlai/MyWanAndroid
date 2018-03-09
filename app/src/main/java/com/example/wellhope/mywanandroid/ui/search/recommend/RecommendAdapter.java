package com.example.wellhope.mywanandroid.ui.search.recommend;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.example.wellhope.mywanandroid.R;
import com.example.wellhope.mywanandroid.bean.RecommendBean;

import java.util.List;

/**
 * Created by Wellhope on 2018/3/9.
 */

public class RecommendAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity,BaseViewHolder> {

    public RecommendAdapter(List<MultiItemEntity> data) {
        super(data);
        addItemType(RecommendBean.TYPE_TITLE, R.layout.item_title);
        addItemType(RecommendBean.TYPE_TIPS, R.layout.item_tips);
        addItemType(RecommendBean.TYPE_HISTORY, R.layout.item_label);
        addItemType(RecommendBean.TYPE_HOTWORD, R.layout.item_label);
        addItemType(RecommendBean.TYPE_STARWEB, R.layout.item_label);
    }

    @Override
    protected void convert(BaseViewHolder helper, MultiItemEntity item) {
        switch (item.getItemType()){
            case RecommendBean.TYPE_TITLE:
                RecommendBean.Title title = (RecommendBean.Title) item;
                helper.setText(R.id.title_content, title.getTitle());
                helper.setVisible(R.id.title_clear, title.isCanClear());
                helper.addOnClickListener(R.id.title_clear);
                break;
            case RecommendBean.TYPE_TIPS:
                RecommendBean.Tips tips = (RecommendBean.Tips) item;
                helper.setText(R.id.tips_content, tips.getTips());
                break;
            case RecommendBean.TYPE_HISTORY:
                helper.setText(R.id.label_content,((RecommendBean.HistoryBean)item).getHistoryContent());
                helper.addOnClickListener(R.id.label_content);
                break;
            case RecommendBean.TYPE_HOTWORD:
                helper.setText(R.id.label_content,((RecommendBean.HotWordBean)item).getName());
                helper.addOnClickListener(R.id.label_content);
                break;
            case RecommendBean.TYPE_STARWEB:
                helper.setText(R.id.label_content,((RecommendBean.StarWebBean)item).getName());
                helper.addOnClickListener(R.id.label_content);
                break;
        }
    }
}
