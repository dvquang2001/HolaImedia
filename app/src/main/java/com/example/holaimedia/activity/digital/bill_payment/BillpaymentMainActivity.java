package com.example.holaimedia.activity.digital.bill_payment;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.holaimedia.R;
import com.example.holaimedia.activity.digital.user_water.AddUserWaterActivity;

public class BillpaymentMainActivity extends AppCompatActivity {
    Button btn_pm_elec,btn_pm_water,btn_pm_internet,btn_add_user_water;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_billpayment_main);
        btn_pm_elec=findViewById(R.id.btn_pm_elec);
        btn_pm_water=findViewById(R.id.btn_pm_water);
        btn_add_user_water=findViewById(R.id.btn_add_user_water);
        btn_pm_internet=findViewById(R.id.btn_pm_internet);
        btn_pm_water.setOnClickListener(v -> {
            Intent i=new Intent(getApplicationContext(),BillpaymentwaterMainActivity.class);
            startActivity(i);
        });
        btn_add_user_water.setOnClickListener(v -> {
            Intent intent=new Intent(getApplicationContext(), AddUserWaterActivity.class);
            startActivity(intent);
        });

    }
}