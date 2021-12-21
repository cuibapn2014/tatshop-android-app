package com.example.tatshop.adapter;

import android.app.Activity;
import android.content.Context;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.tatshop.R;
import com.example.tatshop.model.Comment;
import com.google.android.material.imageview.ShapeableImageView;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.List;

public class CommentAdapter extends BaseAdapter {
    private Activity context;
    private List<Comment> listCmt;
    private int layoutId;

    public CommentAdapter(Activity context, List<Comment> listCmt, int layoutId) {
        this.context = context;
        this.listCmt = listCmt;
        this.layoutId = layoutId;
    }

    @Override
    public int getCount() {
        return listCmt.size();
    }

    @Override
    public Object getItem(int position) {
        return listCmt.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = this.context.getLayoutInflater();
        convertView = inflater.inflate(this.layoutId, parent, false);
        ShapeableImageView imgAvatar = convertView.findViewById(R.id.img_avatar);
        TextView username = (TextView) convertView.findViewById(R.id.user_name);
        TextView vote = (TextView) convertView.findViewById(R.id.vote);
        TextView comment = (TextView) convertView.findViewById(R.id.comment);
        final Comment cmt = listCmt.get(position);

        Picasso.get().load(cmt.getUser().getImage()).into(imgAvatar);
        username.setText(cmt.getUser().getName());
        vote.setText(String.valueOf(cmt.getVote()));
        comment.setText(cmt.getContent());

        return convertView;
    }

}
