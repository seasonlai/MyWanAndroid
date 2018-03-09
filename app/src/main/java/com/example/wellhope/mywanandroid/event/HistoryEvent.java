package com.example.wellhope.mywanandroid.event;

import com.example.wellhope.mywanandroid.bean.RecommendBean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Wellhope on 2018/3/9.
 */

public class HistoryEvent {

    int type;

    public static final int TYPE_SAVE=0xf;
    public static final int TYPE_UPDATE=0xff;
    public static final int TYPE_DEL=0xfff;
    public static final int TYPE_SAVE_SUCCESS=0xfff0;
    public static final int TYPE_DEL_SUCCESS=0xfff1;

    public HistoryEvent(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public static class SingleEvent extends HistoryEvent{
        RecommendBean.HistoryBean mBean;

        public SingleEvent(RecommendBean.HistoryBean bean,int type) {
            super(type);
            mBean = bean;

        }

        public RecommendBean.HistoryBean getBean() {
            return mBean;
        }

    }

    public static class ListEvent extends HistoryEvent{
        List<RecommendBean.HistoryBean> mList;
        public ListEvent(List<RecommendBean.HistoryBean> beans,int type){
            super(type);
            this.mList = beans;
        }

        public List<RecommendBean.HistoryBean> getList() {
            return mList;
        }

    }
}
