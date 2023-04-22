package com.example.holaimedia.activity.digital.bill_payment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.holaimedia.R;
import com.example.holaimedia.adapter.digital.UserWaterAdapter;
import com.example.holaimedia.model.digital.Watercustomer;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class BillPaymentWaterActivity extends AppCompatActivity {
    private ImageView ivBack;
    private RecyclerView rcvUsers;
    private EditText etSearch;
    private UserWaterAdapter mUserAdapter;
    private DatabaseReference myRef;
    private List<Watercustomer> mListUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_billpayment_water);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Paybill");

        initViews();
        initViewListener();
    }

    private void initViews() {
        ivBack = findViewById(R.id.ivBack);
        etSearch = findViewById(R.id.etSearch);
        rcvUsers = findViewById(R.id.rcvUser);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcvUsers.setLayoutManager(linearLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        rcvUsers.addItemDecoration(dividerItemDecoration);
        mListUsers = new ArrayList<>();
        mUserAdapter = new UserWaterAdapter(this, mListUsers);
        rcvUsers.setAdapter(mUserAdapter);
        getListUserFromRealTimeDatabase();
    }

    private void initViewListener() {
        ivBack.setOnClickListener(view -> finish());
    }

    private void getListUserFromRealTimeDatabase() {
        myRef.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Watercustomer user = dataSnapshot.getValue(Watercustomer.class);
                    mListUsers.add(user);
                }
                mUserAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(BillPaymentWaterActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mUserAdapter.getFilter().filter(s);
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });

    }
}
