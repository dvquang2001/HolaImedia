package com.example.holaimedia.ui.main;

import android.app.AlertDialog;
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

        TextView tvUserEmail = root.findViewById(R.id.tvUserEmail);
        tvUserEmail.setText(user.getEmail());

        ListView lvUserFeature = root.findViewById(R.id.lvUserFeature);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1, features);
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
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setMessage(R.string.logout_message)
                .setTitle(R.string.log_out)
                .setPositiveButton(R.string.ok, (dialogInterface, i) -> {
                    SplashActivity.sharedPreferences.edit().putBoolean(SplashActivity.IS_LOGIN, false).apply();
                    SplashActivity.sharedPreferences.edit().putString(SplashActivity.USER_ID, "").apply();
                    SplashActivity.sharedPreferences.edit().putString(SplashActivity.USER_DATA, "").apply();
                    startActivity(new Intent(requireContext(), SplashActivity.class));
                    requireActivity().finish();
                })
                .setNegativeButton(R.string.cancel, (dialogInterface, i) -> {
                    dialogInterface.dismiss();
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}