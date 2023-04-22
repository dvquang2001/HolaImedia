package com.example.holaimedia.activity.user;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.holaimedia.R;
import com.example.holaimedia.activity.auth.SplashActivity;
import com.example.holaimedia.model.auth.User;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.gson.Gson;

import java.util.UUID;

public class InformationActivity extends AppCompatActivity {

    private ImageView imgProfile, ivBack;
    private TextView tvName, tvEmail, tvPhoneNumber, tvAddress;
    private Button btnModify;
    private User user;
    private Gson gson;
    private String userID;
    private FirebaseStorage storage;
    private FirebaseDatabase database;
    private ActivityResultLauncher<String> mTakePhoto;
    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);

        gson = new Gson();
        storage = FirebaseStorage.getInstance();
        database = FirebaseDatabase.getInstance();

        String userJson = SplashActivity.sharedPreferences.getString(SplashActivity.USER_DATA, null);
        userID = SplashActivity.sharedPreferences.getString(SplashActivity.USER_ID, null);
        user = gson.fromJson(userJson, User.class);

        mTakePhoto = registerForActivityResult(new ActivityResultContracts.GetContent(),
                result -> {
                    imgProfile.setImageURI(result);
                    UUID uuid = UUID.randomUUID();
                    storageReference = storage.getReference().child("profile_picture")
                            .child(uuid.toString());
                    storageReference.putFile(result).addOnCompleteListener(task -> {
                        Toast.makeText(this, "Uploaded", Toast.LENGTH_SHORT).show();
                        storageReference.getDownloadUrl().addOnSuccessListener(uri -> {
                            database.getReference().child("Users").child(userID)
                                    .child("profileImg").setValue(uri.toString());
                            user.setProfileImg(uri.toString());
                            Glide.with(this).load(user.getProfileImg()).into(imgProfile);
                            saveUserData();
                            Toast.makeText(this, "Profile picture uploaded", Toast.LENGTH_SHORT).show();
                        });
                    });
                });

        initViews();
        initData();
        initViewListener();
    }

    private void initViews() {
        imgProfile = findViewById(R.id.imgProfile);
        ivBack = findViewById(R.id.ivBack);
        tvName = findViewById(R.id.tvName);
        tvPhoneNumber = findViewById(R.id.tvPhone);
        tvEmail = findViewById(R.id.tvEmail);
        tvAddress = findViewById(R.id.tvAddress);
        btnModify = findViewById(R.id.btnModify);
    }

    private void initData() {
        tvName.setText(user.getName());
        tvEmail.setText(user.getEmail());
        tvPhoneNumber.setText(user.getPhoneNumber());
        tvAddress.setText(user.getAddress());
    }

    private void initViewListener() {
        imgProfile.setOnClickListener(view ->  mTakePhoto.launch("image/*"));
        ivBack.setOnClickListener(view -> finish());
        btnModify.setOnClickListener(view -> startActivity(new Intent(this,ModifyInformationActivity.class)));
    }

    private void saveUserData() {
        String userJson = gson.toJson(user);
        SplashActivity.sharedPreferences.edit().putString(SplashActivity.USER_DATA, userJson).apply();
    }

}