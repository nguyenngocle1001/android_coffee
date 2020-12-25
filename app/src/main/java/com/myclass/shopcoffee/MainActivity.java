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

            Intent intent = new Intent(this, MenuActivity.class);
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

//            database.execSQL(Database.getInstance().scriptTableCategorys());
//            database.execSQL(Database.getInstance().scriptTableProducts());
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