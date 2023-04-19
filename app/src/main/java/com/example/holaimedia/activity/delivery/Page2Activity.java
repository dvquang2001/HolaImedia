package com.example.holaimedia.activity.delivery;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.holaimedia.R;
import com.example.holaimedia.activity.MainActivity;
import com.example.holaimedia.activity.auth.SplashActivity;
import com.example.holaimedia.model.digital.Transaction;
import com.example.holaimedia.model.auth.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class Page2Activity extends AppCompatActivity {
    private TextView tvDiaChiGui, tvDiaChiNhan, tvKhoangCach, tvThanhTien;
    private EditText etTenNguoiNhan, etSoDienThoai, etKhoiLuong;
    private Button btnDat;
    private FirebaseAuth auth;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseFirestore db;
    private String userID;
    private String adminID;
    private User user;
    private Gson gson;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page2);

        auth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        db = FirebaseFirestore.getInstance();

        gson = new Gson();

        String userJson = SplashActivity.sharedPreferences.getString(SplashActivity.USER_DATA, null);
        userID = SplashActivity.sharedPreferences.getString(SplashActivity.USER_ID, null);
        adminID = SplashActivity.ADMIN_ID;
        user = gson.fromJson(userJson, User.class);


        initViews();
        initViewListener();
    }

    @SuppressLint("SetTextI18n")
    private void initViews() {
        tvDiaChiGui = findViewById(R.id.tvDiaChiGui);
        tvDiaChiNhan = findViewById(R.id.tvDiaChiNhan);
        tvKhoangCach = findViewById(R.id.tvKhoangCach);
        tvThanhTien = findViewById(R.id.tvThanhTien);
        etTenNguoiNhan = findViewById(R.id.etTenNguoNhan);
        etSoDienThoai = findViewById(R.id.etSoDienThoai);
        etKhoiLuong = findViewById(R.id.etKhoiLuong);
        btnDat = findViewById(R.id.btnDat);

        String diaChiGui = getIntent().getExtras().getString("dia_chi_gui");
        String diaChiNhan = getIntent().getExtras().getString("dia_chi_nhan");

        tvDiaChiGui.setText(diaChiGui);
        tvDiaChiNhan.setText(diaChiNhan);

        String quanGui = getIntent().getExtras().getString("quan_gui");
        String quanNhan = getIntent().getExtras().getString("quan_nhan");

        //thanh xuan
        if (quanGui.equalsIgnoreCase("Quận Thanh Xuân")) {
            switch (quanNhan) {
                case "Quận Hoàng Mai":
                    tvKhoangCach.setText("3");
                    break;
                case "Quận Hai Bà Trưng":
                case "Quận Hoàn Kiếm":
                    tvKhoangCach.setText("4");
                    break;
                case "Quận Tây Hồ":
                    tvKhoangCach.setText("10");
                    break;
                case "Quận Đống Đa":
                    tvKhoangCach.setText("6");
                    break;
                case "Quận Cầu Giấy":
                    tvKhoangCach.setText("5");
                    break;
            }
        }

        //hoang mai
        if (quanGui.equalsIgnoreCase("Quận Hoàng Mai")) {
            switch (quanNhan) {
                case "Quận Thanh Xuân":
                    tvKhoangCach.setText("3");
                    break;
                case "Quận Hai Bà Trưng":
                case "Quận Đống Đa":
                case "Quận Cầu Giấy":
                    tvKhoangCach.setText("4");
                    break;
                case "Quận Hoàn Kiếm":
                    tvKhoangCach.setText("7");
                    break;
                case "Quận Tây Hồ":
                    tvKhoangCach.setText("13");
                    break;
            }
        }

        //hai ba trung
        if (quanGui.equalsIgnoreCase("Quận Hai Bà Trưng")) {
            switch (quanNhan) {
                case "Quận Hoàng Mai":
                case "Quận Hoàn Kiếm":
                case "Quận Cầu Giấy":
                    tvKhoangCach.setText("3");
                    break;
                case "Quận Thanh Xuân":
                case "Quận Đống Đa":
                    tvKhoangCach.setText("6");
                    break;
                case "Quận Tây Hồ":
                    tvKhoangCach.setText("4");
                    break;
            }
        }

        //hoan kiem
        if (quanGui.equalsIgnoreCase("Quận Hoàn Kiếm")) {
            switch (quanNhan) {
                case "Quận Hoàng Mai":
                    tvKhoangCach.setText("8");
                    break;
                case "Quận Hai Bà Trưng":
                case "Quận Cầu Giấy":
                    tvKhoangCach.setText("3");
                    break;
                case "Quận Thanh Xuân":
                    tvKhoangCach.setText("11");
                    break;
                case "Quận Tây Hồ":
                    tvKhoangCach.setText("6");
                    break;
                case "Quận Đống Đa":
                    tvKhoangCach.setText("4");
                    break;
            }
        }

        //Tây ho
        if (quanGui.equalsIgnoreCase("Quận Tây Hồ")) {
            switch (quanNhan) {
                case "Quận Hoàng Mai":
                    tvKhoangCach.setText("7");
                    break;
                case "Quận Hai Bà Trưng":
                case "Quận Cầu Giấy":
                    tvKhoangCach.setText("3");
                    break;
                case "Quận Hoàn Kiếm":
                    tvKhoangCach.setText("6");
                    break;
                case "Quận Thanh Xuân":
                    tvKhoangCach.setText("11");
                    break;
                case "Quận Đống Đa":
                    tvKhoangCach.setText("8");
                    break;
            }
        }

        //cau giay
        if (quanGui.equalsIgnoreCase("Quận Cầu Giấy")) {
            switch (quanNhan) {
                case "Quận Hoàng Mai":
                    tvKhoangCach.setText("7");
                    break;
                case "Quận Hai Bà Trưng":
                case "Quận Đống Đa":
                    tvKhoangCach.setText("6");
                    break;
                case "Quận Hoàn Kiếm":
                    tvKhoangCach.setText("5");
                    break;
                case "Quận Tây Hồ":
                    tvKhoangCach.setText("3");
                    break;
                case "Quận Thanh Xuân":
                    tvKhoangCach.setText("8");
                    break;
            }
        }

        int khoangcach = Integer.parseInt(tvKhoangCach.getText().toString());
        int thanhTien = khoangcach * 5000; // 5000 / 1km
        tvThanhTien.setText(thanhTien+ "");
    }

    private boolean validateEditText() {
        return !etSoDienThoai.getText().toString().trim().isEmpty()
                && !etTenNguoiNhan.getText().toString().trim().isEmpty()
                && !etKhoiLuong.getText().toString().trim().isEmpty();
    }

    @SuppressLint("SetTextI18n,SimpleDateFormat")
    private void initViewListener() {
        btnDat.setOnClickListener(v -> {
            if (!validateEditText()) {
                Toast.makeText(this, "Bạn chưa điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                return;
            }

            String sdt = etSoDienThoai.getText().toString();
            String thanhTien = tvThanhTien.getText().toString();
            String tenNguoiNhan = etTenNguoiNhan.getText().toString();
            String khoiLuong = etKhoiLuong.getText().toString();

            Calendar calendar = Calendar.getInstance();

            SimpleDateFormat currentDate = new SimpleDateFormat("MM dd, yyyy");
            String saveCurrentDate = currentDate.format(calendar.getTime());

            SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
            String saveCurrentTime = currentTime.format(calendar.getTime());

            auth.addAuthStateListener(firebaseAuth -> {
                firebaseDatabase.getReference().child("Delivery").child(sdt).child("Tien").setValue(thanhTien);
                firebaseDatabase.getReference().child("Delivery").child(sdt).child("Hoten").setValue(tenNguoiNhan);
                firebaseDatabase.getReference().child("Delivery").child(sdt).child("Khoiluong").setValue(khoiLuong);
                firebaseDatabase.getReference().child("Delivery").child(sdt).child("Ngay").setValue(saveCurrentDate);
                firebaseDatabase.getReference().child("Delivery").child(sdt).child("Gio").setValue(saveCurrentTime);
            });

            Long userBalance = user.getAccount();
            Long totalMoney = Long.parseLong(thanhTien);
            if (userBalance < totalMoney) {
                Toast.makeText(Page2Activity.this, "Bạn không đủ tiền để thanh toán", Toast.LENGTH_SHORT).show();
                return;
            }
            userBalance = userBalance - totalMoney;
            user.setAccount(userBalance);
            firebaseDatabase.getReference().child("Users").child(userID).setValue(user).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    saveUserData();
                    Toast.makeText(Page2Activity.this, "Thanh toán thành công", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Page2Activity.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                }
            });
            Long adminBalance = SplashActivity.adminAccount.getAccount();
            createTransaction(totalMoney);
            adminBalance += totalMoney;
            SplashActivity.adminAccount.setAccount(adminBalance);
            firebaseDatabase.getReference().child("Users").child(adminID).setValue(SplashActivity.adminAccount);

            Toast.makeText(getApplicationContext(), "Đặt đơn thành công", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, MainActivity.class));
            finish();
        });
    }

    private void createTransaction(long totalMoney) {
        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MM dd, yyyy");
        String saveCurrentDate = currentDate.format(calendar.getTime());
        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        String saveCurrentTime = currentTime.format(calendar.getTime());
        String transactionDate = saveCurrentTime + " - " + saveCurrentDate;
        Transaction transaction = new Transaction(user.getEmail(), totalMoney, transactionDate);

        final HashMap<String, Object> transactionMap = new HashMap<>();
        transactionMap.put("email", transaction.getEmail());
        transactionMap.put("money", transaction.getMoney());
        transactionMap.put("transactionDate", transaction.getTransactionDate());

        db.collection("Transaction").add(transactionMap);
    }

    private void saveUserData() {
        String userJson = gson.toJson(user);
        SplashActivity.sharedPreferences.edit().putString(SplashActivity.USER_DATA, userJson).apply();
    }
}