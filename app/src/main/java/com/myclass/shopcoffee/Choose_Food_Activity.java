package com.myclass.shopcoffee;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Html;
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
import java.util.List;

public class Choose_Food_Activity extends AppCompatActivity implements FoodInterFace{

    RecyclerView myRecyclerView;
    Spinner mySpinner;
    FoodAdapter foodAdapter;
    ArrayList<String> categories = new ArrayList<String>();

    TextView textView;
    Button buttonAdd;
    Button buttonPay;
    int idBill;
    ArrayList<Food> selectedFood;


    public static int idTable;
    public static int statusTable;

    private void initializeViews() {
        categories.add("Tất cả");
        categories.add("Nước");
        categories.add("Thức ăn");
        Toast.makeText(this, "id table: "+idTable, Toast.LENGTH_SHORT).show();
        textView.setText("Bàn số " +idTable);
        mySpinner = findViewById(R.id.spinner1);
        mySpinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, categories));

        myRecyclerView = findViewById(R.id.myrecycler);
        foodAdapter = new FoodAdapter(getFoodBodies(),this);
        myRecyclerView.setAdapter(foodAdapter);

        //spinner selection events
        mySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long itemID) {
                if (position >= 0 && position < categories.size()) {
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
            f.setPrice(cursor.getFloat(2));
            f.setCategoryID(cursor.getInt(3));
            f.setImage(cursor.getInt(4));
            data.add(f);
            cursor.moveToNext();
        }
        cursor.close();

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

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(Html.fromHtml("<span style='color: #ffffff'>Chọn món<span>", Html.FROM_HTML_MODE_COMPACT));
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.back_icon);


        RecyclerView myRecyclerView = findViewById(R.id.myrecycler);
        textView = findViewById(R.id.layoutHeader);
        buttonAdd = findViewById(R.id.button1);
        buttonPay = findViewById(R.id.pay_button);
        initializeViews();

//        myRecyclerView = findViewById(R.id.myrecycler);
//        foodAdapter = new FoodAdapter(getFoodBodies(),this);
//        myRecyclerView.setAdapter(foodAdapter);
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedFood = foodAdapter.getSelectedFood();
                StringBuilder FoodNames = new StringBuilder();
                for (int i=0; i<selectedFood.size();i++){
                    if(i==0){
                        FoodNames.append(selectedFood.get(i).getName());
                    }
                    else{
                        FoodNames.append("\n").append(selectedFood.get(i).getName());
                    }
                }
                insertToBill();
                Toast.makeText(Choose_Food_Activity.this, FoodNames.toString(),Toast.LENGTH_SHORT).show();
            }
        });

        buttonPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(idBill!=0){
                    Intent intent = new Intent(Choose_Food_Activity.this, PaymentActivity.class);
                    PaymentActivity.idTable = idTable;
                    PaymentActivity.idBill = idBill;
                    startActivity(intent);
                }
                else{
                    Toast.makeText(Choose_Food_Activity.this, "Chưa chọn món", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
    private void insertToBill(){

        ContentValues contentValues = new ContentValues();
        if(TableStatus()) {
            contentValues.put("Table_Id", idTable);
            contentValues.put("Bill_Status", 0);
            long count = MainActivity._DATABASE.insert(Database.getInstance().TBBILLS, null , contentValues);
            idBill = getBillId();

            contentValues.clear();
            contentValues.put("Table_Status", 1);
            MainActivity._DATABASE.insert(Database.getInstance().TBTABLES, null , contentValues);

            for(Food food : selectedFood){
                contentValues.clear();
                contentValues.put("Bill_Id", idTable);
                contentValues.put("Product_Id", food.getId());
                contentValues.put("Quantity", 1);
                MainActivity._DATABASE.insert(Database.getInstance().TBBILLDETAILS, null , contentValues);
            }
        }else {
            ArrayList<Food> listFood = new ArrayList<>();
            idBill = getBillId();
            Cursor cursor = MainActivity._DATABASE.query(Database.getInstance().TBBILLDETAILS,
                    null, "Table_Id = ? and Bill_Id = ?", new String[]{idTable+"", idBill+""}, null, null, null);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()){
                int quantity = cursor.getInt(2);
                for(Food food : selectedFood){
                    if(food.getId() == cursor.getInt(1)){
                        contentValues.clear();
                        contentValues.put("Quantity", quantity + 1);
                        MainActivity._DATABASE.update(Database.getInstance().TBBILLDETAILS, contentValues, "Bill_Id =? and Product_Id = ?",
                                new String[]{idBill+"", food.getId() + ""});
                        selectedFood.remove(food);
                    }
                }
                cursor.moveToNext();
            }
            for (Food food : selectedFood) {
                contentValues.clear();
                contentValues.put("Bill_Id", idTable);
                contentValues.put("Product_Id", food.getId());
                contentValues.put("Quantity", 1);
                MainActivity._DATABASE.insert(Database.getInstance().TBBILLDETAILS, null, contentValues);
            }

        }



//        ArrayList<Integer> id = new ArrayList<Integer>();
//        Cursor cursor;
//        cursor = MainActivity._DATABASE.query(Database.getInstance().TBBILLS,
//                null, null, null, null, null, null);
//        cursor.moveToFirst();
//        while (!cursor.isAfterLast()) {
//            id.add(cursor.getInt(0));
//            cursor.moveToNext();
//        }
//        cursor.close();
//        ContentValues cv = new ContentValues();
//        cv.put("Quantity", );
//        MainActivity._DATABASE.update(Database.getInstance().TBBILLDETAILS, )
    }

    private int getBillId(){
        Cursor cursor = MainActivity._DATABASE.query(Database.getInstance().TBBILLS, null, "Table_Id = ? AND Bill_Status =? ", new  String[]{idTable + "", "0"}, null, null, null);
        cursor.moveToFirst();
        int idBill = cursor.getInt(0);
        cursor.close();
        return  idBill;
    }

    private boolean TableStatus(){
        Cursor cursor = MainActivity._DATABASE.query(Database.getInstance().TBTABLES, null, "Table_Id =?", new String[]{idTable+""}, null, null, null);
        cursor.moveToFirst();
        int status = cursor.getInt(1);
        return  status == 0 ? true : false;
    }

    @Override
    public void onFoodAction(Boolean isSelected) {
        if(isSelected){
            buttonAdd.setVisibility(View.VISIBLE);
        }
        else{
            buttonAdd.setVisibility(View.GONE);
        }
    }
}