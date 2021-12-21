package com.example.tatshop.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.tatshop.R;
import com.example.tatshop.model.Image;
import com.example.tatshop.model.Photo;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ImageAdapter extends PagerAdapter {
    private List<Image> listImage;

    public ImageAdapter(List<Image> listImage) {
        this.listImage = listImage;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.item_gellary, container, false);
        ImageView imageView = view.findViewById(R.id.img_gellary);

        Image image = listImage.get(position);
        Picasso.get().load(image.getImage()).into(imageView);

        container.addView(view);
        return view;
    }

    @Override
    public int getCount() {
        if(listImage != null)
            return listImage.size();
        return 0;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
