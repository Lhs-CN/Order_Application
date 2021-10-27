package com.example.orderapplication.activity;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.orderapplication.R;
import com.example.orderapplication.bean.ShopBean;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

public class ShopDetailActivity extends AppCompatActivity implements View.OnClickListener {
    public ShopBean bean;
    public TextView tvShopName,tvTime,tvNotice,tvTitle;
    public ImageView ivShopPic,ivBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_detail);
        bean=(ShopBean)getIntent().getSerializableExtra("shop");

        initView();
        initData();


    }

    private void initData() {
        tvShopName.setText(bean.getShopName());
        tvTime.setText(bean.getTime());
        tvNotice.setText(bean.getShopNotice());
        Glide.with(this).load(bean.getShopPic()).error(R.mipmap.ic_launcher).into(ivShopPic);

    }

    private void initView() {
        tvShopName=findViewById(R.id.tv_shop_name);
        tvTime=findViewById(R.id.tv_time);
        tvNotice=findViewById(R.id.tv_notice);
        tvTitle=findViewById(R.id.tv_title);
        ivShopPic=findViewById(R.id.iv_shop_pic);
        ivBack=findViewById(R.id.iv_back);

        tvTitle.setText("店铺详情");
        ivBack.setVisibility(View.VISIBLE);
        ivBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.iv_back:
                finish();
                break;
        }
    }
}