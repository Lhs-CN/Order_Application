package com.example.orderapplication.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.example.orderapplication.R;
import com.example.orderapplication.adapter.ShopAdapter;
import com.example.orderapplication.bean.ShopBean;
import com.example.orderapplication.utils.Constants;
import com.example.orderapplication.utils.JsonParse;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    public TextView tvTitle;
    public ListView lvShopList;
    public ShopAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initData();
    }

    private void initData() {
        OkHttpClient client=new OkHttpClient();

//        Request request=new Request.Builder().url(Constants.WEB_SITE+Constants.REQUEST_SHOP_DATA).build();
        Request request=new Request.Builder().url("http://192.168.254.1:3000/order/"+Constants.REQUEST_SHOP_DATA).build();
        Call call=client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json=response.body().string();
                final List<ShopBean> list= JsonParse.getInstance().getShopList(json);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.setData(list);
                    }
                });

            }
        });
    }

    private void initView() {
        tvTitle=findViewById(R.id.tv_title);
        lvShopList=findViewById(R.id.lv_shop_list);
        adapter=new ShopAdapter(this);
        lvShopList.setAdapter(adapter);
    }
}