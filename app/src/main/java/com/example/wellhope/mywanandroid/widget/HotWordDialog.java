package com.example.wellhope.mywanandroid.widget;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.wellhope.mywanandroid.R;
import com.flyco.dialog.widget.popup.base.BasePopup;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by season on 2018/3/6.
 */

public class HotWordDialog extends BasePopup<HotWordDialog> {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    List<String> hotwords;

    public HotWordDialog(Context context) {
        super(context);
    }
    private BaseQuickAdapter<String, BaseViewHolder> adapter;

    @Override
    public View onCreatePopupView() {
        View inflate = View.inflate(mContext, R.layout.popup_hotword, null);
        ButterKnife.bind(this, inflate);
        hotwords = Arrays.asList(new String[]{"热更新","NDK"});
        recyclerView.setLayoutManager(new GridLayoutManager(mContext, 2));
        recyclerView.setAdapter(adapter = new BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_hotword, hotwords) {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            protected void convert(BaseViewHolder helper, String s) {
                helper.setText(R.id.tv_hotword, s);
            }
        });
        return inflate;
    }

    @Override
    public void setUiBeforShow() {

    }
}
