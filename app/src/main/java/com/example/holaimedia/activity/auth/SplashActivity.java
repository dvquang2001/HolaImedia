package com.example.holaimedia.activity.auth;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.holaimedia.R;
import com.example.holaimedia.activity.MainActivity;
import com.example.holaimedia.model.auth.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

@SuppressLint("CustomSplashScreen")
public class SplashActivity extends AppCompatActivity {

    public static SharedPreferences sharedPreferences;
    public static String SHARE_PREF_NAME = "SHARE_PREF_NAME";
    public static String USER_DATA = "USER_DATA";
    public static String USER_ID = "USER_ID";
    public static String IS_LOGIN = "IS_LOGIN";
    public static String LOAI_THE = "LOAI_THE";
    public static String MENH_GIA = "MENH_GIA";
    public static String ADMIN_EMAIL = "admin2023@gmail.com";
    public static String ADMIN_ID = "b9sEJWcoBagpvndBwwHWwmP9khr1";
    public static User adminAccount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        sharedPreferences = getApplicationContext().getSharedPreferences(SHARE_PREF_NAME, Context.MODE_PRIVATE);

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("Users/b9sEJWcoBagpvndBwwHWwmP9khr1");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                adminAccount = snapshot.getValue(User.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        boolean isLogin =  sharedPreferences.getBoolean(IS_LOGIN,false);

        if(isLogin) {
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
        } else {
            startActivity(new Intent(SplashActivity.this,LoginActivity.class));
        }
        finish();
    }
}