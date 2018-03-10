package com.example.wellhope.mywanandroid.ui.search;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.wellhope.mywanandroid.R;
import com.example.wellhope.mywanandroid.bean.RecommendBean;
import com.example.wellhope.mywanandroid.bean.RecommendBean.HistoryBean;

import java.util.ArrayList;
import java.util.List;

public class HistoryAdapter extends ArrayAdapter<HistoryBean> {


    List<HistoryBean> mOriginData;
    public static final String TAG = HistoryAdapter.class.getSimpleName();


    public HistoryAdapter(@NonNull Context context, int resource) {
        this(context, resource, new ArrayList<HistoryBean>());
    }

    public HistoryAdapter(@NonNull Context context, int resource, List<HistoryBean> list) {
        super(context, resource, list);
        mOriginData = new ArrayList<>(list);
    }


    @NonNull
    @Override
    public Filter getFilter() {
        return new Filter() {

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                if (constraint == null || constraint.length() == 0) {
                    //如果接收到的文字为空，则不作比较，直接返回未筛选前的完整数据
                    results.count = mOriginData.size();
                    results.values = mOriginData;
                } else {
                    List<HistoryBean> filteredArrList = new ArrayList<>();
                    //遍历原始数据，与接收到的文字作比较，得到筛选结果
                    constraint = constraint.toString().toLowerCase();
                    for (HistoryBean data : mOriginData) {
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
//                Log.e(TAG, "publishResults: size :" + historyBeanList.size());
                setNotifyOnChange(false);
                HistoryAdapter.super.clear();
                setNotifyOnChange(true);
                addAll(historyBeanList);
            }
        };
    }

    public void changeData(List<HistoryBean> histories) {
        if (mOriginData == null)
            mOriginData = new ArrayList<>();
        mOriginData.clear();
        if (histories != null) {
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
        holder.setContent(item,position);
        return convertView;
    }

    @Override
    public void remove(@Nullable HistoryBean object) {
        super.remove(object);
        mOriginData.remove(object);
    }

    @Override
    public void add(HistoryBean historyBean) {
        mOriginData.add(historyBean);
    }

    @Override
    public void clear() {
        super.clear();
        mOriginData.clear();
    }

    class ViewHolder {
        TextView tv;
        ImageButton ib;

        ViewHolder(TextView tv, ImageButton ib) {
            this.tv = tv;
            this.ib = ib;
            this.tv.setOnClickListener(HistoryAdapter.this.getOnClickListener());
            this.ib.setOnClickListener(HistoryAdapter.this.getOnClickListener());
        }

        void setContent(HistoryBean bean, int position) {
            this.tv.setTag(position);
            this.ib.setTag(position);
            this.tv.setText(bean.getHistoryContent());
        }

    }

    private OnClickListener mOnClickListener;

    public void setOnClickListener(OnClickListener onClickListener) {
        mOnClickListener = onClickListener;
    }

    interface OnClickListener {

        void textClick(HistoryBean bean);

        void delClick(HistoryBean bean);
    }

    private View.OnClickListener myClickListener;

    private View.OnClickListener getOnClickListener() {
        if (myClickListener == null) {
            myClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int p = (int) view.getTag();
                    if (mOnClickListener != null) {
                        if (view instanceof ImageButton) {
                            mOnClickListener.delClick(getItem(p));
                        } else if (view instanceof TextView) {
                            mOnClickListener.textClick(getItem(p));
                        }
                    }
                }
            };
        }
        return myClickListener;
    }


}