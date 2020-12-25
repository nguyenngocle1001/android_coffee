package com.myclass.shopcoffee;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class DataCoffeeHelper extends SQLiteOpenHelper {

    private Context context;
    private static  final String DATEBASE_NAME="Coffeshop.db";
    private static final  int DATABASE_VERSION =1;

    private static final String TABLE_USER = "USERS";
    private static final String USER_ID = "user_id";
    private static final String USER_NAME = "user_name";
    private static final String USER_PASSWORD = "user_password";

    private static final String TABLE_TABLES = "TABLES";
    private static final String TABLE_ID = "table_id";
    private static final String TABLE_STATUS = "table_status";

    private static final String TABLE_CATEGORY = "CATEGORYS";
    private static final String CATE_ID = "cate_id";
    private static final String CATE_NAME = "cate_name";

    private static final String TABLE_PRODUCT = "PRODUCTS";
    private static final String PRODUCT_ID = "product_id";
    private static final String PRODUCT_NAME = "product_name";
    private static final String PRODUCT_PRICE = "product_price";
    private static final String PRODUCT_IMAGE = "product_image";
    private static final String PRODUCT_CATE_ID = "cate_id";

    private static final String TABLE_BILL = "BILLS";
    private static final String BILL_ID = "bill_id";
    private static final String BILL_TABLE_ID = "table_id";
    private static final String BILL_STATUS = "bill_status";
    private static final String BILL_PRICE = "bill_price";

    private static final String TABLE_BILL_DETAIL = "BILL_DETAILS";
    private static final String BILL_DETAIL_ID = "bill_id";
    private static final String BILL_DETAIL_PRODUCT_ID = "product_id";
    private static final String BILL_DETAIL_QUANTITY = "bill_detail_quantity";


    public DataCoffeeHelper(@Nullable Context context) {
        super(context, DATEBASE_NAME, null, DATABASE_VERSION);
        this.context=context;
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        String createUsers = "CREATE TABLE " + TABLE_USER +
                " ("+ USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                USER_NAME + " TEXT, " +
                USER_PASSWORD + " TEXT);";
        db.execSQL(createUsers);

        String createTables ="CREATE TABLE "+ TABLE_TABLES + " ("+ TABLE_ID +" INTEGER PRIMARY KEY AUTOINCREMENT,"+
                TABLE_STATUS+" INTEGER default 0);";
        db.execSQL(createTables);

        String createCategorys ="CREATE TABLE "+ TABLE_CATEGORY + " ("+ CATE_ID +" INTEGER PRIMARY KEY AUTOINCREMENT,"+
                CATE_NAME+" TEXT);";
        db.execSQL(createCategorys);

        String createProducts ="CREATE TABLE "+ TABLE_PRODUCT +" ("+PRODUCT_ID+"INTEGER PRIMARY KEY AUTOINCREMENT,"+
                PRODUCT_NAME+" TEXT,"+ PRODUCT_PRICE +" FLOAT," +
                PRODUCT_IMAGE+" TEXT,"+PRODUCT_CATE_ID+" INTEGER, FOREIGN KEY("+ PRODUCT_CATE_ID +") REFERENCES "
                +TABLE_CATEGORY +"("+CATE_ID+"));";
        db.execSQL(createProducts);

        String createBills ="CREATE TABLE "+ TABLE_BILL +" ("+BILL_ID+"INTEGER PRIMARY KEY AUTOINCREMENT,"+
                BILL_STATUS+" TEXT,"+
                BILL_PRICE+" TEXT,"+
                BILL_TABLE_ID+" INTEGER, FOREIGN KEY("+ BILL_TABLE_ID +") REFERENCES "
                +TABLE_TABLES +"("+TABLE_ID+"));";
        db.execSQL(createBills);

        String createBillDetails ="CREATE TABLE "+ TABLE_BILL_DETAIL + " ("+ BILL_DETAIL_ID  +" INTEGER,"+
                BILL_DETAIL_PRODUCT_ID+" INTEGER, PRIMARY KEY("+BILL_DETAIL_ID+","+BILL_DETAIL_PRODUCT_ID+") );";
        db.execSQL(createBillDetails);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TABLES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BILL);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BILL_DETAIL);
        onCreate(db);
    }
}
