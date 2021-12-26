package com.example.tatshop.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tatshop.R;
import com.example.tatshop.controller.CartController;
import com.example.tatshop.model.Item;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ItemBoughtAdapter extends BaseAdapter {
    private Activity context;
    private List<Item> list;
    private int layoutId;

    public ItemBoughtAdapter(Activity context, List<Item> list, int layoutId) {
        this.context = context;
        this.list = list;
        this.layoutId = layoutId;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = this.context.getLayoutInflater();
        convertView = layoutInflater.inflate(this.layoutId, null);
        setData(convertView, position);

        return convertView;
    }


    private void setData(View convertView, int position) {
        ImageView imageView = (ImageView) convertView.findViewById(R.id.img_product);
        TextView txtTitle = (TextView) convertView.findViewById(R.id.product_name);
        TextView txtAttr = (TextView) convertView.findViewById(R.id.attr_item);
        TextView price = (TextView) convertView.findViewById(R.id.price);
        TextView qty = (TextView) convertView.findViewById(R.id.qty);

        final Item item = list.get(position);
        qty.setText(String.valueOf(item.getQty()));
        Picasso.get().load(item.getImage()).into(imageView);
        txtTitle.setText(item.getName());
        txtAttr.setText(item.getAttr());
        price.setText(CartController.nf.format(item.getPrice()));

    }
}
