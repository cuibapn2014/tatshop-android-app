package com.example.tatshop.adapter;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.tatshop.R;
import com.example.tatshop.model.AttributeProduct;
import com.example.tatshop.model.Product;
import com.squareup.picasso.Picasso;

import java.util.List;


public class CustomAttributeAdapter extends BaseAdapter {
    private Activity context;
    private List<AttributeProduct> listAttr;
    private int layoutId;
    private int checkButton = -1;
    private AttributeProduct obj;

    public CustomAttributeAdapter(Activity activity, List<AttributeProduct> listAttr, int layoutId) {
        this.context = activity;
        this.listAttr = listAttr;
        this.layoutId = layoutId;
    }


    @Override
    public int getCount() {
        return listAttr.size();
    }

    @Override
    public Object getItem(int position) {
        return listAttr.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = this.context.getLayoutInflater();
        convertView = layoutInflater.inflate(this.layoutId, null);
        final AttributeProduct attr = listAttr.get(position);
        RadioButton radioButton = (RadioButton) convertView.findViewById(R.id.attr_value);

        radioButton.setText(attr.getValue());

        //radioButton.setTag(attr);

        radioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                checkButton = position;
                obj = listAttr.get(position);
                notifyDataSetChanged();
            }
        });

        if (checkButton == position){
            radioButton.setChecked(true);
            obj = listAttr.get(checkButton);
        }else {
            radioButton.setChecked(false);
        }

        return convertView;
    }

    public AttributeProduct getAttributeProduct(){
        return obj;
    }
}
