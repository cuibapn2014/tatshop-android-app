package com.example.tatshop.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import com.example.tatshop.DetailProductActivity;
import com.example.tatshop.R;
import com.example.tatshop.adapter.ProductAdapter;
import com.example.tatshop.api.ApiService;
import com.example.tatshop.model.Product;
import com.example.tatshop.module.Helper;
import com.google.gson.Gson;

import org.w3c.dom.Text;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchFragment extends Fragment {
    private EditText keyword;
    private List<Product> listProduct;
    private GridView resultGrid;
    private AppCompatButton btnSearch;
    private TextView txtResult;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mapping(view);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Helper.hideKeyboard(getActivity());
                if (keyword.getText().toString() != "") {
                    txtResult.setText("Chúng tôi đang tìm kiếm sản phẩm cho bạn, hãy chờ một lát..");
                    getListResult(keyword.getText().toString());
                }
            }
        });
    }

    public void mapping(View v) {
        keyword = (EditText) v.findViewById(R.id.keyword);
        resultGrid = (GridView) v.findViewById(R.id.grid_result_search);
        btnSearch = (AppCompatButton) v.findViewById(R.id.btn_search);
        txtResult = (TextView) v.findViewById(R.id.txt_result);
    }

    @Override
    public void onResume() {
        super.onResume();
        keyword.setEnabled(true);
    }

    public void onPause() {
        super.onPause();
        keyword.setEnabled(false);
    }

    private void getListResult(String keyword) {
        final String token = "f0ecce8d027f556c369a1d24d237b18b272a010df5896f8abe05e98accabd261";
        ApiService.apiService.getResultSearch(keyword, token).enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                listProduct = response.body();
                ProductAdapter adapter = new ProductAdapter(getActivity(), listProduct, R.layout.item_custom_grid);
                resultGrid.setAdapter(adapter);
                Helper.setGridViewHeight(resultGrid);
                Gson gson = new Gson();
                resultGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(getActivity(), DetailProductActivity.class);
                        intent.putExtra("Product", gson.toJson(listProduct.get(position)));
                        startActivity(intent);
                    }
                });
                if (adapter.getCount() > 0)
                    txtResult.setText("Tìm thấy " + adapter.getCount() + " kết quả với từ khóa " + keyword);
                else
                    txtResult.setText("Không tìm thấy kết quả nào!");
                Log.e("TAG", "Success");
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                txtResult.setText("Không tìm thấy kết quả nào!");
                Log.e("TAG", "Error");
            }
        });
    }
}
