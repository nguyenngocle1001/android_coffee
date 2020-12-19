package com.myclass.shopcoffee;

public class Table {
    int id;
    boolean status;
    public Table(int id, int status){
        this.id = id;
        this.status = status == 1 ? true : false;
    }
}