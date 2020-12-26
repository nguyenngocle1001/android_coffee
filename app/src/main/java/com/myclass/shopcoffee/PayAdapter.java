package com.myclass.shopcoffee;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class PayAdapter extends BaseAdapter {
    ArrayList<Pay> pays = new ArrayList<Pay>();
    public PayAdapter(ArrayList<Pay> pays){
        this.pays=pays;
    }
    @Override
    public int getCount() {
        return pays.size();
    }

    @Override
    public Pay getItem(int position) {
        return pays.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View viewpay;
        if(convertView==null){
            viewpay = View.inflate(parent.getContext(),R.layout.paylist,null);
        }
        else
            viewpay = convertView;
        Pay p = getItem(position);
        ((TextView) viewpay.findViewById(R.id.titlevalue)).setText(p.product_name);
        ((TextView) viewpay.findViewById(R.id.pricevalue)).setText(Float.toString(p.product_price));
        ((TextView) viewpay.findViewById(R.id.quantityvalue)).setText(Integer.toString(p.quanity));
        return viewpay;
    }
}
