package com.example.tatshop.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.aurelhubert.ahbottomnavigation.notification.AHNotification;
import com.example.tatshop.DataLocalManger;
import com.example.tatshop.MainActivity;
import com.example.tatshop.R;
import com.example.tatshop.adapter.ItemCartAdapter;
import com.example.tatshop.controller.CartController;
import com.example.tatshop.model.Product;

import org.w3c.dom.Text;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class CartFragment extends Fragment {
    private List<Product> listProduct;
    private ListView listViewResult;
    private ItemCartAdapter itemCartAdapter;
    private View view;
    private AppCompatButton btnRemoveCart, btnOrder;
    private TextView result;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view = view;
        mapping();
        loadData(this.view);

        btnRemoveCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeCart();
                Toast.makeText(getActivity(), "Đã xóa giỏ hàng",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadData(View view){
        listProduct = CartController.getListCartItem();
        result = view.findViewById(R.id.result);
        TextView txtTotal = (TextView) view.findViewById(R.id.total_price);
        if(listProduct.size() > 0) {
            itemCartAdapter = new ItemCartAdapter(getActivity(), listProduct, R.layout.custom_list_cart_item);
            listViewResult.setAdapter(itemCartAdapter);
            result.setText(null);
        }else {
            result.setText("Bạn chưa có gì trong giỏ! Tiếp tục shopping thôi..");
        }
        txtTotal.setText(CartController.nf.format(CartController.getTotal()));
    }

    @Override
    public void onResume() {
        super.onResume();
        loadData(this.view);
    }

    private void removeCart(){
        CartController.clearCart();
        DataLocalManger.setListCartItem(listProduct);
        loadData(this.view);
        changeData();

    }

    private void mapping(){
        listViewResult = view.findViewById(R.id.listview_shopping_cart);
        btnRemoveCart = this.view.findViewById(R.id.btn_removeCart);
        btnOrder = this.view.findViewById(R.id.btn_order);
    }

    public void changeData() {
        int amountCartItem = CartController.getListCartItem().size();
        AHNotification notification = new AHNotification.Builder()
                .setText(String.valueOf(amountCartItem))
                .setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorBottomNavigationNotification))
                .setTextColor(ContextCompat.getColor(getActivity(), R.color.white))
                .build();
        MainActivity.bottomNavigationView.setNotification(notification, 2);
    }
}
