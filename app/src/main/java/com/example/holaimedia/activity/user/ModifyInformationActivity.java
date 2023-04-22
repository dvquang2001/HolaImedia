package com.example.holaimedia.activity.user;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.holaimedia.R;
import com.example.holaimedia.activity.auth.SplashActivity;
import com.example.holaimedia.activity.face.FaceActivity;
import com.example.holaimedia.model.auth.User;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

public class ModifyInformationActivity extends AppCompatActivity {

    private ImageView ivBack;
    private EditText etName, etPhoneNumber, etAddress, etPinCode;
    private Button btnUpdate, btnAddFaceIdentified;
    private User user;
    private String userID;
    private Gson gson;
    private FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_information);

        database = FirebaseDatabase.getInstance();

        gson = new Gson();
        String userJson = SplashActivity.sharedPreferences.getString(SplashActivity.USER_DATA, null);
        userID = SplashActivity.sharedPreferences.getString(SplashActivity.USER_ID, null);
        user = gson.fromJson(userJson, User.class);

        initViews();
        initViewListener();
    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
    }

    private void initViews() {
        ivBack = findViewById(R.id.ivBack);
        etName = findViewById(R.id.etName);
        etPhoneNumber = findViewById(R.id.etPhoneNumber);
        etAddress = findViewById(R.id.etAddress);
        etPinCode = findViewById(R.id.etPinCode);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnAddFaceIdentified = findViewById(R.id.btnAddFaceIdentified);
    }

    private void initData() {
        if (user != null) {
            etName.setText(user.getName(), TextView.BufferType.SPANNABLE);
            etPhoneNumber.setText(user.getPhoneNumber(), TextView.BufferType.SPANNABLE);
            etAddress.setText(user.getAddress(), TextView.BufferType.SPANNABLE);
            etPinCode.setText(user.getPinCode(), TextView.BufferType.SPANNABLE);
        }
    }

    private void initViewListener() {
        ivBack.setOnClickListener(view -> finish());
        btnAddFaceIdentified.setOnClickListener(view -> onAddFaceIdentifiedClicked());
        btnUpdate.setOnClickListener(view -> onUpdateClicked());
    }

    private void onAddFaceIdentifiedClicked() {
        startActivity(new Intent(this, FaceActivity.class));
    }

    private void onUpdateClicked() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialog = inflater.inflate(R.layout.dialog_password, null);
        builder.setView(dialog);

        EditText etPassword = dialog.findViewById(R.id.etPassword);
        Button btnConfirm = dialog.findViewById(R.id.btnConfirm);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        btnConfirm.setOnClickListener(view -> {
            String inputPassword = etPassword.getText().toString();
            if (inputPassword.equalsIgnoreCase(user.getPassword())) {
                user.setName(etName.getText().toString());
                user.setPhoneNumber(etPhoneNumber.getText().toString());
                user.setAddress(etAddress.getText().toString());
                user.setPinCode(etPinCode.getText().toString());
                database.getReference().child("Users").child(userID).setValue(user).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        saveUserData();
                        Toast.makeText(this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                    }
                });
                alertDialog.dismiss();
            } else {
                Toast.makeText(this, "Mật khẩu không đúng", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveUserData() {
        String userJson = gson.toJson(user);
        SplashActivity.sharedPreferences.edit().putString(SplashActivity.USER_DATA, userJson).apply();
    }
}