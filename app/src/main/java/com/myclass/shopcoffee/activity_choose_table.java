package com.myclass.shopcoffee;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;

public class activity_choose_table extends AppCompatActivity {

    private GridView gridView;
    private RadioGroup rgFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_table);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(Html.fromHtml("<span style='color: #ffffff'>Chọn bàn<span>", Html.FROM_HTML_MODE_COMPACT));
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.back_icon);
        //actionBar.setBackgroundDrawable(new ColorDrawable(Color.BLACK));

        gridView = (GridView) findViewById(R.id.gridView);
        loadList("SELECT * FROM " + Database.getInstance().TBTABLES);

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

    public ArrayList<Table> loadTable(String query) {
        ArrayList<Table> listTable = new ArrayList<>();
        Cursor cursor = MainActivity._DATABASE.rawQuery(query, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            listTable.add(new Table(cursor.getInt(0), cursor.getInt(1)));
            cursor.moveToNext();
        }
        cursor.close();
        return listTable;
    }

    public void Filter(View view) {
        Toast.makeText(this, rgFilter.getCheckedRadioButtonId() + "", Toast.LENGTH_SHORT).show();
        String query = "";
        switch (rgFilter.getCheckedRadioButtonId()){
            case R.id.rbNull:
                query = "SELECT * FROM " + Database.getInstance().TBTABLES +  " WHERE Table_Status = 0";
                break;
            case R.id.rbNotNull:
                query = "SELECT * FROM " + Database.getInstance().TBTABLES +  " WHERE Table_Status = 1";
                break;
            default:
                query = "SELECT * FROM " + Database.getInstance().TBTABLES;
                break;
        }
        loadList(query);
    }

    public void loadList(String query){
        ArrayList<Table> listTable = loadTable(query);
        TableAdapter tableAdapter = new TableAdapter(listTable);
        gridView.setAdapter(tableAdapter);
    }
}