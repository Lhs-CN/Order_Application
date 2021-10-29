package com.example.orderapplication.activity;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.orderapplication.R;
import com.example.orderapplication.adapter.MenuAdapter;
import com.example.orderapplication.adapter.CarAdapter;
import com.example.orderapplication.bean.FoodBean;
import com.example.orderapplication.bean.ShopBean;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ShopDetailActivity extends AppCompatActivity implements View.OnClickListener {
    public ShopBean bean;
    public TextView tvShopName,tvTime,tvNotice,tvTitle,tvBack,
            tvSettleAccounts, tvCount, tvMoney, tvDistributionCost,
            tvNotEnough, tvClear;;
    public ImageView ivShopPic,ivBack, ivShopCar;
    public ListView lvList,lvCar;
    public static final int MSG_COUNT_OK = 1;
    public MenuAdapter menuAdapter;
    private MHandler mHandler;
    private int totalCount = 0;
    private BigDecimal totalMoney;            //购物车中菜品的总价格
    private List<FoodBean> carFoodList;      //购物车中的菜品数据
    private MenuAdapter adapter;
    private CarAdapter carAdapter;
    private RelativeLayout rlCarList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_detail);
        bean=(ShopBean)getIntent().getSerializableExtra("shop");
        mHandler = new MHandler();
        totalMoney = new BigDecimal(0.0);
        carFoodList = new ArrayList<>();
        initView();
        initAdapter();
        setData();

    }

    private void initAdapter() {
        menuAdapter=new MenuAdapter(this, new MenuAdapter.OnSelectListener() {
            @Override
            public void onSelectAddCar(int position) {
                //点击加入购物车按钮将菜添加到购物车中
                FoodBean fb = bean.getFoodList().get(position);
                fb.setCount(fb.getCount() + 1);
                Iterator<FoodBean> iterator = carFoodList.iterator();
                while (iterator.hasNext()) {
                    FoodBean food = iterator.next();
                    if (food.getFoodId() == fb.getFoodId()) {
                        iterator.remove();
                    }
                }
                carFoodList.add(fb);
                totalCount = totalCount + 1;
                totalMoney = totalMoney.add(fb.getPrice());
                carDataMsg();
            }
        });
        carAdapter = new CarAdapter(this, new CarAdapter.OnSelectListener() {
            @Override
            public void onSelectAdd(int position, TextView tv_food_count, TextView
                    tv_food_price) {
                //添加菜品到购物车中
                FoodBean bean = carFoodList.get(position);
                tv_food_count.setText(bean.getCount() + 1 + "");
                BigDecimal count = BigDecimal.valueOf(bean.getCount() + 1);
                tv_food_price.setText("￥" + bean.getPrice().multiply(count));
                bean.setCount(bean.getCount() + 1);
                Iterator<FoodBean> iterator = carFoodList.iterator();
                while (iterator.hasNext()) {
                    FoodBean food = iterator.next();
                    if (food.getFoodId() == bean.getFoodId()) {
                        iterator.remove();
                    }
                }
                carFoodList.add(position, bean);
                totalCount = totalCount + 1;
                totalMoney = totalMoney.add(bean.getPrice());
                carDataMsg();
            }
            @Override
            public void onSelectMis(int position, TextView tv_food_count, TextView
                    tv_food_price) {
                FoodBean bean = carFoodList.get(position);
                tv_food_count.setText(bean.getCount() - 1 + "");
                BigDecimal count = BigDecimal.valueOf(bean.getCount() - 1);
                tv_food_price.setText("￥" + bean.getPrice().multiply(count));
                minusCarData(bean, position);
            }
        });
        lvList.setAdapter(menuAdapter);
    }

    private void setData() {
        tvShopName.setText(bean.getShopName());
        tvTime.setText(bean.getTime());
        tvNotice.setText(bean.getShopNotice());
        Glide.with(this).load(bean.getShopPic()).error(R.mipmap.ic_launcher).into(ivShopPic);
        menuAdapter.setBeans(bean.getFoodList());

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

        lvList=findViewById(R.id.lv_list);
        tvClear = (TextView) findViewById(R.id.tv_clear);
        tvSettleAccounts = (TextView) findViewById(R.id.tv_settle_accounts);
        tvDistributionCost = (TextView) findViewById(R.id.tv_distribution_cost);
        ivShopCar = (ImageView) findViewById(R.id.iv_shop_car);
        tvCount = (TextView) findViewById(R.id.tv_count);
        tvMoney = (TextView) findViewById(R.id.tv_money);
        tvNotEnough = (TextView) findViewById(R.id.tv_not_enough);
        lvCar = (ListView) findViewById(R.id.lv_car);
        rlCarList = (RelativeLayout) findViewById(R.id.rl_car_list);
        rlCarList.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (rlCarList.getVisibility() == View.VISIBLE) {
                    rlCarList.setVisibility(View.GONE);
                }
                return false;
            }
        });
        tvSettleAccounts.setOnClickListener(this);
        ivShopCar.setOnClickListener(this);
        tvClear.setOnClickListener(this);
        tvClear = (TextView) findViewById(R.id.tv_clear);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_back:               //返回按钮的点击事件
                finish();
                break;
            case R.id.tv_settle_accounts: //去结算按钮的点击事件
                //跳转到订单界面
                if (totalCount > 0) {
                    Intent intent = new Intent(ShopDetailActivity.this, OrderActivity.class);
                    intent.putExtra("carFoodList", (Serializable) carFoodList);
                    intent.putExtra("totalMoney", totalMoney + "");
                    intent.putExtra("distributionCost", bean.getDistributionCost() + "");
                    startActivity(intent);
                }
                break;
            case R.id.iv_shop_car:          //购物车的点击事件
                if (totalCount <= 0) return;
                if (rlCarList != null) {
                    if (rlCarList.getVisibility() == View.VISIBLE) {
                        rlCarList.setVisibility(View.GONE);
                    } else {
                        rlCarList.setVisibility(View.VISIBLE);
                    }
                }
                carAdapter.setData(carFoodList);
                lvCar.setAdapter(carAdapter);
                break;
            case R.id.tv_clear://清空按钮的点击事件
                if (carFoodList == null) return;
                for (FoodBean bean : carFoodList) {
                    bean.setCount(0);
                }
                carFoodList.clear();
                carAdapter.notifyDataSetChanged();
                totalCount = 0;
                totalMoney = BigDecimal.valueOf(0.0);
                carDataMsg();
                break;
        }
    }
    private void carDataMsg() {
        Message msg = Message.obtain();
        msg.what = MSG_COUNT_OK;
        Bundle bundle = new Bundle();
        bundle.putString("totalCount", totalCount + "");
        bundle.putString("totalMoney", totalMoney + "");
        msg.setData(bundle);
        mHandler.sendMessage(msg);
    }
    private void minusCarData(FoodBean bean, int position) {
        int count = bean.getCount() - 1;
        bean.setCount(count);
        Iterator<FoodBean> iterator = carFoodList.iterator();
        while (iterator.hasNext()) {
            FoodBean food = iterator.next();
            if (food.getFoodId() == bean.getFoodId()) {
                iterator.remove();
            }
        }
        if (count > 0) carFoodList.add(position, bean);
        else carAdapter.notifyDataSetChanged();
        totalCount = totalCount - 1;
        totalMoney = totalMoney.subtract(bean.getPrice());
        carDataMsg();
    }



    class MHandler extends Handler {
        @Override
        public void dispatchMessage(Message msg) {
            super.dispatchMessage(msg);
            switch (msg.what) {
                case MSG_COUNT_OK:
                    Bundle bundle = msg.getData();
                    String count = bundle.getString("totalCount", "");
                    String money = bundle.getString("totalMoney", "");
                    if (bundle != null) {
                        if (Integer.parseInt(count) > 0) {
                            ivShopCar.setImageResource(R.drawable.shop_car);
                            tvCount.setVisibility(View.VISIBLE);
                            tvDistributionCost.setVisibility(View.VISIBLE);
                            tvMoney.setTextColor(Color.parseColor("#ffffff"));
                            tvMoney.getPaint().setFakeBoldText(true);
                            tvMoney.setText("￥" + money);
                            tvCount.setText(count);
                            tvDistributionCost.setText("另需配送费￥" + bean.getDistributionCost());
                            //将变量money的类型转换为BigDecimal类型
                            BigDecimal bdm = new BigDecimal(money);
                            int result = bdm.compareTo(bean.getOfferPrice());
                            if (-1 == result) {
                                tvSettleAccounts.setVisibility(View.GONE);
                                tvNotEnough.setVisibility(View.VISIBLE);
                                BigDecimal m = bean.getOfferPrice().subtract(bdm);
                                tvNotEnough.setText("还差￥" + m + "起送");
                            } else { //总价格>=起送价格
                                tvSettleAccounts.setVisibility(View.VISIBLE);
                                tvNotEnough.setVisibility(View.GONE);
                            }
                        } else {
                            if (rlCarList.getVisibility() == View.VISIBLE) {
                                rlCarList.setVisibility(View.GONE);
                            } else {
                                rlCarList.setVisibility(View.VISIBLE);
                            }
                            ivShopCar.setImageResource(R.drawable.shop_car_empty);
                            tvSettleAccounts.setVisibility(View.GONE);
                            tvNotEnough.setVisibility(View.VISIBLE);
                            tvNotEnough.setText("￥" + bean.getOfferPrice() + "起送");
                            tvCount.setVisibility(View.GONE);
                            tvDistributionCost.setVisibility(View.GONE);
                            tvMoney.setTextColor(getResources().getColor(R.color.
                                    light_gray));
                            tvMoney.setText("未选购商品");
                        }
                    }
                    break;
            }
        }
    }
}