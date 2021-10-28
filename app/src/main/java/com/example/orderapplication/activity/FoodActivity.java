package com.example.orderapplication.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.orderapplication.R;
import com.example.orderapplication.bean.FoodBean;

public class FoodActivity extends AppCompatActivity {
    private FoodBean bean;
    private TextView tv_food_name,  tv_price;
    private ImageView iv_food_pic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);
        bean = (FoodBean) getIntent().getSerializableExtra("food");
        initView();
        setData();
    }

    private void setData() {
        tv_food_name.setText(bean.getFoodName());
        tv_price.setText("￥" + bean.getPrice());
        Glide.with(this).load(bean.getFoodPic()).error(R.mipmap.ic_launcher).into(iv_food_pic);
    }

    private void initView() {
        tv_food_name = (TextView) findViewById(R.id.tv_food_name);
        tv_price = (TextView) findViewById(R.id.tv_price);
        iv_food_pic = (ImageView) findViewById(R.id.iv_food_pic);
    }
}