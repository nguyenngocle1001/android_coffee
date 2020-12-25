package com.myclass.shopcoffee;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.PopupWindow;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(Html.fromHtml("<span style='color: #ffffff'>Danh Mục<span>", Html.FROM_HTML_MODE_COMPACT));
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.back_icon);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void SelectTable(View view){
        Intent intent = new Intent(this, activity_choose_table.class);
        startActivity(intent);
    }

    public void AddUser(View view){
        Intent intent = new Intent(this, AddUserActivity.class);
        startActivity(intent);
    }
    public void Payment(View view){
        Intent intent = new Intent(this, activity_choose_table.class);
        startActivity(intent);
    }
}