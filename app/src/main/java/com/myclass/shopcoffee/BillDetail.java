package com.myclass.shopcoffee;

public class BillDetail {
    int Product_Id;
    int Quantity;

    public int getProduct_Id() {
        return Product_Id;
    }

    public void setProduct_Id(int product_Id) {
        Product_Id = product_Id;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }

    public BillDetail(int product_Id, int quantity) {
        Product_Id = product_Id;
        Quantity = quantity;
    }
}
