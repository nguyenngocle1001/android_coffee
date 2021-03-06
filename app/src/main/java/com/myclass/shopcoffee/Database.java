package com.myclass.shopcoffee;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.util.ArrayList;;

public class Database {

    private static Database instane = null;

    public static Database getInstance() {
        if (instane == null) instane = new Database();
        return instane;
    }

    public final String _DBNAME = "FoodandDrink.db";

    public final String TBUSERS = "Users";
    public final String TBTABLES = "Tables";
    public final String TBCATEGORYS = "Categorys";
    public final String TBPRODUCTS = "Products";
    public final String TBBILLS = "Bills";
    public final String TBBILLDETAILS = "Bill_Details";


    public String scriptTableUsers() {
        return "create table if not exists "+TBUSERS+" (User_Id integer primary key autoincrement, User_Name text unique, User_Pass text)";
    }

    public String scriptTableTables() {
        return "CREATE TABLE IF NOT EXISTS " + TBTABLES + "(Table_Id integer primary key autoincrement, Table_Status bit)";
    }

    public String scriptTableCategorys() {
        return "CREATE TABLE IF NOT EXISTS " + TBCATEGORYS + "(" +
                "Category_Id integer primary key autoincrement," +
                "Category_Name text" +
                ")";
    }

    public String scriptTableProducts() {
        return "CREATE TABLE IF NOT EXISTS " + TBPRODUCTS + "(" +
                "Product_Id integer primary key autoincrement," +
                "Product_Name text," +
                "Product_Price double," +
                "Product_Description text," +
                "Product_Image text" +
                ")";
    }

    public String scriptTableBills() {
        return "CREATE TABLE IF NOT EXISTS " + TBBILLS + "(" +
                "Bill_Id integer primary key autoincrement," +
                "Table_Id integer references Tables(Table_Id)," +
                "Bill_Status bit," +
                "Bill_Price double)";
    }

    public String scriptTableBillDetails() {
        return "CREATE TABLE IF NOT EXISTS " + TBBILLDETAILS + "(" +
                "Bill_Id integer references Bills(Bill_Id)," +
                "Product_Id integer references Products(Product_Id)," +
                "Quantity integer default 1," +
                "Primary key(Bill_Id, Product_Id)" +
                ")";
    }

    public void count(SQLiteDatabase database, String tableName, Context context) {
        int sl = 0;
        Cursor cursor = database.rawQuery("select * from " + tableName, null);
        sl = cursor.getCount();
        Toast.makeText(context, sl + "", Toast.LENGTH_SHORT).show();
    }
}


