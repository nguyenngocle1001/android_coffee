package com.myclass.shopcoffee;

public class Pay {
    String product_name;
    float product_price;
    int quanity;

    public String getProduct_name() {
        return product_name;
    }

    public float getProduct_price() {
        return product_price;
    }

    public int getQuanity() {
        return quanity;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public void setProduct_price(float product_price) {
        this.product_price = product_price;
    }

    public void setQuanity(int quanity) {
        this.quanity = quanity;
    }
    public Pay(){

    }

    public Pay(String product_name, float product_price, int quanity){
        this.product_name = product_name;
        this.product_price = product_price;
        this.quanity = quanity;
    }
}
