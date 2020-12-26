package com.myclass.shopcoffee;

public class Food {
    private int id;
    private String name;
    private float price;
    private int categoryID;
    private int image;

    public Boolean isSelect = false;


    public int getId(){
        return id;
    }
    public String getName() {
        return name;
    }
    public float getPrice() {
        return price;
    }
    public int getCategotuID() {
        return categoryID;
    }
    public  int getImage(){
        return image;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public Food(){
    }

    public Food(int id, String name, float price, int categotyId, int image){
        this.id=id;
        this.name=name;
        this.price=price;
        this.categoryID=categotyId;
        this.image=image;
    }
    @Override
    public String toString() {
        return name;
    }
}
