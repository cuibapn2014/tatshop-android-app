package com.example.tatshop;

import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tatshop.adapter.ProductCategoryAdapter;
import com.example.tatshop.model.Product;
import com.example.tatshop.module.Paginate;
import com.example.tatshop.module.PaginationScrollListener;
import com.example.tatshop.storage.DataLocalManger;

import java.util.ArrayList;
import java.util.List;

public class CategoryActivity extends AppCompatActivity {
    private RecyclerView rvProduct;
    private ProductCategoryAdapter productCategoryAdapter;
    private GridLayoutManager gridLayoutManager;
    private List<Product> listProduct;
    private boolean isLoading, isLastPage;
    private int currentPage = 1;
    private int totalPage = 2;
    private ProgressBar progressBar;
    private int idCategory;
    private Paginate paginate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category_activity);
        mapping();
        loadData();
        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.color.teal));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void mapping() {
        rvProduct = (RecyclerView) findViewById(R.id.rv_product);
        gridLayoutManager = new GridLayoutManager(CategoryActivity.this, 2);
        rvProduct.setLayoutManager(gridLayoutManager);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        paginate = new Paginate();
    }

    private void loadData() {
        idCategory = getIntent().getIntExtra("IDCategory", 0);
        if (idCategory == 0)
            getSupportActionBar().setTitle("Thời trang nữ");
        else if (idCategory == 1)
            getSupportActionBar().setTitle("Thời trang nam");
        paginate.setListProduct(getListProduct());
        totalPage = paginate.paginate(getListProduct().size());
        listProduct = paginate.getItemCurrentPage(currentPage);
        productCategoryAdapter = new ProductCategoryAdapter();
        productCategoryAdapter.setListProduct(listProduct);
        productCategoryAdapter.setContext(this);
        rvProduct.addOnScrollListener(new PaginationScrollListener(gridLayoutManager) {
            @Override
            public void loadMoreItem() {
                isLoading = true;
                currentPage += 1;
                loadNextPage();
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }

            @Override
            public boolean isLastPage() {
                return isLastPage;
            }
        });
        setData();
    }

    private void loadNextPage() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getListItemCurrentPage();
                productCategoryAdapter.notifyDataSetChanged();
                isLoading = false;
                progressBar.setVisibility(View.GONE);
                if (currentPage == totalPage)
                    isLastPage = true;
            }
        }, 2000);
    }

    private List<Product> getListProduct() {
        List<Product> list = new ArrayList<>();
        for (Product product : DataLocalManger.getListProduct()) {
            if (idCategory == 1) {
                if (product.getSubcategory().getCategory().getType() == 1)
                    list.add(product);
            } else if (idCategory == 0) {
                if (product.getSubcategory().getCategory().getType() == 0 || product.getSubcategory().getCategory().getType() == 2)
                    list.add(product);
            }
        }
        return list;
    }

    @Override
    public void onBackPressed() {
        this.finish();
        super.onBackPressed();
    }

    private void setData() {
        productCategoryAdapter.setData(listProduct);
        rvProduct.setAdapter(productCategoryAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            this.finish();
        return super.onOptionsItemSelected(item);
    }

    private void getListItemCurrentPage(){
        List<Product> list = paginate.getItemCurrentPage(currentPage);
        for (Product product : list){
            listProduct.add(product);
        }
    }
}
