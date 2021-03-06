package com.example.tatshop.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.example.tatshop.CheckoutActivity;
import com.example.tatshop.MainActivity;
import com.example.tatshop.R;
import com.example.tatshop.adapter.ItemCartAdapter;
import com.example.tatshop.controller.CartController;
import com.example.tatshop.model.Product;
import com.example.tatshop.module.Helper;
import com.example.tatshop.storage.DataLocalManger;

import java.util.List;

public class CartFragment extends Fragment {
    private List<Product> listProduct;
    private ListView listViewResult;
    private ItemCartAdapter itemCartAdapter;
    private View view;
    private AppCompatButton btnRemoveCart, btnOrder, btnApplyCode;
    private TextView result, txtTotal;
    private EditText code;

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
                itemCartAdapter.notifyDataSetChanged();
                Toast.makeText(getActivity(), "???? x??a gi??? h??ng", Toast.LENGTH_SHORT).show();
            }
        });

        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView total = (TextView) view.findViewById(R.id.total_price);
                Intent intent = new Intent(getActivity(), CheckoutActivity.class);
                startActivity(intent);
            }
        });

        btnApplyCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Helper.hideKeyboard(getActivity());
                CartController.applyCode(code.getText().toString().trim(), txtTotal, getActivity());
            }
        });
    }

    private void loadData(View view) {
        if (CartController.getListCartItem() != null)
            listProduct = CartController.getListCartItem();
        result = view.findViewById(R.id.result);
        if (listProduct != null) {
            itemCartAdapter = new ItemCartAdapter(getActivity(), listProduct, R.layout.custom_list_cart_item);
            listViewResult.setAdapter(itemCartAdapter);
            result.setText(null);
        } else {
            result.setText("B???n ch??a c?? g?? trong gi???! Ti???p t???c shopping th??i..");
        }
        txtTotal.setText(CartController.nf.format(CartController.getTotalPrice()));
        changeData();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (CheckoutActivity.isCheck()) {
            Toast.makeText(getContext(), "?????t h??ng th??nh c??ng!", Toast.LENGTH_LONG).show();
            CartController.clearCart();
            DataLocalManger.setDataKey("CART_KEY");
            DataLocalManger.setListCartItem(CartController.getListCartItem());
            CheckoutActivity.setCheck(false);
        }
        loadData(this.view);
        code.setEnabled(true);
    }

    private void removeCart() {
        CartController.clearCart();
        DataLocalManger.setListCartItem(listProduct);
        loadData(this.view);
        changeData();
    }

    private void mapping() {
        listViewResult = view.findViewById(R.id.listview_shopping_cart);
        btnRemoveCart = this.view.findViewById(R.id.btn_removeCart);
        btnOrder = this.view.findViewById(R.id.btn_order);
        btnApplyCode = this.view.findViewById(R.id.btn_confirm_discount);
        txtTotal = (TextView) this.view.findViewById(R.id.total_price);
        code = (EditText) this.view.findViewById(R.id.txt_code);
    }

    public void changeData() {
        int amountCartItem = 0;
        if(CartController.getListCartItem() != null)
        amountCartItem = CartController.getListCartItem().size();
        AHNotification notification = new AHNotification.Builder()
                .setText(String.valueOf(amountCartItem))
                .setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorBottomNavigationNotification))
                .setTextColor(ContextCompat.getColor(getActivity(), R.color.white))
                .build();
        MainActivity.bottomNavigationView.setNotification(notification, 2);
    }

    @Override
    public void onPause() {
        super.onPause();
        code.setEnabled(false);
    }

}
