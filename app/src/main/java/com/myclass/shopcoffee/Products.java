package com.myclass.shopcoffee;

public class Products {
    int Product_Id;
    String Product_Name;
    float Product_Price;

    public int getProduct_Id() {
        return Product_Id;
    }

    public void setProduct_Id(int product_Id) {
        Product_Id = product_Id;
    }

    public String getProduct_Name() {
        return Product_Name;
    }

    public void setProduct_Name(String product_Name) {
        Product_Name = product_Name;
    }

    public float getProduct_Price() {
        return Product_Price;
    }

    public void setProduct_Price(float product_Price) {
        Product_Price = product_Price;
    }

    public Products(int product_Id, String product_Name, float product_Price) {
        Product_Id = product_Id;
        Product_Name = product_Name;
        Product_Price = product_Price;
    }
}
