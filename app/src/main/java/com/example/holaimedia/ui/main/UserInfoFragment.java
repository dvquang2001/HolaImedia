package com.example.holaimedia.ui.main;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.holaimedia.R;
import com.example.holaimedia.activity.auth.SplashActivity;
import com.example.holaimedia.activity.user.InformationActivity;
import com.example.holaimedia.activity.user.UserPolicyActivity;
import com.example.holaimedia.model.auth.User;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class UserInfoFragment extends Fragment {
    View root;
    private final List<String> features = new ArrayList<>();
    private User user;
    private Gson gson;
    public UserInfoFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_user_info, container, false);

        gson = new Gson();
        String userJson = SplashActivity.sharedPreferences.getString(SplashActivity.USER_DATA, null);
        user = gson.fromJson(userJson, User.class);

        initViews();
        return root;
    }

    private void initViews() {
        features.add("Thông tin tài khoản");
        features.add("Điều khoản và quy định");
        features.add("Đăng xuất");

        TextView tvEmail = root.findViewById(R.id.tvEmail);
        tvEmail.setText(user.getEmail());

        ListView lvUserFeature = root.findViewById(R.id.lvUserFeature);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1,features);
        lvUserFeature.setAdapter(adapter);

        lvUserFeature.setOnItemClickListener((adapterView, view, position, l) -> {
            switch (position) {
                case 0:
                    startActivity(new Intent(requireContext(), InformationActivity.class));
                    break;
                case 1:
                    startActivity(new Intent(requireContext(), UserPolicyActivity.class));
                    break;
                case 2:
                    showLogoutDialog();
                    break;
            }
        });
    }

    private void showLogoutDialog() {

    }
}