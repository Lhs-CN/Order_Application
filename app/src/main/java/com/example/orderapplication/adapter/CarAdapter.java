package com.example.orderapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.example.orderapplication.R;
import com.example.orderapplication.bean.FoodBean;

public class CarAdapter extends BaseAdapter {
    private Context context;
    private List<FoodBean> beans=new ArrayList<>();
    private OnSelectListener onSelectListener;
    public CarAdapter(Context context, OnSelectListener onSelectListener) {
        this.context = context;
        this.onSelectListener=onSelectListener;
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
        //复用convertView
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.car_item, null);
            viewHolder.tvFoodName = (TextView) convertView.findViewById(R.id.tv_food_name);
            viewHolder.tvFoodCount = (TextView) convertView.findViewById(R.id.tv_food_count);
            viewHolder.tvFoodPrice = (TextView) convertView.findViewById(R.id.tv_food_price);
            viewHolder.ivAdd = (ImageView) convertView.findViewById(R.id.iv_add);
            viewHolder.ivMinus = (ImageView) convertView.findViewById(R.id.iv_minus);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final FoodBean bean = getItem(position);
        if (bean != null) {
            viewHolder.tvFoodName.setText(bean.getFoodName());
            viewHolder.tvFoodCount.setText(bean.getCount()+"");
            BigDecimal count=BigDecimal.valueOf(bean.getCount());
            viewHolder.tvFoodPrice.setText("￥" + bean.getPrice().multiply(count));
        }
        viewHolder.ivAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSelectListener.onSelectAdd(position,viewHolder.tvFoodCount,viewHolder.tvFoodPrice);
            }
        });
        viewHolder.ivMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSelectListener.onSelectMis(position,viewHolder.tvFoodCount,viewHolder.tvFoodPrice);
            }
        });
        return convertView;
    }
    class ViewHolder {
        public TextView tvFoodName, tvFoodCount, tvFoodPrice;
        public ImageView ivAdd, ivMinus;
    }
    public interface OnSelectListener {
        void onSelectAdd(int position,TextView tvFoodPrice,TextView tvFoodCount);
        void onSelectMis(int position,TextView tvFoodPrice,TextView tvFoodCount);
    }
}
