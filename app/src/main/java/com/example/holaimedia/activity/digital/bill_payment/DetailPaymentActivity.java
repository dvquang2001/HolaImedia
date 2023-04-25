package com.example.holaimedia.activity.digital.bill_payment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.holaimedia.R;
import com.example.holaimedia.activity.auth.SplashActivity;
import com.example.holaimedia.model.auth.User;
import com.example.holaimedia.model.digital.Transaction;
import com.example.holaimedia.model.digital.Watercustomer;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class DetailPaymentActivity extends AppCompatActivity {
    private TextView tvCustomerId, tvCustomerName, tvCustomerAddress, tvCustomerPhoneNumber, tvCustomerDatePayment, tvMoney;
    private EditText etPinCode;
    private Button btnPay;
    private ImageView ivBack;
    private FirebaseDatabase database;
    private FirebaseFirestore db;
    private User user1;
    private Watercustomer user;
    private String userID;
    private Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailpayment);
        Bundle bundle = getIntent().getExtras();
        db = FirebaseFirestore.getInstance();
        database = FirebaseDatabase.getInstance();

        gson = new Gson();
        String userJson = SplashActivity.sharedPreferences.getString(SplashActivity.USER_DATA, null);
        userID = SplashActivity.sharedPreferences.getString(SplashActivity.USER_ID, null);
        user1 = gson.fromJson(userJson, User.class);

        if (bundle != null) {
            user = (Watercustomer) bundle.get("object_user");
        }

        initViews();
        initData();
        initViewListener();
    }

    private void initViews() {
        ivBack = findViewById(R.id.ivBack);
        tvCustomerId = findViewById(R.id.tvCustomerId);
        tvCustomerName = findViewById(R.id.tvCustomerName);
        tvCustomerAddress = findViewById(R.id.tvCustomerAddress);
        tvCustomerPhoneNumber = findViewById(R.id.tvPhoneNumber);
        tvCustomerDatePayment = findViewById(R.id.tvDatePayment);
        tvMoney = findViewById(R.id.tvMoney);
        etPinCode = findViewById(R.id.etPinCode);
        btnPay = findViewById(R.id.btnPay);
    }

    private void initData() {
        tvCustomerId.setText(user.getMa());
        tvCustomerName.setText(user.getTen());
        tvCustomerAddress.setText(user.getDiachi());
        tvCustomerPhoneNumber.setText(user.getSodienthoai());
        tvCustomerDatePayment.setText(user.getKyhan());
        tvMoney.setText(user.getTien());
    }

    private void initViewListener() {
        ivBack.setOnClickListener(view -> finish());
        btnPay.setOnClickListener(view -> payBill());
    }

    private void payBill() {
        Date currentTime = Calendar.getInstance().getTime();
        String pinCode = etPinCode.getText().toString().trim();
        if (pinCode.equals(user1.getPinCode())) {
            DatabaseReference myRef = database.getReference("Paybill");
            user.setTien("0");
            myRef.child(String.valueOf(user.getMa())).updateChildren(user.toMap());
            updateUserAccount(tvMoney.getText().toString().trim());
            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(DetailPaymentActivity.this)
                    .setTitle("Phiếu thanh toán")
                    .setMessage("Giá: " + tvMoney.getText().toString() + "\n" + "Date: " + currentTime)
                    .setPositiveButton("OK", (dialogInterface, i) -> {
                        dialogInterface.dismiss();
                        Toast.makeText(DetailPaymentActivity.this, "Cảm ơn đã sử dụng dịch vụ", Toast.LENGTH_LONG).show();
                        finish();
                    })
                    .setNegativeButton("Cancel", (dialogInterface, i) -> {
                        dialogInterface.dismiss();
                    });
            AlertDialog alertDialog = alertBuilder.create();
            alertDialog.show();
            Toast.makeText(DetailPaymentActivity.this, "Thanh toán thành công", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "Bạn nhập sai mã", Toast.LENGTH_SHORT).show();

        }
    }

    private void updateUserAccount(String strMenhGia) {
        int totalMoney = Integer.parseInt(strMenhGia.replace(".", ""));

        long userBalance = user1.getAccount();
        if (userBalance < totalMoney) {
            Toast.makeText(this, "Bạn không đủ tiền để thanh toán", Toast.LENGTH_SHORT).show();
            return;
        }
        userBalance = userBalance - totalMoney;
        user1.setAccount(userBalance);
        database.getReference().child("Users").child(userID).setValue(user1).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                saveUserData();
                Toast.makeText(this, "Thanh toán thành công", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, task.getException().toString(), Toast.LENGTH_SHORT).show();
            }
        });
        long adminBalance = SplashActivity.adminAccount.getAccount();
        createTransaction(totalMoney);
        adminBalance += totalMoney;
        SplashActivity.adminAccount.setAccount(adminBalance);
        database.getReference().child("Users").child(SplashActivity.ADMIN_ID).setValue(SplashActivity.adminAccount);
    }

    @SuppressLint("SimpleDateFormat")
    private void createTransaction(long totalMoney) {
        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MM dd, yyyy");
        String saveCurrentDate = currentDate.format(calendar.getTime());
        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        String saveCurrentTime = currentTime.format(calendar.getTime());
        String transactionDate = saveCurrentTime + " - " + saveCurrentDate;

        Transaction transaction = new Transaction(user1.getEmail(), totalMoney, transactionDate);

        final HashMap<String, Object> transactionMap = new HashMap<>();
        transactionMap.put("email", transaction.getEmail());
        transactionMap.put("money", transaction.getMoney());
        transactionMap.put("transactionDate", transaction.getTransactionDate());
        db.collection("Transaction").add(transactionMap);
    }

    private void saveUserData() {
        String userJson = gson.toJson(user1);
        SplashActivity.sharedPreferences.edit().putString(SplashActivity.USER_DATA, userJson).apply();
    }
}
