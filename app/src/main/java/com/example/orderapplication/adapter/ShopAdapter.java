package com.example.orderapplication.adapter;

import static androidx.constraintlayout.motion.utils.Oscillator.TAG;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.orderapplication.R;
import com.example.orderapplication.activity.ShopDetailActivity;
import com.example.orderapplication.bean.ShopBean;

import java.util.ArrayList;
import java.util.List;

public class ShopAdapter extends BaseAdapter {
    private Context context;
    private List<ShopBean> data=new ArrayList<>();

    public ShopAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<ShopBean> data) {
        this.data.clear();
        this.data.addAll(data);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder viewHolder=null;
        if (convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.shop_item,null);
            viewHolder=new ViewHolder();
            viewHolder.tvOfferPrice=convertView.findViewById(R.id.tv_offer_price);
            viewHolder.tvShopName=convertView.findViewById(R.id.tv_shop_name);
            viewHolder.tvTime=convertView.findViewById(R.id.tv_time);
            viewHolder.ivShopPic=convertView.findViewById(R.id.iv_shop_pic);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder= (ViewHolder) convertView.getTag();
        }

        ShopBean shopBean=data.get(position);
        Glide.with(context).load(shopBean.getShopPic()).error(R.mipmap.ic_launcher).into(viewHolder.ivShopPic);
        Log.i(TAG, "getView: "+shopBean.getShopPic());
        viewHolder.tvShopName.setText(shopBean.getShopName());
        viewHolder.tvTime.setText(shopBean.getTime());
        viewHolder.tvOfferPrice.setText("配送费：￥"+shopBean.getDistributionCost());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, ShopDetailActivity.class);
                intent.putExtra("shop",shopBean);
                context.startActivity(intent);

            }
        });

        return convertView;
    }
    class ViewHolder{
        TextView tvShopName,tvOfferPrice,tvTime;
        ImageView ivShopPic;
    }
}
