package com.example.tatshop.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tatshop.DetailProductActivity;
import com.example.tatshop.R;
import com.example.tatshop.controller.CartController;
import com.example.tatshop.model.Product;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProductCategoryAdapter extends RecyclerView.Adapter<ProductCategoryAdapter.ProductViewHolder>{
    private List<Product> listProduct;
    private Context context;

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_custom_grid, parent, false);

        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = listProduct.get(position);
        if (product == null)
            return;
        holder.productName.setText(product.getTitle());
        float price = product.getPrice() * (1 - (float) product.getDiscount() / 100);
        holder.productPrice.setText(CartController.nf.format(price));
        Picasso.get().load(product.getThumbnail()).into(holder.imageProduct);
        holder.originalPrice.setText(CartController.nf.format(product.getPrice()));
        holder.txtDiscount.setText("-" + String.valueOf(product.getDiscount()) + "%");
        if (product.getDiscount() > 0) {
            holder.originalPrice.setPaintFlags(holder.originalPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        } else {
            holder.layout.removeView(holder.txtDiscount);
            holder.linearLayout.removeView(holder.originalPrice);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context.getApplicationContext(), DetailProductActivity.class);
                intent.putExtra("Product", new Gson().toJson(product));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (listProduct != null)
            return listProduct.size();
        return 0;
    }

    public void setData(List<Product> list) {
        this.listProduct = list;
        notifyDataSetChanged();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {
        private TextView productName, txtDiscount, productPrice, originalPrice;
        private ImageView imageProduct;
        private RelativeLayout layout;
        private LinearLayout linearLayout;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            this.productName = itemView.findViewById(R.id.product_name);
            this.txtDiscount = itemView.findViewById(R.id.txtDiscount);
            this.productPrice = itemView.findViewById(R.id.product_price);
            this.originalPrice = itemView.findViewById(R.id.original_price);
            this.imageProduct = itemView.findViewById(R.id.img_product);
            this.layout = itemView.findViewById(R.id.rl);
            this.linearLayout = itemView.findViewById(R.id.ln);
        }

        public ProductViewHolder(@NonNull View itemView, TextView productName, TextView txtDiscount, TextView productPrice,
                                 TextView originalPrice, ImageView imageProduct, RelativeLayout layout) {
            super(itemView);
            this.productName = productName;
            this.txtDiscount = txtDiscount;
            this.productPrice = productPrice;
            this.originalPrice = originalPrice;
            this.imageProduct = imageProduct;
            this.layout = layout;
        }


    }

    public List<Product> getListProduct() {
        return listProduct;
    }

    public void setListProduct(List<Product> listProduct) {
        this.listProduct = listProduct;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}

