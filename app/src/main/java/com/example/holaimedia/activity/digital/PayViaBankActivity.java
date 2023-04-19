package com.example.holaimedia.activity.digital;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.holaimedia.R;
import com.example.holaimedia.activity.auth.SplashActivity;
import com.example.holaimedia.model.digital.Transaction;
import com.example.holaimedia.model.auth.User;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;

public class PayViaBankActivity extends AppCompatActivity {
    TextView tvNhaMang, tvMenhGia;
    Button btnPay;
    EditText etTransactionName, etCardNumber, etEffectiveDate;
    AlertDialog.Builder alertBuilder;
    private FirebaseFirestore db;
    private FirebaseDatabase firebaseDatabase;
    private String userID;
    private String adminID;
    private User user;
    private Gson gson;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_via_bank);

        db = FirebaseFirestore.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();

        gson = new Gson();

        String userJson = SplashActivity.sharedPreferences.getString(SplashActivity.USER_DATA, null);
        userID = SplashActivity.sharedPreferences.getString(SplashActivity.USER_ID, null);
        adminID = SplashActivity.ADMIN_ID;
        user = gson.fromJson(userJson, User.class);

        initViews();
        initData();
        initViewListener();
    }

    private void initViews() {
        tvNhaMang = findViewById(R.id.tvNhaMang);
        tvMenhGia = findViewById(R.id.tvMenhGia);
        etTransactionName = findViewById(R.id.etTransactionName);
        etCardNumber = findViewById(R.id.etCardNumber);
        etEffectiveDate = findViewById(R.id.etEffectiveDate);
        btnPay = findViewById(R.id.btnPay);
    }

    private void initData() {
        String loaiThe = getIntent().getExtras().getString(SplashActivity.LOAI_THE);
        String gia = getIntent().getExtras().getString(SplashActivity.MENH_GIA);

        tvNhaMang.setText(loaiThe);
        tvMenhGia.setText(gia);
    }

    private void initViewListener() {
        Random random = new Random();
        int seri = random.nextInt(1000000);
        int pin = random.nextInt(1000000000);
        Date currentTime = Calendar.getInstance().getTime();
        btnPay.setOnClickListener(v -> {
            updateUserAccount(tvMenhGia.getText().toString().trim());
            alertBuilder = new AlertDialog.Builder(PayViaBankActivity.this);
            alertBuilder.setTitle("Thông tin thẻ");
            alertBuilder.setMessage("Nhà mạng: " + tvNhaMang.getText() + "\n" +
                    "Giá: " + tvMenhGia.getText() + "\n" +
                    "Seri: " + seri+ "\n" +
                    "Pin: " + pin + "\n" +
                    "Ngày mua: " + currentTime

            );
            alertBuilder.setPositiveButton("Ok", (dialogInterface, i) -> {
                dialogInterface.dismiss();
                Toast.makeText(PayViaBankActivity.this, "Cảm ơn đã sự dụng dịch vụ", Toast.LENGTH_LONG).show();
            });
            alertBuilder.setNegativeButton("Cancel", (dialogInterface, i) -> dialogInterface.dismiss());
            AlertDialog alertDialog = alertBuilder.create();
            alertDialog.show();
        });
    }

    private void updateUserAccount(String strMenhGia) {
        int totalMoney = Integer.parseInt(strMenhGia.replace(".",""));

        Long userBalance = user.getAccount();
        if(userBalance < totalMoney) {
            Toast.makeText(PayViaBankActivity.this, "Bạn không đủ tiền để thanh toán", Toast.LENGTH_SHORT).show();
            return;
        }
        userBalance = userBalance - totalMoney;
        user.setAccount(userBalance);
        firebaseDatabase.getReference().child("Users").child(userID).setValue(user).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                saveUserData();
                Toast.makeText(PayViaBankActivity.this, "Thanh toán thành công", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(PayViaBankActivity.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
            }
        });
        Long adminBalance = SplashActivity.adminAccount.getAccount();
        createTransaction(totalMoney);
        adminBalance += totalMoney;
        SplashActivity.adminAccount.setAccount(adminBalance);
        firebaseDatabase.getReference().child("Users").child(adminID).setValue(SplashActivity.adminAccount);
    }

    private void createTransaction(long totalMoney) {
        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MM dd, yyyy");
        String saveCurrentDate = currentDate.format(calendar.getTime());
        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        String saveCurrentTime = currentTime.format(calendar.getTime());
        String transactionDate = saveCurrentTime + " - " + saveCurrentDate;
        Transaction transaction = new Transaction(user.getEmail(),totalMoney,transactionDate);

        final HashMap<String, Object> transactionMap = new HashMap<>();
        transactionMap.put("email",transaction.getEmail());
        transactionMap.put("money", transaction.getMoney());
        transactionMap.put("transactionDate",transaction.getTransactionDate());

        db.collection("Transaction").add(transactionMap);
    }

    private void saveUserData() {
        String userJson = gson.toJson(user);
        SplashActivity.sharedPreferences.edit().putString(SplashActivity.USER_DATA, userJson).apply();
    }
}