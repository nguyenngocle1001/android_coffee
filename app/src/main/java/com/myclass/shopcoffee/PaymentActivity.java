package com.myclass.shopcoffee;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class PaymentActivity extends AppCompatActivity {
    public static int idTable;
    public static int idBill;
    TextView layoutHeader, sumvalue;
    ListView mylist;

    ArrayList<Pay> listPay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(Html.fromHtml("<span style='color: #ffffff'>Thanh toán<span>", Html.FROM_HTML_MODE_COMPACT));
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.back_icon);


        layoutHeader = (TextView) findViewById(R.id.layoutHeader);
        layoutHeader.setText("HÓA ĐƠN BÀN SỐ: " + idTable);

        listPay = getListPay();

        sumvalue = (TextView) findViewById(R.id.sumvalue);
        sumvalue.setText(sum(listPay) + "");

        PayAdapter ad = new PayAdapter(listPay);

        mylist = findViewById(R.id.mylist);
        mylist.setAdapter(ad);

    }

    public void Deal(View view){
        ContentValues contentValues = new ContentValues();
        contentValues.put("Table_Status", 1);
        MainActivity._DATABASE.update(Database.getInstance().TBTABLES, contentValues, "Table_Id = ?", new String[]{idTable+""});

        contentValues.clear();
        contentValues.put("Bill_Status", 1);
        MainActivity._DATABASE.update(Database.getInstance().TBBILLS, contentValues, "Bill_Id = ?", new String[]{idBill+""});

        Toast.makeText(this, "Đã thanh toán", Toast.LENGTH_SHORT).show();
    }

    private double sum(ArrayList<Pay> listPay) {
        double result = 0;
        for (Pay pay : listPay) {
            result += pay.getProduct_price() * pay.getQuanity();
        }
        return  result;
    }

    private ArrayList<Pay> getListPay() {
        ArrayList<BillDetail> billDetails = new ArrayList<BillDetail>();

        Cursor cursor = MainActivity._DATABASE.
                query(Database.getInstance().TBBILLDETAILS, null, "Bill_Id=?", new String[]{idBill + ""},
                        null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            billDetails.add(new BillDetail(cursor.getInt(1), cursor.getInt(2)));
            cursor.moveToNext();
        }
        cursor.close();


        ArrayList<Products> products = new ArrayList<Products>();

        Cursor cursorProduct = MainActivity._DATABASE.
                query(Database.getInstance().TBPRODUCTS, null, null, null, null, null, null);
        cursorProduct.moveToFirst();
        while (!cursorProduct.isAfterLast()) {
            products.add(new Products(cursorProduct.getInt(0), cursorProduct.getString(1), cursorProduct.getFloat(2)));
            cursorProduct.moveToNext();
        }
        cursorProduct.close();

        ArrayList<Pay> listBillPay = new ArrayList<Pay>();

        for (BillDetail billDetail : billDetails) {
            for (Products product : products) {
                if (billDetail.getProduct_Id() == product.getProduct_Id()) {
                    Pay pay = new Pay(product.getProduct_Name(), product.getProduct_Price(), billDetail.getQuantity());
                    listBillPay.add(pay);
                }
            }
        }
        return listBillPay;

    }

}