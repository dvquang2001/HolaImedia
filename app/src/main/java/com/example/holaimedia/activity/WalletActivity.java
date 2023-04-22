package com.example.holaimedia.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.holaimedia.R;
import com.example.holaimedia.activity.auth.SplashActivity;
import com.example.holaimedia.activity.transaction.NapTienActivity;
import com.example.holaimedia.activity.transaction.RutTienActivity;
import com.example.holaimedia.adapter.digital.TransactionAdapter;
import com.example.holaimedia.model.auth.User;
import com.example.holaimedia.model.digital.Transaction;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class WalletActivity extends AppCompatActivity {
    private ImageView ivBack;
    private TextView tvCurrentBalance;
    private Button btnWithDraw, btnRecharge;
    private RecyclerView rcvTransaction;
    private TransactionAdapter adapter;
    private List<Transaction> transactions;
    private View root;
    private FirebaseFirestore db;
    private Gson gson;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);

        db = FirebaseFirestore.getInstance();

        gson = new Gson();
        String userJson = SplashActivity.sharedPreferences.getString(SplashActivity.USER_DATA, null);
        user = gson.fromJson(userJson, User.class);

        db.collection("Transaction").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {
                    Transaction transaction = documentSnapshot.toObject(Transaction.class);
                    if (transaction.getEmail().equals(user.getEmail()) || user.isAdmin()) {
                        transactions.add(transaction);
                    }
                    adapter.notifyItemInserted(transactions.size() - 1);
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
        tvCurrentBalance = findViewById(R.id.tvCurrentBalance);
        btnWithDraw = findViewById(R.id.btnWithDrawMoney);
        btnRecharge = findViewById(R.id.btnRecharge);
        rcvTransaction = findViewById(R.id.rvTransaction);

        rcvTransaction.setLayoutManager(new LinearLayoutManager(this));
        transactions = new ArrayList<>();
        adapter = new TransactionAdapter(transactions, user.isAdmin());
        rcvTransaction.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    @SuppressLint("SetTextI18n")
    private void initData() {
        String userJson = SplashActivity.sharedPreferences.getString(SplashActivity.USER_DATA, null);
        user = gson.fromJson(userJson, User.class);
        tvCurrentBalance.setText(user.getAccount().toString());
    }

    private void initViewListener() {
        ivBack.setOnClickListener(view -> finish());
        btnWithDraw.setOnClickListener(view -> startActivity(new Intent(this, RutTienActivity.class)));
        btnRecharge.setOnClickListener(view -> startActivity(new Intent(this, NapTienActivity.class)));
    }
}