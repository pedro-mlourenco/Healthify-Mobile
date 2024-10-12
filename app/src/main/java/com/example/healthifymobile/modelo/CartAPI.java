package com.example.healthifymobile.modelo;

public class CartAPI {
    private int id,userprofilesid,mealsid,itemquantity;
    private String sellingprice, state;

    public CartAPI(int id, int userprofilesid, int mealsid, int itemquantity, String sellingprice, String state) {
        this.id = id;
        this.userprofilesid = userprofilesid;
        this.mealsid = mealsid;
        this.itemquantity = itemquantity;
        this.sellingprice = sellingprice;
        this.state = state;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserprofilesid() {
        return userprofilesid;
    }

    public void setUserprofilesid(int userprofilesid) {
        this.userprofilesid = userprofilesid;
    }

    public int getMealsid() {
        return mealsid;
    }

    public void setMealsid(int mealsid) {
        this.mealsid = mealsid;
    }

    public int getItemquantity() {
        return itemquantity;
    }

    public void setItemquantity(int itemquantity) {
        this.itemquantity = itemquantity;
    }

    public String getSellingprice() {
        return sellingprice;
    }

    public void setSellingprice(String sellingprice) {
        this.sellingprice = sellingprice;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
