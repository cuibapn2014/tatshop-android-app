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

import com.example.tatshop.R;
import com.example.tatshop.controller.CartController;
import com.example.tatshop.model.Product;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

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

    private void setAmount(View convertView, EditText mAmount) {
        Button btnUp = (Button) convertView.findViewById(R.id.btn_up);
        Button btnDown = (Button) convertView.findViewById(R.id.btn_down);
        amount = Integer.parseInt(String.valueOf(mAmount.getText()));

        btnUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                amount++;
                mAmount.setText(String.valueOf(amount));
            }
        });

        btnDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (amount > 1)
                    amount--;
                mAmount.setText(String.valueOf(amount));
            }
        });
    }

    private void setData(View convertView, int position) {
        ImageView imageView = (ImageView) convertView.findViewById(R.id.img_product);
        TextView txtTitle = (TextView) convertView.findViewById(R.id.product_name);
        TextView txtAttr = (TextView) convertView.findViewById(R.id.attr_item);
        TextView price = (TextView) convertView.findViewById(R.id.price);
        EditText mAmount = (EditText) convertView.findViewById(R.id.amount_order);
        TextView txtTotal = (TextView) convertView.findViewById(R.id.total_price);

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

        String priceTxt = CartController.nf.format(product.getPrice());
        price.setText(priceTxt);

        setAmount(convertView, mAmount);
    }
}
