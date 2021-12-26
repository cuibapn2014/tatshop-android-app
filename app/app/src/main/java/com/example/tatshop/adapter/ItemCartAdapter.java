package com.example.tatshop.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.aurelhubert.ahbottomnavigation.notification.AHNotification;
import com.example.tatshop.MainActivity;
import com.example.tatshop.R;
import com.example.tatshop.controller.CartController;
import com.example.tatshop.model.Product;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ItemCartAdapter extends BaseAdapter {
    private Activity context;
    private List<Product> list;
    private int layoutId;
    private int amount;

    public ItemCartAdapter(Activity context, List<Product> list, int layoutId) {
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

    private void setAmount(View convertView, EditText mAmount, int position) {
        TextView txtTotal = context.findViewById(R.id.total_price);
        Button btnUp = (Button) convertView.findViewById(R.id.btn_up);
        Button btnDown = (Button) convertView.findViewById(R.id.btn_down);
        btnUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                amount = Integer.parseInt(String.valueOf(mAmount.getText()));
                amount++;
                mAmount.setText(String.valueOf(amount));
                list.get(position).setQty(amount);
                list.set(position, list.get(position));
                CartController.updateCartItem(list.get(position), position);
                txtTotal.setText(CartController.nf.format(CartController.getTotalPrice()));
            }
        });

        btnDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                amount = Integer.parseInt(String.valueOf(mAmount.getText()));
                if (amount > 1)
                    amount--;
                mAmount.setText(String.valueOf(amount));
                list.get(position).setQty(amount);
                list.set(position, list.get(position));
                CartController.updateCartItem(list.get(position), position);
                txtTotal.setText(CartController.nf.format(CartController.getTotalPrice()));
            }
        });
    }

    private void setData(View convertView, int position) {
        ImageView imageView = (ImageView) convertView.findViewById(R.id.img_product);
        TextView txtTitle = (TextView) convertView.findViewById(R.id.product_name);
        TextView txtAttr = (TextView) convertView.findViewById(R.id.attr_item);
        TextView price = (TextView) convertView.findViewById(R.id.price);
        EditText mAmount = (EditText) convertView.findViewById(R.id.amount_order);
        TextView btnRemoveItem = (TextView) convertView.findViewById(R.id.btn_remove_item);
        btnRemoveItem.setTag(position);
        mAmount.setTag(position);

        final Product product = list.get(position);
        String strAttr = null;
        String color = "Màu: " + product.getAttr().get(1).getValue();
        String size = "Size: " + product.getAttr().get(0).getValue();
        String qty = String.valueOf(product.getQty());
        Picasso.get().load(product.getThumbnail()).into(imageView);
        txtTitle.setText(product.getTitle());
        strAttr = size + " - " + color;
        txtAttr.setText(strAttr);
        mAmount.setText(qty);
        float discount = 1 - (float) product.getDiscount() / 100;
        String priceTxt = CartController.nf.format(product.getPrice() * discount);
        price.setText(priceTxt);

        setAmount(convertView, mAmount, position);
        setRemoveItem(convertView, btnRemoveItem, position);
    }

    private void setRemoveItem(View convertView, TextView btnRemoveItem, int position) {
        btnRemoveItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView txtTotal = context.findViewById(R.id.total_price);
                CartController.removeCartItem((Product)getItem(position));
                ItemCartAdapter.this.notifyDataSetChanged();
                txtTotal.setText(CartController.nf.format(CartController.getTotalPrice()));
                changeData();
            }
        });
    }

    public void changeData() {
        int amountCartItem = CartController.getListCartItem().size();
        AHNotification notification = new AHNotification.Builder()
                .setText(String.valueOf(amountCartItem))
                .setBackgroundColor(ContextCompat.getColor(context, R.color.colorBottomNavigationNotification))
                .setTextColor(ContextCompat.getColor(context, R.color.white))
                .build();
        MainActivity.bottomNavigationView.setNotification(notification, 2);
    }
}
