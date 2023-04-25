package com.example.holaimedia.activity.delivery;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.holaimedia.R;
import com.example.holaimedia.activity.auth.SplashActivity;
import com.example.holaimedia.adapter.delivery.OrderShipAdapter;
import com.example.holaimedia.model.delivery.OrderManagement;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DeliveryOrderManagementActivity extends AppCompatActivity {
    private RecyclerView rcvUsers;
    private ImageView ivBack,ivEmptyList;
    private OrderShipAdapter mUserAdapter;
    private DatabaseReference myRef;
    private List<OrderManagement> mListUsers;
    private String userID;
    private TextView tvEmptyList;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_order_management);

        userID = SplashActivity.sharedPreferences.getString(SplashActivity.USER_ID, null);

        ivBack = findViewById(R.id.ivBack);
        ivEmptyList = findViewById(R.id.ivEmptyList);
        tvEmptyList = findViewById(R.id.tvEmptyList);
        progressBar = findViewById(R.id.progressBar);

        rcvUsers = findViewById(R.id.rcvOrderShip);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcvUsers.setLayoutManager(linearLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        rcvUsers.addItemDecoration(dividerItemDecoration);
        mListUsers = new ArrayList<>();
        mUserAdapter = new OrderShipAdapter(this, mListUsers);
        rcvUsers.setAdapter(mUserAdapter);

        progressBar.setVisibility(View.VISIBLE);
        getListUserFromRealTimeDatabase();

        ivBack.setOnClickListener(view -> finish());
    }

    private void getListUserFromRealTimeDatabase() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Delivery");
        myRef.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    OrderManagement user = dataSnapshot.getValue(OrderManagement.class);
                    if(user.getUserId() != null && user.getUserId().equals(userID)) {
                        mListUsers.add(user);
                    }
                }
                mUserAdapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);
                setupRecyclerView();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    private void setupRecyclerView() {
        if(mListUsers.isEmpty()) {
            rcvUsers.setVisibility(View.GONE);
            tvEmptyList.setVisibility(View.VISIBLE);
            ivEmptyList.setVisibility(View.VISIBLE);
        } else {
            rcvUsers.setVisibility(View.VISIBLE);
            tvEmptyList.setVisibility(View.GONE);
            ivEmptyList.setVisibility(View.GONE);
        }
    }
}
