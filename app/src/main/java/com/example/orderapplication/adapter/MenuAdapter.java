package com.example.orderapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.orderapplication.R;
import com.example.orderapplication.bean.FoodBean;

import java.util.ArrayList;
import java.util.List;

public class MenuAdapter extends BaseAdapter {

    private Context context;
    private List<FoodBean> beans=new ArrayList<>();

    public MenuAdapter(Context context) {
        this.context = context;
    }

    public void setBeans(List<FoodBean> beans) {
        this.beans.clear();
        this.beans.addAll(beans);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return beans.size();
    }

    @Override
    public Object getItem(int position) {
        return beans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder viewHolder=null;
        if (convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.manu_item,null);
            viewHolder=new ViewHolder();
            viewHolder.tvFoodName=convertView.findViewById(R.id.tv_food_name);
            viewHolder.tvPrice=convertView.findViewById(R.id.tv_price);
            viewHolder.tvTaste=convertView.findViewById(R.id.tv_taste);
            viewHolder.ivFoodPic=convertView.findViewById(R.id.iv_food_pic);
            viewHolder.btnAddCar=convertView.findViewById(R.id.btn_add_car);
            convertView.setTag(viewHolder);
        }
        else{
            viewHolder= (ViewHolder) convertView.getTag();
        }

        FoodBean foodBean=beans.get(position);
        viewHolder.tvFoodName.setText(foodBean.getFoodName());
        viewHolder.tvPrice.setText("￥"+String.valueOf(foodBean.getPrice()));
        viewHolder.tvTaste.setText(foodBean.getTaste());
        Glide.with(context).load(foodBean.getFoodPic()).error(R.mipmap.ic_launcher).into(viewHolder.ivFoodPic);

        return convertView;
    }

    class ViewHolder{
        TextView tvFoodName,tvTaste,tvPrice;
        ImageView ivFoodPic;
        public Button btnAddCar;
    }
}
