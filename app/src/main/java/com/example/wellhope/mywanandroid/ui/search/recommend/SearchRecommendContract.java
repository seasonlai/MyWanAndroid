package com.example.wellhope.mywanandroid.ui.search.recommend;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.example.wellhope.mywanandroid.base.BaseView;
import com.example.wellhope.mywanandroid.bean.RecommendBean;

import java.util.List;

/**
 * Created by Wellhope on 2018/3/9.
 */

public class SearchRecommendContract {
    public interface View extends BaseView {

        void clearHistory();

        void loadHistory(List<RecommendBean.HistoryBean> histories);

        void saveHistory(MultiItemEntity historyBean);

        void deleteHistory(MultiItemEntity historyBean);

        void loadHotWord(List<RecommendBean.HotWordBean> hotWords);

        void loadStartWeb(List<RecommendBean.StarWebBean> starWeb);
    }

    public interface Presenter {

        void getHistory();

        void getHotWord();

        void getStarWeb();

        void saveHistory(RecommendBean.HistoryBean history);

        void deleteHistory(RecommendBean.HistoryBean history);

        void clearHistory();
    }
}
