package com.myclass.shopcoffee;

import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class TableAdapter extends BaseAdapter {
    private ArrayList<Table> arrayList;

    public TableAdapter(ArrayList<Table> arrayList) {
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View viewProduct;
        if (convertView == null) {
            viewProduct = View.inflate(parent.getContext(), R.layout.table_item, null);
        } else viewProduct = convertView;
        Table table = (Table) getItem(position);
        TextView textView = (TextView) viewProduct.findViewById(R.id.idTable);
        if (table.status)
            textView.setTextColor(Color.RED);
        else textView.setTextColor(Color.WHITE);
        textView.setText(table.id + "");
        return viewProduct;
    }
}

