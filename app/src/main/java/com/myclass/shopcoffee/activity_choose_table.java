package com.myclass.shopcoffee;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class activity_choose_table extends AppCompatActivity {

    private GridView gridView;
    private RadioGroup rgFilter;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_table);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(Html.fromHtml("<span style='color: #ffffff'>Chọn bàn<span>", Html.FROM_HTML_MODE_COMPACT));
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.back_icon);
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.BLACK));

        ArrayList<Table> listTable = loadTable();

        TableAdapter tableAdapter = new TableAdapter(listTable);

        gridView = (GridView) findViewById(R.id.gridView);
        gridView.setAdapter(tableAdapter);


        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                final Intent intent;
                intent = new Intent(activity_choose_table.this, Choose_Food_Activity.class);
                Choose_Food_Activity.idTable = listTable.get(position).id;
                startActivity(intent);

            }
        });
        rgFilter = (RadioGroup) findViewById(R.id.rgFilter);
        rgFilter.check(R.id.rbAll);
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

    public ArrayList<Table> loadTable() {
        ArrayList<Table> listTable = new ArrayList<>();
        Cursor cursor = MainActivity._DATABASE.query(Database.getInstance().TBTABLES, null, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            listTable.add(new Table(cursor.getInt(0), cursor.getInt(1)));
            cursor.moveToNext();
        }
        cursor.close();
        return listTable;
    }

    public void Filter(View view) {

    }
}