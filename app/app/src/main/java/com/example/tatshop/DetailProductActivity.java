package com.example.tatshop;

import android.content.DialogInterface;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.tatshop.adapter.CommentAdapter;
import com.example.tatshop.adapter.CustomAttributeAdapter;
import com.example.tatshop.adapter.ImageAdapter;
import com.example.tatshop.controller.CartController;
import com.example.tatshop.controller.CommentController;
import com.example.tatshop.controller.UserController;
import com.example.tatshop.model.AttributeProduct;
import com.example.tatshop.model.Comment;
import com.example.tatshop.model.Image;
import com.example.tatshop.model.Product;
import com.example.tatshop.module.Helper;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;

public class DetailProductActivity extends AppCompatActivity {
    private Product product, cartItem;
    private TextView title, sold, detailDes, price, discount, discard;
    private List<Image> listImg;
    private ImageAdapter imageAdapter;
    private ViewPager viewPagerPhoto;
    private Button btnAdd, btnUp, btnDown, btnAddItemCart, submitComment;
    private CircleIndicator indicator;
    private CustomAttributeAdapter attrSizeAdapter, attrColorAdapter;
    private GridView gridViewSize, gridViewColor;
    private List<AttributeProduct> listSize, listColor;
    private EditText mAmount, comment;
    private int amount;
    private BottomSheetDialog sheetDialog;
    private List<Comment> listCmt;
    private CommentAdapter commentAdapter;
    private RatingBar vote;
    private ListView lv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_product_activity);

        mapping();
        loadData();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sheetDialog = new BottomSheetDialog(DetailProductActivity.this, R.style.BottomSheetDialogTheme);
                View bottomSheetView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.popup_order, findViewById(R.id.dialog_attr));
                setAmount(bottomSheetView);
                btnAdd.setEnabled(false);

                btnAddItemCart = (Button) bottomSheetView.findViewById(R.id.add_cart);
                gridViewSize = bottomSheetView.findViewById(R.id.option_size);
                gridViewColor = bottomSheetView.findViewById(R.id.option_color);

                attrSizeAdapter = new CustomAttributeAdapter(DetailProductActivity.this, listSize, R.layout.item_attr_check);
                attrColorAdapter = new CustomAttributeAdapter(DetailProductActivity.this, listColor, R.layout.item_attr_check);

                gridViewSize.setAdapter(attrSizeAdapter);
                gridViewColor.setAdapter(attrColorAdapter);

                btnAddItemCart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (attrSizeAdapter.getAttributeProduct() != null && attrColorAdapter.getAttributeProduct() != null) {
                            btnAddItemCart.setEnabled(false);
                            List<AttributeProduct> lat = new ArrayList<>();
                            lat.add(attrSizeAdapter.getAttributeProduct());
                            lat.add(attrColorAdapter.getAttributeProduct());
                            cartItem = new Product();
                            cartItem.setId(product.getId());
                            cartItem.setTitle(product.getTitle());
                            cartItem.setThumbnail(product.getThumbnail());
                            cartItem.setAttr(lat);
                            cartItem.setQty(amount);
                            cartItem.setPrice(product.getPrice());
                            cartItem.setDiscount(product.getDiscount());

                            CartController cartController = new CartController();
                            cartController.addCartItem(cartItem);
                            cartController.saveDataLocal();

                            sheetDialog.hide();
                            btnAdd.setEnabled(true);
                            btnAddItemCart.setEnabled(true);
                            Toast.makeText(DetailProductActivity.this,
                                    getResources().getString(R.string.toast_success_add),
                                    Toast.LENGTH_SHORT)
                                    .show();
                        }
                    }
                });

                sheetDialog.setContentView(bottomSheetView);
                sheetDialog.show();
                sheetDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        btnAdd.setEnabled(true);
                    }
                });
            }
        });

        submitComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (UserController.getUSER() == null)
                    Toast.makeText(DetailProductActivity.this, "Vui lòng đăng nhập để sử dụng tính năng này", Toast.LENGTH_SHORT).show();
                else {
                    if (vote.getRating() > 0 && comment.getText().toString().trim().length() > 0) {
                        Comment cmt = new Comment();
                        cmt.setUser(UserController.getUSER());
                        cmt.setVote((int) vote.getRating());
                        cmt.setContent(comment.getText().toString());
                        cmt.setIdProduct(product.getId());
                        new CommentController().addComment(cmt, DetailProductActivity.this);
                        listCmt.add(cmt);
                        commentAdapter.notifyDataSetChanged();
                        Helper.setListViewHeight(lv);
                    } else {
                        Toast.makeText(DetailProductActivity.this, "Bạn chưa nhập đánh giá hoặc chưa bình chọn", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void loadData() {
        Gson gson = new Gson();
        product = gson.fromJson(getIntent().getStringExtra("Product"), Product.class);
        listCmt = product.getComment();
        lv = (ListView) findViewById(R.id.list_comment);
        commentAdapter = new CommentAdapter(this, listCmt, R.layout.list_comment);

        if (commentAdapter.getCount() > 0) {
            LinearLayout layout = (LinearLayout) findViewById(R.id.ln);
            TextView tv = findViewById(R.id.result);
            layout.removeView(tv);
        }

        lv.setAdapter(commentAdapter);
        Helper.setListViewHeight(lv);

        listImg = product.getImage();
        listSize = getListSize(product.getAttr());
        listColor = getListColor(product.getAttr());

        imageAdapter = new ImageAdapter(listImg);
        viewPagerPhoto.setAdapter(imageAdapter);
        indicator.setViewPager(viewPagerPhoto);

        title.setText(product.getTitle());
        sold.setText(String.valueOf(product.getSold()));

        float pDiscount = 1 - ((float) product.getDiscount() / 100);

        price.setText(CartController.nf.format(product.getPrice() * pDiscount));
        isDiscount(product);

        String description = product.getContent();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
            detailDes.setText(Html.fromHtml(description, Html.FROM_HTML_MODE_COMPACT));
        else
            detailDes.setText(Html.fromHtml(description));

    }

    private void mapping() {
        title = (TextView) findViewById(R.id.detail_title);
        sold = (TextView) findViewById(R.id.sold_amount);
        detailDes = (TextView) findViewById(R.id.detail_des);
        viewPagerPhoto = findViewById(R.id.view_pager_product);
        btnAdd = (Button) findViewById(R.id.btn_popup_attr);
        indicator = findViewById(R.id.indicator_detail);
        price = (TextView) findViewById(R.id.txt_price);
        discount = (TextView) findViewById(R.id.txtDiscount);
        discard = (TextView) findViewById(R.id.original_price);
        comment = (EditText) findViewById(R.id.comment);
        vote = (RatingBar) findViewById(R.id.rating);
        submitComment = (Button) findViewById(R.id.btn_submit);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (sheetDialog != null)
            sheetDialog.cancel();
        this.finish();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (sheetDialog != null)
                    sheetDialog.cancel();
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public List<AttributeProduct> getListSize(List<AttributeProduct> listAttr) {
        List<AttributeProduct> list = new ArrayList<>();
        for (AttributeProduct attr : listAttr) {
            if (attr.getName().equals("size"))
                list.add(attr);
        }
        return list;
    }

    public List<AttributeProduct> getListColor(List<AttributeProduct> listAttr) {
        List<AttributeProduct> list = new ArrayList<>();
        for (AttributeProduct attr : listAttr) {
            if (attr.getName().equals("color"))
                list.add(attr);
        }
        return list;
    }

    public void setAmount(View v) {
        btnUp = (Button) v.findViewById(R.id.btn_up);
        btnDown = (Button) v.findViewById(R.id.btn_down);
        mAmount = (EditText) v.findViewById(R.id.amount_order);
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

    public void isDiscount(Product product) {
        if (product.getDiscount() > 0) {
            discount.setText("-" + String.valueOf(product.getDiscount()) + "%");
            discard.setPaintFlags(discard.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            discard.setText(CartController.nf.format(product.getPrice()));
        } else {
            RelativeLayout layout = (RelativeLayout) findViewById(R.id.dl);
            LinearLayout lnLayout = (LinearLayout) findViewById(R.id.price);
            layout.removeView(discount);
            lnLayout.removeView(discard);
        }
    }

}
