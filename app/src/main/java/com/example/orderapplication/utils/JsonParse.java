package com.example.orderapplication.utils;

import com.example.orderapplication.bean.ShopBean;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class JsonParse {
    private static JsonParse instance;
    private JsonParse(){

    }
    public synchronized static JsonParse getInstance(){
        if(instance==null){
            instance = new JsonParse();
        }
        return instance;
    }
    public List<ShopBean> getShopList(String json){
        Type listType=new TypeToken<List<ShopBean>>(){}.getType();
        return new Gson().fromJson(json,listType);
    }

}
