package com.example.lmasi.hangsho;

import java.io.Serializable;

/**
 * Created by lmasi on 2016-09-18.
 */
public class ShopInfo implements Serializable{

    private static final long serialVersionUID = 1L;

    private String type;
    private String shopName;
    private String subName;

    public ShopInfo(String type, String shopName, String subName)
    {
        this.setShopName(shopName);
        this.setSubName(subName);
        this.setType(type);
    }

    public String getShopName() {
        return shopName;
    }

    public String getSubName() {
        return subName;
    }

    public String getType() {
        return type;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public void setSubName(String subName) {
        this.subName = subName;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object obj) {

        ShopInfo other;

        if(obj instanceof  ShopInfo)
            other = (ShopInfo)obj;
        else
            return false;

        return getType().equals(other.getType()) && getShopName().equals(other.getShopName()) && getSubName().equals(other.getSubName());
    }
}
