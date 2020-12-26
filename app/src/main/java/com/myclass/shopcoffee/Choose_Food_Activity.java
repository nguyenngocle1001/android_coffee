package com.myclass.shopcoffee;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.internal.NavigationMenuView;

import java.util.ArrayList;

public class Choose_Food_Activity extends AppCompatActivity implements FoodInterFace{

    RecyclerView myRecyclerView;
    Spinner mySpinner;
    FoodAdapter foodAdapter;
    String[] categories={"Tất cả","Nước","Đồ ăn"};
    TextView textView;
    Button buttonAdd;

    public static int idTable;

    private void initializeViews() {
        Toast.makeText(this, "id table: "+idTable, Toast.LENGTH_SHORT).show();
        textView.setText("Bàn số " +idTable);
        mySpinner = findViewById(R.id.spinner1);
        mySpinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, categories));

        myRecyclerView = findViewById(R.id.myrecycler);
        foodAdapter = new FoodAdapter(getFoodBodies(),this);
        myRecyclerView.setAdapter(foodAdapter);

        //myListView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, getFoodBodies()));

        //
        //SQLiteDatabase db;
        //Cursor c = db.query("LUONG", null, "chucvu = 'tp' and luongchinh > 7000000", null, null, null, null);
        //


        //spinner selection events
        mySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long itemID) {
                if (position >= 0 && position < categories.length) {
                    getSelectedCategoryData(position);
                } else {
                    Toast.makeText(Choose_Food_Activity.this, "Selected Category Does not Exist!", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private ArrayList<Food> getFoodBodies() {
        ArrayList<Food> data = new ArrayList<>();
        data.clear();

        Cursor cursor;

        cursor = MainActivity._DATABASE.query(Database.getInstance().TBPRODUCTS,
                null, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Food f = new Food();
            f.setId(cursor.getInt(0));
            f.setName(cursor.getString(1));
            f.setPrice(cursor.getString(2));
            f.setCategoryID(cursor.getInt(3));
            f.setImage(cursor.getInt(4));
            data.add(f);
        }
        cursor.close();


//        String myText = "R.drawable.bacsiu";
//        int a = 0;
//        try {
//            a  = Integer.parseInt(myText);
//        } catch(NumberFormatException nfe) {
//            System.out.println("Could not parse " + nfe);
//        }
//        data.add(new Food(1,"BẠC SỈU", "32000", 1, R.drawable.bacsiu));
//        data.add(new Food(2,"CÀ PHÊ ĐEN", "32000", 1, R.drawable.cfden));
//        data.add(new Food(3,"CÀ PHÊ SỮA ĐÁ", "32000", 1, R.drawable.cfsuada));
//        data.add(new Food(4,"CAPPUCCINO", "45000", 1, R.drawable.cappuccino));
//        data.add(new Food(5,"CARAMEL MACCHIATO", "55000", 1, R.drawable.caramel));
//        data.add(new Food(6,"ESPRESSO", "35000", 1, R.drawable.espresso));
//        data.add(new Food(7,"LATTE", "45000", 1, R.drawable.latte));
//        data.add(new Food(8,"MOCHA", "49000", 1, R.drawable.mocha));
//        data.add(new Food(9,"BÁNH MÌ CHÀ BÔNG PHÔ MAI", "32000", 2, R.drawable.phomaichabong));
//        data.add(new Food(10,"BÁNH MÌ QUE", "12000", 2, R.drawable.banhmyque));
//        data.add(new Food(11,"BÔNG LAN TRỨNG MUỐI", "29000", 2, R.drawable.bonglan));
//        data.add(new Food(12,"CROISSANT TRỨNG MUỐI", "35000", 2, R.drawable.croissant));
//        data.add(new Food(13,"MOCHI KEM CHOCOLATE", "19000", 2, R.drawable.mochichoco));
//        data.add(new Food(14,"MOCHI KEM MATCHA", "19000", 2, R.drawable.mochimatcha));
//        data.add(new Food(15,"MOCHI KEM XOÀI", "19000", 2, R.drawable.mochimango));
//        data.add(new Food(16,"MOUSSE GẤU CHOCOLATE", "39000", 2, R.drawable.moussegau));



        return data;
    }

    private void getSelectedCategoryData(int categoryID) {
        //arraylist to hold selected cosmic bodies
        ArrayList<Food> foods = new ArrayList<>();
        if(categoryID == 0)
        {
            myRecyclerView = findViewById(R.id.myrecycler);

            foodAdapter = new FoodAdapter(getFoodBodies(),this);
            myRecyclerView.setAdapter(foodAdapter);
            foodAdapter = new FoodAdapter(getFoodBodies(),this);
        }else{
            //filter by id
            for (Food food : getFoodBodies()) {
                if (food.getCategotuID() == categoryID) {
                    foods.add(food);
                }
            }
            myRecyclerView = findViewById(R.id.myrecycler);
            foodAdapter = new FoodAdapter(foods,this);
            myRecyclerView.setAdapter(foodAdapter);
        }
//        set the adapter to GridView
        myRecyclerView.setAdapter(foodAdapter);
//        myListView.setAdapter(adapter);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose__food);

        RecyclerView myRecyclerView = findViewById(R.id.myrecycler);
        textView = findViewById(R.id.layoutHeader);
        buttonAdd = findViewById(R.id.button1);
        initializeViews();
    }

    @Override
    public void onFoodAction(Boolean isSelected) {

    }
}