package com.example.holaimedia.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.holaimedia.R;
import com.example.holaimedia.activity.auth.SplashActivity;
import com.example.holaimedia.model.food.Cart;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class PlacedOrderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_placed_order);

        String userID = SplashActivity.sharedPreferences.getString(SplashActivity.USER_ID, null);

        FirebaseFirestore fireStore = FirebaseFirestore.getInstance();
        List<Cart> list = (ArrayList<Cart>) getIntent().getSerializableExtra("itemList");

        Button btnOk = findViewById(R.id.btnOk);
        btnOk.setOnClickListener(view -> {
            finish();
            startActivity(new Intent(PlacedOrderActivity.this, MainActivity.class));
        });
        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());

        if (list != null && list.size() > 0) {
            for (Cart cart : list) {
                final HashMap<String, Object> cartMap = new HashMap<>();
                cartMap.put("userId", userID);
                cartMap.put("productName", cart.getProductName());
                cartMap.put("productPrice", cart.getProductPrice());
                cartMap.put("currentDate", currentDate);
                cartMap.put("imageUrl", cart.getImageUrl());
                cartMap.put("currentTime", currentTime);
                cartMap.put("totalQuantity", cart.getTotalQuantity());
                cartMap.put("totalPrice", cart.getTotalPrice());

                fireStore.collection("Order").add(cartMap).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        fireStore.collection("Cart").document(cart.getDocumentId()).delete();
                    }
                });
                Toast.makeText(PlacedOrderActivity.this, "Đơn hàng của bạn đã được thực hiện", Toast.LENGTH_SHORT).show();
            }
        }
    }
}