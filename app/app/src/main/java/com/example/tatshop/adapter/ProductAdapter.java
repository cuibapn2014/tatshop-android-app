package com.example.tatshop.adapter;

import android.app.Activity;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.tatshop.R;
import com.example.tatshop.controller.CartController;
import com.example.tatshop.model.Product;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProductAdapter extends BaseAdapter {
    private Activity context;
    private List<Product> list;
    private int layoutId;

    public ProductAdapter(Activity context, List<Product> list, int layoutId) {
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
        ImageView imageView = (ImageView) convertView.findViewById(R.id.img_product);
        TextView textView = (TextView) convertView.findViewById(R.id.product_name);
        TextView price = (TextView) convertView.findViewById(R.id.product_price);

        final Product product = list.get(position);
        float pDiscount = 1 - (float) product.getDiscount() / 100;

        price.setText(CartController.nf.format(product.getPrice() * pDiscount));
        Picasso.get().load(product.getThumbnail()).into(imageView);
        textView.setText(product.getTitle());
        isDiscount(product, convertView);
        return convertView;
    }

    public void isDiscount(Product product, View v) {
        TextView discount = (TextView) v.findViewById(R.id.txtDiscount);
        TextView discard = (TextView) v.findViewById(R.id.original_price);
        if (product.getDiscount() > 0) {
            discount.setText("-" + String.valueOf(product.getDiscount()) + "%");
            discard.setPaintFlags(discard.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            discard.setText(CartController.nf.format(product.getPrice()));
        } else {
            RelativeLayout layout = (RelativeLayout) v.findViewById(R.id.rl);
            LinearLayout lnLayout = (LinearLayout) v.findViewById(R.id.ln);
            layout.removeView(discount);
            lnLayout.removeView(discard);
        }
    }
}
