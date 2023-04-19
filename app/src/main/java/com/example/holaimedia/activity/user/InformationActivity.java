package com.example.holaimedia.activity.user;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import com.example.holaimedia.R;

public class InformationActivity extends AppCompatActivity {

    private ImageView imgProfile;
    private Button btnModify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);

        initViews();
        initViewListener();
    }

    private void initViews() {
        imgProfile = findViewById(R.id.imgProfile);
    }

    private void initViewListener() {
        imgProfile.setOnClickListener(view -> getPictureFromGallery());
        btnModify.setOnClickListener(view -> startActivity(new Intent(this,ModifyInformationActivity.class)));
    }

    private void getPictureFromGallery() {
        //todo
    }
}