package com.example.orderapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.example.orderapplication.R;
import com.example.orderapplication.bean.FoodBean;

public class OrderAdapter extends BaseAdapter {
    private Context context;
    private List<FoodBean> beans=new ArrayList<>();
    public OrderAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<FoodBean> beans) {
        this.beans.clear();
        this.beans.addAll(beans);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return beans.size();
    }

    @Override
    public FoodBean getItem(int position) {
        return beans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.order_item, null);
            viewHolder.tvFoodName = (TextView) convertView.findViewById(R.id.tv_food_name);
            viewHolder.tvCount = (TextView) convertView.findViewById(R.id.tv_count);
            viewHolder.tvMoney = (TextView) convertView.findViewById(R.id.tv_money);
            viewHolder.ivFoodPic = (ImageView) convertView.findViewById(R.id.iv_food_pic);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final FoodBean bean = getItem(position);
        if (bean != null) {
            viewHolder.tvFoodName.setText(bean.getFoodName());
            viewHolder.tvCount.setText("x"+bean.getCount());
            viewHolder.tvMoney.setText("￥"+bean.getPrice().multiply(BigDecimal.valueOf(bean.getCount())));
            Glide.with(context).load(bean.getFoodPic()).error(R.mipmap.ic_launcher).into(viewHolder.ivFoodPic);
        }
        return convertView;
    }
    class ViewHolder {
        public TextView tvFoodName, tvCount, tvMoney;
        public ImageView ivFoodPic;
    }
}
