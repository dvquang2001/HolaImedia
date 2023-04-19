package com.example.holaimedia.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.holaimedia.R;
import com.example.holaimedia.activity.auth.SplashActivity;
import com.example.holaimedia.model.food.Product;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class DetailActivity extends AppCompatActivity {
    private TextView tvName, tvPrice, tvDesc, tvQuantity, tvTotalPrice;
    private ImageView ivIncrease, ivDecrease, ivProduct;
    private Button btnAddToCart;
    FirebaseFirestore fireStore;
    FirebaseAuth auth;
    Product product = null;
    int totalPrice = 0;
    private String userID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);

        fireStore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        userID = SplashActivity.sharedPreferences.getString(SplashActivity.USER_ID, null);

        initData();
        initViews();
        initViewListener();
    }

    private void initData() {
        final Object object = getIntent().getSerializableExtra("detail");
        if (object instanceof Product) {
            product = (Product) object;
        }
    }

    private void initViews() {
        ivProduct = findViewById(R.id.ivProduct);
        tvName = findViewById(R.id.tvName);
        tvPrice = findViewById(R.id.tvPrice);
        tvDesc = findViewById(R.id.tvDescription);
        tvQuantity = findViewById(R.id.tvQuantity);
        tvTotalPrice = findViewById(R.id.tvTotalPrice);
        ivIncrease = findViewById(R.id.ivIncrease);
        ivDecrease = findViewById(R.id.ivDecrease);
        btnAddToCart = findViewById(R.id.btnAddToCart);

        tvQuantity.setText("0");
        tvTotalPrice.setText(getString(R.string.total_amount).concat("0").concat(" VND"));

        if (product != null) {
            if(product.getImg_url().isEmpty()) {
                ivProduct.setImageResource(R.drawable.imedia);
            } else {
                Glide.with(getApplicationContext()).load(product.getImg_url()).into(ivProduct);
            }
            tvName.setText(product.getName());
            tvDesc.setText(product.getDescription());
            tvPrice.setText(String.valueOf(product.getPrice()).concat(" VND"));
        }
    }

    private void initViewListener() {
        btnAddToCart.setOnClickListener(v -> addedToCart());
        ivIncrease.setOnClickListener(v -> increaseQuantity());
        ivDecrease.setOnClickListener(v -> decreaseQuantity());
    }

    private void increaseQuantity() {
        int quantity = Integer.parseInt(tvQuantity.getText().toString());
        quantity++;
        tvQuantity.setText(String.valueOf(quantity));
        totalPrice = product.getPrice() * quantity;
        tvTotalPrice.setText(getString(R.string.total_amount).concat(String.valueOf(totalPrice)).concat(" VND"));
    }

    private void decreaseQuantity() {
        int quantity = Integer.parseInt(tvQuantity.getText().toString());
        if (quantity == 0) {
            return;
        }
        quantity--;
        tvQuantity.setText(String.valueOf(quantity));
        totalPrice = product.getPrice() * quantity;
        tvTotalPrice.setText(getString(R.string.total_amount).concat(String.valueOf(totalPrice)).concat(" VND"));
    }

    @SuppressLint("SimpleDateFormat")
    private void addedToCart() {
        if(Integer.parseInt(tvQuantity.getText().toString()) == 0) {
            Toast.makeText(DetailActivity.this, "Bạn chưa chọn số lượng mua", Toast.LENGTH_SHORT).show();
            return;
        }
        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MM dd, yyyy");
        String saveCurrentDate = currentDate.format(calendar.getTime());
        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        String saveCurrentTime = currentTime.format(calendar.getTime());

        final HashMap<String, Object> cartMap = new HashMap<>();
        cartMap.put("userId",userID);
        cartMap.put("imageUrl",product.getImg_url());
        cartMap.put("productName", product.getName());
        cartMap.put("productPrice", String.valueOf(product.getPrice()));
        cartMap.put("currentDate", saveCurrentDate);
        cartMap.put("currentTime", saveCurrentTime);
        cartMap.put("totalQuantity", tvQuantity.getText().toString());
        cartMap.put("totalPrice", totalPrice);

        fireStore.collection("Cart").add(cartMap).addOnCompleteListener(task -> {
            Toast.makeText(DetailActivity.this, "Đã thêm vào giỏ hàng", Toast.LENGTH_SHORT).show();
            finish();
        });


    }
}