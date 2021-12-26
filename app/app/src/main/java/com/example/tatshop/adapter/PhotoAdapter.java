package com.example.tatshop.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.tatshop.model.Photo;
import com.example.tatshop.R;

import java.util.List;

public class PhotoAdapter extends PagerAdapter {
    private List<Photo> listPhoto;

    public PhotoAdapter(List<Photo> listPhoto) {
        this.listPhoto = listPhoto;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.item_gellary, container, false);
        ImageView imageView = view.findViewById(R.id.img_gellary);

        Photo photo = listPhoto.get(position);
        imageView.setImageResource(photo.getResourceId());

        container.addView(view);
        return view;
    }

    @Override
    public int getCount() {
        if (listPhoto != null)
            return listPhoto.size();
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
