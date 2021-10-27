package com.example.orderapplication.bean;


import static androidx.constraintlayout.motion.utils.Oscillator.TAG;

import android.util.Log;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class ShopBean implements Serializable {


    private int id;
    private String shopName;
    private int saleNum;
    private BigDecimal offerPrice;
    private int distributionCost;
    private String welfare;
    private String time;
    private String shopPic;
    private String shopNotice;
    private List<FoodBean> foodList;

    public List<FoodBean> getFoodList() {
        return foodList;
    }

    public void setFoodList(List<FoodBean> foodList) {
        this.foodList = foodList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public int getSaleNum() {
        return saleNum;
    }

    public void setSaleNum(int saleNum) {
        this.saleNum = saleNum;
    }

    public BigDecimal getOfferPrice() {
        return offerPrice;
    }

    public void setOfferPrice(BigDecimal offerPrice) {
        this.offerPrice = offerPrice;
    }

    public int getDistributionCost() {
        return distributionCost;
    }

    public void setDistributionCost(int distributionCost) {
        this.distributionCost = distributionCost;
    }

    public String getWelfare() {
        return welfare;
    }

    public void setWelfare(String welfare) {
        this.welfare = welfare;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getShopPic() {
        Log.i(TAG, "getShopPic: "+shopPic);
        return shopPic;
    }

    public void setShopPic(String shopPic) {
        this.shopPic = shopPic;
    }

    public String getShopNotice() {
        return shopNotice;
    }

    public void setShopNotice(String shopNotice) {
        this.shopNotice = shopNotice;
    }


}
