package com.example.holaimedia.activity.food;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.holaimedia.R;
import com.example.holaimedia.activity.PlacedOrderActivity;
import com.example.holaimedia.activity.auth.SplashActivity;
import com.example.holaimedia.adapter.food.CartAdapter;
import com.example.holaimedia.base.BaseFoodActivity;
import com.example.holaimedia.model.food.Cart;
import com.example.holaimedia.model.digital.Transaction;
import com.example.holaimedia.model.auth.User;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class CartActivity extends BaseFoodActivity {

    private FirebaseFirestore db;
    private FirebaseDatabase firebaseDatabase;
    private TextView tvTongTien;
    private Button btnThanhToan;
    private RecyclerView rcvCart;
    private CartAdapter cartAdapter;
    private List<Cart> cartList;
    private String userID;
    private String adminID;
    private User user;
    private Gson gson;

    private ImageView ivBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        db = FirebaseFirestore.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        gson = new Gson();

        String userJson = SplashActivity.sharedPreferences.getString(SplashActivity.USER_DATA, null);
        userID = SplashActivity.sharedPreferences.getString(SplashActivity.USER_ID, null);
        adminID = SplashActivity.ADMIN_ID;
        user = gson.fromJson(userJson, User.class);

        db.collection("Cart").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {
                    String documentId = documentSnapshot.getId();
                    Cart cart = documentSnapshot.toObject(Cart.class);
                    cart.setDocumentId(documentId);
                    if(cart.getProductPrice() != null || cart.getProductName() != null) {
                        if(cart.getUserId().equals(userID) ) {
                            cartList.add(cart);
                        }
                    }
                    cartAdapter.notifyItemInserted(cartList.size() - 1);
                    calculateTotalAmount(cartList);
                }
            } else {
                Toast.makeText(this, "Error" + task.getException(), Toast.LENGTH_SHORT).show();
            }
        });

        initViews();
        initViewListener();
    }

    private void initViews() {
        ivBack = findViewById(R.id.ivBack);
        tvTongTien = findViewById(R.id.tvTongTien);
        btnThanhToan = findViewById(R.id.btnThanhToan);
        rcvCart = findViewById(R.id.rcvCart);

        cartList = new ArrayList<>();
        cartAdapter = new CartAdapter(cartList);
        rcvCart.setLayoutManager(new LinearLayoutManager(this));
        rcvCart.setAdapter(cartAdapter);
    }

    private void initViewListener() {
        ivBack.setOnClickListener(view -> finish());
        btnThanhToan.setOnClickListener(v -> {
            int totalMoney = 0;
            for(Cart cart: cartList) {
                totalMoney += cart.getTotalPrice();
            }

            Long userBalance = user.getAccount();
            if(userBalance < totalMoney) {
                Toast.makeText(this, "Bạn không đủ tiền để thanh toán", Toast.LENGTH_SHORT).show();
                return;
            }
            userBalance = userBalance - totalMoney;
            user.setAccount(userBalance);
            firebaseDatabase.getReference().child("Users").child(userID).setValue(user).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    saveUserData();
                    Toast.makeText(this, "Thanh toán thành công", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                }
            });
            Long adminBalance = SplashActivity.adminAccount.getAccount();
            createTransaction(totalMoney);
            adminBalance += totalMoney;
            SplashActivity.adminAccount.setAccount(adminBalance);
            firebaseDatabase.getReference().child("Users").child(adminID).setValue(SplashActivity.adminAccount);
            Intent intent = new Intent(this, PlacedOrderActivity.class);
            intent.putExtra("itemList", (Serializable) cartList);
            startActivity(intent);
        });
    }

    @SuppressLint("SimpleDateFormat")
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

    @SuppressLint("SetTextI18n")
    private void calculateTotalAmount(List<Cart> cartList) {
        double totalAmount = 0.0;
        for (Cart cart : cartList) {
            totalAmount += cart.getTotalPrice();
        }
        int result = (int) Math.ceil(totalAmount);
        tvTongTien.setText(String.valueOf(result).concat(" VND"));
    }

    private void saveUserData() {
        String userJson = gson.toJson(user);
        SplashActivity.sharedPreferences.edit().putString(SplashActivity.USER_DATA, userJson).apply();
    }
}