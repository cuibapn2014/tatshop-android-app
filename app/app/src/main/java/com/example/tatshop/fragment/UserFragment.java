package com.example.tatshop.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.tatshop.FollowOrderActivity;
import com.example.tatshop.LoginActivity;
import com.example.tatshop.PersonalProfileActivity;
import com.example.tatshop.R;
import com.example.tatshop.controller.UserController;
import com.example.tatshop.model.User;
import com.google.android.material.imageview.ShapeableImageView;
import com.squareup.picasso.Picasso;

public class UserFragment extends Fragment {
    private ShapeableImageView imgAvatar;
    private TextView name, email, txtLogin, txtLogout, txtFollow;
    private LinearLayout layout;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mapping(view);
        loadData();

        setBtnClick();
        txtFollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(UserController.getUSER() != null)
                    startActivity(new Intent(getActivity(), FollowOrderActivity.class));
                else
                    startActivity(new Intent(getActivity(), LoginActivity.class));

            }
        });
    }

    public void mapping(View v) {
        imgAvatar = (ShapeableImageView) v.findViewById(R.id.img_avatar);
        name = (TextView) v.findViewById(R.id.name);
        email = (TextView) v.findViewById(R.id.email);
        txtLogin = (TextView) v.findViewById(R.id.user_login);
        txtLogout = (TextView) v.findViewById(R.id.user_logout);
        layout = (LinearLayout) v.findViewById(R.id.user_menu);
        txtFollow = (TextView) v.findViewById(R.id.user_follow);
    }

    @Override
    public void onResume() {
        super.onResume();
        loadData();
        setBtnClick();
    }

    public void loadData() {
        User user = UserController.isLogin() ? UserController.getUSER() : UserController.getUSER();
        if (user != null && user.getId() != 0) {
            Picasso.get().load(user.getImage()).into(imgAvatar);
            name.setText(user.getName());
            email.setText(user.getEmail());
            txtLogin.setText(getResources().getString(R.string.my_info));
            txtLogout.setVisibility(TextView.VISIBLE);
        } else {
            imgAvatar.setImageDrawable(getResources().getDrawable(R.drawable.user));
            name.setText(null);
            email.setText(null);
            txtLogin.setText(getResources().getString(R.string.login));
            txtLogout.setVisibility(TextView.INVISIBLE);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    public void setBtnClick() {
        if (!UserController.isSuccess()) {
            txtLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                }
            });
        } else {
            txtLogin.setText(getResources().getString(R.string.my_info));
            txtLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getActivity(), PersonalProfileActivity.class));
                }
            });

            txtLogout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    UserController.logout();
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                    Toast.makeText(getActivity(), "Bạn đã đăng xuất !", Toast.LENGTH_LONG).show();
                    loadData();
                }
            });
        }
    }
}
