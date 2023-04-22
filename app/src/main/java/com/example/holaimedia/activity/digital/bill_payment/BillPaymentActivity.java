package com.example.holaimedia.activity.digital.bill_payment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.holaimedia.R;
import com.example.holaimedia.activity.auth.SplashActivity;
import com.example.holaimedia.model.auth.User;
import com.google.gson.Gson;

public class BillPaymentActivity extends AppCompatActivity {
    private ImageView ivBack, ivAdd;
    private ConstraintLayout layoutWaterBill;
    private User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_billpayment);

        Gson gson = new Gson();
        String userJson = SplashActivity.sharedPreferences.getString(SplashActivity.USER_DATA, null);
        user = gson.fromJson(userJson, User.class);

        initViews();
        initViewListener();
    }

    private void initViews() {
        ivBack = findViewById(R.id.ivBack);
        ivAdd = findViewById(R.id.ivAdd);
        layoutWaterBill = findViewById(R.id.layoutWaterBill);
        ivAdd.setVisibility(user.isAdmin() ? View.VISIBLE : View.GONE);
    }

    private void initViewListener() {
        ivBack.setOnClickListener(view -> finish());
        ivAdd.setOnClickListener(v -> {
            Intent intent=new Intent(getApplicationContext(), WaterPayment.class);
            startActivity(intent);
        });
        layoutWaterBill.setOnClickListener(v -> {
            Intent i=new Intent(getApplicationContext(), BillPaymentWaterActivity.class);
            startActivity(i);
        });
    }
}