package com.example.orderapplication.activity;

import android.app.Dialog;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.math.BigDecimal;
import java.util.List;

import com.example.orderapplication.R;
import com.example.orderapplication.adapter.OrderAdapter;
import com.example.orderapplication.bean.FoodBean;

public class OrderActivity extends AppCompatActivity {
    private ListView lvOrder;
    private OrderAdapter orderAdapter;
    private List<FoodBean> carFoodList;
    private TextView tvTitle, tvDistributionCost, tvTotalCost,
            tvCost, tvPayment;
    private ImageView ivBack;
    private RelativeLayout rlTitleBar;
    private BigDecimal money,distributionCost;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        carFoodList= (List<FoodBean>) getIntent().getSerializableExtra("carFoodList");
        money=new BigDecimal(getIntent().getStringExtra("totalMoney"));
        distributionCost=new BigDecimal(getIntent().getStringExtra(
                "distributionCost"));
        initView();
        setData();
    }

    private void initView(){
        tvTitle = (TextView) findViewById(R.id.tv_title);
        tvTitle.setText("订单");
        rlTitleBar = (RelativeLayout) findViewById(R.id.title_bar);
        rlTitleBar.setBackgroundColor(getResources().getColor(R.color.blue_color));
        ivBack = (ImageView) findViewById(R.id.iv_back);
        lvOrder = (ListView) findViewById(R.id.lv_order);
        tvDistributionCost = (TextView) findViewById(R.id.tv_distribution_cost);
        tvTotalCost = (TextView) findViewById(R.id.tv_total_cost);
        tvCost = (TextView) findViewById(R.id.tv_cost);
        tvPayment = (TextView) findViewById(R.id.tv_payment);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tvPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(OrderActivity.this, R.style.Dialog_Style);
                dialog.setContentView(R.layout.qr_code);
                dialog.show();
            }
        });
    }
    private void setData() {
        orderAdapter =new OrderAdapter(this);
        lvOrder.setAdapter(orderAdapter);
        orderAdapter.setData(carFoodList);
        tvCost.setText("￥"+money);
        tvDistributionCost.setText("￥"+distributionCost);
        tvTotalCost.setText("￥"+(money.add(distributionCost)));
    }
}