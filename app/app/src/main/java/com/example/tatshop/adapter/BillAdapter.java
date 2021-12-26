package com.example.tatshop.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.tatshop.R;
import com.example.tatshop.controller.CartController;
import com.example.tatshop.model.Bill;
import com.example.tatshop.module.Helper;

import java.util.List;

public class BillAdapter extends BaseAdapter {
    private Activity context;
    private List<Bill> list;
    private int layoutId;

    public BillAdapter(Activity context, List<Bill> list, int layoutId) {
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
        TextView stt = (TextView) convertView.findViewById(R.id.status_ship);
        TextView billKey = (TextView) convertView.findViewById(R.id.bill_key);
        TextView total = (TextView) convertView.findViewById(R.id.total_price);
        ListView listItem = (ListView) convertView.findViewById(R.id.list_item_bought);
        final Bill bill = list.get(position);
        String key = "Mã hóa đơn: " + bill.getId();
        ItemBoughtAdapter adapter = new ItemBoughtAdapter(this.context, bill.getItem(), R.layout.item_bought);
        listItem.setAdapter(adapter);
        Helper.setListViewHeight(listItem);
        billKey.setText(key);
        stt.setText(this.convertStt(bill.getStt(), stt));
        total.setText(CartController.nf.format(bill.getTotal()));
        return convertView;
    }

    private String convertStt(int stt, TextView txtStt) {
        switch (stt) {
            case 1:
                txtStt.setBackground(context.getResources().getDrawable(R.drawable.bg_wait));
                txtStt.setTextColor(context.getResources().getColor(R.color.warning));
                return "Chờ xác nhận";
            case 2:
                return "Đã xác nhận";
            case 3:
                return "Đang vận chuyển";
            case 4:
                txtStt.setTextColor(context.getResources().getColor(R.color.success));
                txtStt.setBackground(context.getResources().getDrawable(R.drawable.bg_success));
                return "Đã giao";
            default:
                return null;
        }

    }
}
