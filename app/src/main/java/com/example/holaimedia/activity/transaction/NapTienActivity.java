package com.example.holaimedia.activity.transaction;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.holaimedia.R;
import com.example.holaimedia.activity.auth.SplashActivity;
import com.example.holaimedia.model.auth.User;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import java.util.Calendar;
import java.util.Date;

public class NapTienActivity extends AppCompatActivity {
    private ImageView ivBack;
    private EditText etAmountOfMoney, etTransactionName, etCardNumber, etEffectiveDate;
    private Button btnNapTien;
    private AlertDialog.Builder alertBuilder;
    private long currentBalance = 0L;
    private User user;
    private String userID;
    private Gson gson;
    private FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_bank);

        database = FirebaseDatabase.getInstance();
        gson = new Gson();

        String userJson = SplashActivity.sharedPreferences.getString(SplashActivity.USER_DATA, null);
        userID = SplashActivity.sharedPreferences.getString(SplashActivity.USER_ID, null);
        user = gson.fromJson(userJson, User.class);

        currentBalance = user.getAccount();

        initViews();
        initViewListener();
    }

    private void initViews() {
        ivBack = findViewById(R.id.ivBack);
        etAmountOfMoney = findViewById(R.id.etAmountOfMoney);
        etTransactionName = findViewById(R.id.etTransactionName);
        etCardNumber = findViewById(R.id.etCardNumber);
        etEffectiveDate = findViewById(R.id.etEffectiveDate);
        btnNapTien = findViewById(R.id.btnTransaction);
        btnNapTien.setText(getString(R.string.recharge));
    }

    private void initViewListener() {
        ivBack.setOnClickListener(view -> finish());
        btnNapTien.setOnClickListener(v -> {
            currentBalance = currentBalance + Integer.parseInt(etAmountOfMoney.getText().toString());
            int amountOfMoney = Integer.parseInt(etAmountOfMoney.getText().toString());

            user.setAccount(currentBalance);
            database.getReference().child("Users").child(userID).setValue(user).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    user.setAccount(currentBalance);
                    saveUserData();
                    Toast.makeText(NapTienActivity.this, "Nạp tiền thành công", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(NapTienActivity.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                }
            });
            Date currentTime = Calendar.getInstance().getTime();

            alertBuilder = new AlertDialog.Builder(NapTienActivity.this);
            alertBuilder.setTitle(" Loai dich vu: Nap tien");
            alertBuilder.setMessage("So tien: " + amountOfMoney + "\n" +
                    "Ho va ten: " + etTransactionName.getText().toString() + "\n" +
                    "So the: " + etCardNumber.getText().toString() + "\n" +
                    "Ngay hieu luc: " + etEffectiveDate.getText().toString() + "\n" +
                    "Thoi gian: " + currentTime.getDate() + currentTime.getTime()
            );
            alertBuilder.setPositiveButton("Ok", (dialogInterface, i) -> {
                dialogInterface.dismiss();
                Toast.makeText(NapTienActivity.this, "Cảm ơn đã sử dụng dịch vụ", Toast.LENGTH_LONG).show();
            });
            alertBuilder.setNegativeButton("Cancel", (dialogInterface, i) -> dialogInterface.dismiss());
            AlertDialog alertDialog = alertBuilder.create();
            alertDialog.show();

        });
    }

    private void saveUserData() {
        String userJson = gson.toJson(user);
        SplashActivity.sharedPreferences.edit().putString(SplashActivity.USER_DATA, userJson).apply();
    }
}