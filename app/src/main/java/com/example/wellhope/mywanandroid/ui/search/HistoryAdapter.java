package com.example.wellhope.mywanandroid.ui.search;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.wellhope.mywanandroid.R;
import com.example.wellhope.mywanandroid.bean.HistoryBean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HistoryAdapter extends ArrayAdapter<HistoryBean> {


    public List<HistoryBean> mHistories;


    public HistoryAdapter(@NonNull Context context, int resource) {
//        this(context, resource, new ArrayList<HistoryBean>());
        this(context, resource, Arrays.asList(new HistoryBean[]{
                new HistoryBean("666",1234560)
        }));
    }

    public HistoryAdapter(@NonNull Context context, int resource, List<HistoryBean> list) {
        super(context, resource, list);

        mHistories = list;
    }

    List<HistoryBean> mOriginData;

    @NonNull
    @Override
    public Filter getFilter() {
        return new Filter() {

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                if (mOriginData == null) {
                    //保存一份未筛选前的完整数据
                    mOriginData = new ArrayList<>(mHistories);
                }
                if (constraint == null || constraint.length() == 0) {
                    //如果接收到的文字为空，则不作比较，直接返回未筛选前的完整数据
                    results.count = mOriginData.size();
                    results.values = mOriginData;
                } else {
                    List<HistoryBean> filteredArrList = new ArrayList<>();
                    //遍历原始数据，与接收到的文字作比较，得到筛选结果
                    constraint = constraint.toString().toLowerCase();
                    for (int i = 0; i < mHistories.size(); i++) {
                        HistoryBean data = mHistories.get(i);
                        if (data.getHistoryContent().toLowerCase().startsWith(constraint.toString())) {
                            filteredArrList.add(data);
                        }
                    }
                    results.count = filteredArrList.size();
                    results.values = filteredArrList;
                }
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                List<HistoryBean> historyBeanList = (List<HistoryBean>) results.values;
//                mHistories.removeAll(mHistories);
//                mHistories.addAll (historyBeanList);
                remove();
                mHistories=historyBeanList;
                notifyDataSetChanged();
            }
        };
    }

    public void changeData(List<HistoryBean> histories){
        if(mOriginData==null)
            mOriginData =new ArrayList<>();
        mOriginData.removeAll(mOriginData);
        if(histories!=null){
            mOriginData.addAll(histories);
        }

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        HistoryBean item = getItem(position);
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_history_tip, null);
            TextView tv = convertView.findViewById(R.id.tv_history);
            ImageButton ib = convertView.findViewById(R.id.ib_delete);
            holder = new ViewHolder(tv, ib);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.setItem(item);
        return convertView;
    }

    static class ViewHolder {
        TextView tv;
        ImageButton ib;
        private HistoryBean bean;

        public ViewHolder(TextView tv, ImageButton ib) {
            this.tv = tv;
            this.ib = ib;
            ib.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    ViewHolder.this.bean;
                }
            });
        }

        public void setItem(HistoryBean bean) {
            if (bean == null)
                return;
            tv.setText(bean.getHistoryContent());
            this.bean = bean;
        }
    }
}