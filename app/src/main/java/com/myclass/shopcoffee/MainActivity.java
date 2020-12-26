package com.myclass.shopcoffee;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    public static SQLiteDatabase _DATABASE;

    CheckBox cbPassword;
    EditText edtPassword, edtUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        cbPassword = (CheckBox) findViewById(R.id.cbShowPassword);
        edtPassword = (EditText) findViewById(R.id.edtPassword);
        edtUsername = (EditText) findViewById(R.id.edtUsername);

        _DATABASE = getDatabase();
    }

    public void showPassword(View view) {
        if (cbPassword.isChecked()) {
            edtPassword.setTransformationMethod(null);
            edtPassword.setSelection(edtPassword.getText().length());
        } else {
            edtPassword.setTransformationMethod(new PasswordTransformationMethod());
            edtPassword.setSelection(edtPassword.getText().length());
        }
    }

    public void login(View view) {
        if (isLogin(edtUsername.getText().toString(), edtPassword.getText().toString())) {
            Toast.makeText(this, "Login successfuly ", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(this, activity_choose_table.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Sai thông tin vui lòng kiểm tra lại", Toast.LENGTH_SHORT).show();
            edtUsername.setFocusable(true);
        }

    }

    public SQLiteDatabase getDatabase() {
        SQLiteDatabase database = null;
        try {
            database = openOrCreateDatabase(Database.getInstance()._DBNAME, MODE_PRIVATE, null);
            if (isTableExists(database, Database.getInstance().TBUSERS)) return database;
            database.setLocale(Locale.getDefault());
            database.setVersion(1);

            database.execSQL(Database.getInstance().scriptTableUsers());
            ContentValues contentValues = new ContentValues();
            contentValues.put("User_Name", "lexe");
            contentValues.put("User_Pass", "123456789");
            database.insert(Database.getInstance().TBUSERS, null, contentValues);
            contentValues.clear();
            contentValues.put("User_Name", "admin");
            contentValues.put("User_Pass", "123456789");
            database.insert(Database.getInstance().TBUSERS, null, contentValues);
            contentValues.clear();

            database.execSQL(Database.getInstance().scriptTableTables());
            for (int i = 1; i <= 10; i++) {
                contentValues.put("Table_Status", 0);
                database.insert(Database.getInstance().TBTABLES, null, contentValues);
                contentValues.clear();
            }

            Toast.makeText(this, "Load successfuly", Toast.LENGTH_SHORT).show();

            database.execSQL(Database.getInstance().scriptTableCategorys());
            contentValues.put("Category_Name","Thức uống");
            database.insert(Database.getInstance().TBCATEGORYS, null, contentValues);
            contentValues.clear();
            contentValues.put("Category_Name","Thức ăn");
            database.insert(Database.getInstance().TBCATEGORYS, null, contentValues);
            contentValues.clear();


            database.execSQL(Database.getInstance().scriptTableProducts());
            contentValues.put("Product_Name","BẠC SỈU");
            contentValues.put("Product_Price", 32000);
            contentValues.put("Cate", 1);
            contentValues.put("Product_Image","bacsiu");
            database.insert(Database.getInstance().TBPRODUCTS, null, contentValues);
            contentValues.clear();

//            contentValues.put("Product_Name","CÀ PHÊ ĐEN");
//            contentValues.put("Product_Price",32000);
//            contentValues.put("Cate_id",1);
//            contentValues.put("Product_Image",R.drawable.cfden);
//
//            database.insert(Database.getInstance().TBPRODUCTS, null, contentValues);
//            contentValues.clear();
//
//            contentValues.put("Product_Name","CÀ PHÊ SỮA ĐÁ");
//            contentValues.put("Product_Price",32000);
//            contentValues.put("Product_Image",R.drawable.cfsuada);
//            contentValues.put("Cate_id",1);
//            database.insert(Database.getInstance().TBPRODUCTS, null, contentValues);
//            contentValues.clear();
//
//            contentValues.put("Product_Name","CAPPUCCINO");
//            contentValues.put("Product_Price",45000);
//            contentValues.put("Product_Image",R.drawable.cappuccino);
//            contentValues.put("Cate_id",1);
//            database.insert(Database.getInstance().TBPRODUCTS, null, contentValues);
//            contentValues.clear();
//
//            contentValues.put("Product_Name","CARAMEL MACCHIATO");
//            contentValues.put("Product_Price",55000);
//            contentValues.put("Product_Image",R.drawable.caramel);
//            contentValues.put("Cate_id",1);
//            database.insert(Database.getInstance().TBPRODUCTS, null, contentValues);
//            contentValues.clear();
//
//            contentValues.put("Product_Name","ESPRESSO");
//            contentValues.put("Product_Price",35000);
//            contentValues.put("Product_Image",R.drawable.espresso);
//            contentValues.put("Cate_id",1);
//            database.insert(Database.getInstance().TBPRODUCTS, null, contentValues);
//            contentValues.clear();
//
//            contentValues.put("Product_Name","LATTE");
//            contentValues.put("Product_Price",45000);
//            contentValues.put("Product_Image",R.drawable.latte);
//            contentValues.put("Cate_id",1);
//            database.insert(Database.getInstance().TBPRODUCTS, null, contentValues);
//            contentValues.clear();
//
//            contentValues.put("Product_Name","MOCHA");
//            contentValues.put("Product_Price",49000);
//            contentValues.put("Product_Image","R.drawable.mocha");
//            contentValues.put("Cate_id",1);
//            database.insert(Database.getInstance().TBPRODUCTS, null, contentValues);
//            contentValues.clear();
//
//            contentValues.put("Product_Name","BÁNH MÌ CHÀ BÔNG PHÔ MAI");
//            contentValues.put("Product_Price",32000);
//            contentValues.put("Product_Image","R.drawable.phomaichabong");
//            contentValues.put("Cate_id",2);
//            database.insert(Database.getInstance().TBPRODUCTS, null, contentValues);
//            contentValues.clear();
//
//            contentValues.put("Product_Name","BÁNH MÌ QUE");
//            contentValues.put("Product_Price",12000);
//            contentValues.put("Product_Image","R.drawable.banhmyque");
//            contentValues.put("Cate_id",2);
//            database.insert(Database.getInstance().TBPRODUCTS, null, contentValues);
//            contentValues.clear();
//
//            contentValues.put("Product_Name","BÔNG LAN TRỨNG MUỐI");
//            contentValues.put("Product_Price",29000);
//            contentValues.put("Product_Image","R.drawable.bonglan");
//            contentValues.put("Cate_id",2);
//            database.insert(Database.getInstance().TBPRODUCTS, null, contentValues);
//            contentValues.clear();
//
//            contentValues.put("Product_Name","CROISSANT TRỨNG MUỐI");
//            contentValues.put("Product_Price",35000);
//            contentValues.put("Product_Image","R.drawable.croissant");
//            contentValues.put("Cate_id",2);
//            database.insert(Database.getInstance().TBPRODUCTS, null, contentValues);
//            contentValues.clear();
//
//            contentValues.put("Product_Name","MOCHI KEM CHOCOLATE");
//            contentValues.put("Product_Price",19000);
//            contentValues.put("Product_Image","R.drawable.mochichoco");
//            contentValues.put("Cate_id",2);
//            database.insert(Database.getInstance().TBPRODUCTS, null, contentValues);
//            contentValues.clear();
//
//            contentValues.put("Product_Name","MOCHI KEM MATCHA");
//            contentValues.put("Product_Price",19000);
//            contentValues.put("Product_Image","R.drawable.mochimatcha");
//            contentValues.put("Cate_id",2);
//            database.insert(Database.getInstance().TBPRODUCTS, null, contentValues);
//            contentValues.clear();
//
//            contentValues.put("Product_Name","MOCHI KEM XOÀI");
//            contentValues.put("Product_Price",19000);
//            contentValues.put("Product_Image","R.drawable.mochimango");
//            contentValues.put("Cate_id",2);
//            database.insert(Database.getInstance().TBPRODUCTS, null, contentValues);
//            contentValues.clear();
//
//            contentValues.put("Product_Name","MOUSSE GẤU CHOCOLATE");
//            contentValues.put("Product_Price",39000);
//            contentValues.put("Product_Image","R.drawable.moussegau");
//            contentValues.put("Cate_id",2);
//            database.insert(Database.getInstance().TBPRODUCTS, null, contentValues);
//            contentValues.clear();
//
//
//            database.execSQL(Database.getInstance().scriptTableBills());
//            database.execSQL(Database.getInstance().scriptTableBillDetails());

        } catch (Exception e) {
            Toast.makeText(this, "Lỗi", Toast.LENGTH_SHORT).show();
        }
        return database;
    }

    public boolean isTableExists(SQLiteDatabase database, String tableName) {
        Cursor cursor = database.rawQuery("select DISTINCT tbl_name from sqlite_master where tbl_name = '" +
                tableName + "'", null);
        if (cursor != null) {
            if (cursor.getCount() > 0) {
                cursor.close();
                return true;
            }
            cursor.close();
        }
        return false;
    }

    public boolean isLogin(String username, String pass) {
        int count;
        Cursor cursor = _DATABASE.query(Database.getInstance().TBUSERS, null, "User_Name=? and User_Pass=?"
                , new String[]{username, pass}, null, null, null);
        count = cursor.getCount();
        cursor.close();
        return count > 0 ? true : false;
    }
}