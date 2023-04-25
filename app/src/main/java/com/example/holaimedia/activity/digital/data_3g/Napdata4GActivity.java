package com.example.holaimedia.activity.digital.data_3g;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.holaimedia.R;
import com.example.holaimedia.activity.auth.SplashActivity;
import com.example.holaimedia.adapter.digital.CardAdapter;
import com.example.holaimedia.adapter.digital.NetworkAdapter;
import com.example.holaimedia.adapter.digital.OnClickCardSelected;
import com.example.holaimedia.adapter.digital.OnClickNetworkSelected;
import com.example.holaimedia.model.auth.User;
import com.example.holaimedia.model.digital.Card;
import com.example.holaimedia.model.digital.Transaction;
import com.example.holaimedia.utils.CenteredRecyclerView;
import com.example.holaimedia.utils.GridSpacingItemDecoration;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@SuppressLint("UseCompatLoadingForDrawables")
public class Napdata4GActivity extends AppCompatActivity implements OnClickCardSelected, OnClickNetworkSelected {
    private ImageView ivBack;
    private CenteredRecyclerView rvNetwork, rvCard;
    private Button btnThanhToan;
    private TextView tvLoaiThe, tvMenhGia, tvGoiCuoc;
    private EditText edtPhoneNumber;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseFirestore db;
    private User user;
    private long currentBalance = 0L;
    private String userID;
    private String adminID;
    private Gson gson;
    private AlertDialog.Builder alertBuilder;
    private List<Card> networks = new ArrayList<>();
    private List<Card> cardList = new ArrayList<>();
    private NetworkAdapter networkAdapter;
    private CardAdapter cardAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_napdata4_gactivity);

        db = FirebaseFirestore.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();

        gson = new Gson();
        String userJson = SplashActivity.sharedPreferences.getString(SplashActivity.USER_DATA, null);
        userID = SplashActivity.sharedPreferences.getString(SplashActivity.USER_ID, null);
        adminID = SplashActivity.ADMIN_ID;
        user = gson.fromJson(userJson, User.class);
        currentBalance = user.getAccount();

        initData();
        initViews();
        initViewListener();
    }

    private void initData() {
        networks.add(new Card(getString(R.string.viettel_card), 0));
        networks.add(new Card(getString(R.string.mobiphone_card), 0));
        networks.add(new Card(getString(R.string.vinaphone_card), 0));

        cardList.add(new Card(getString(R.string.mobile_data_1), 3000));
        cardList.add(new Card(getString(R.string.mobile_data_2), 10000));
        cardList.add(new Card(getString(R.string.mobile_data_3), 40000));
        cardList.add(new Card(getString(R.string.mobile_data_4), 70000));
        cardList.add(new Card(getString(R.string.mobile_data_5), 90000));
        cardList.add(new Card(getString(R.string.mobile_data_6), 125000));
        cardList.add(new Card(getString(R.string.mobile_data_7), 230000));
        cardList.add(new Card(getString(R.string.mobile_data_8), 5000));
        cardList.add(new Card(getString(R.string.mobile_data_9), 9000));
        cardList.add(new Card(getString(R.string.mobile_data_10), 17000));
        cardList.add(new Card(getString(R.string.mobile_data_11), 10000));
        cardList.add(new Card(getString(R.string.mobile_data_12), 15000));
    }

    @Override
    public void execute(int money, String name) {
        tvGoiCuoc.setText(name.substring(0, 13));
        tvMenhGia.setText(String.valueOf(money));
    }

    @Override
    public void executeNetwork(String name) {
        tvLoaiThe.setText(name);
    }

    private boolean validateConditions(String goicuoc, String loaithe, String gia) {
        if (loaithe.trim().isEmpty()) {
            Toast.makeText(this, "Vui lòng chọn nhà mạng", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (goicuoc.isEmpty() || gia.trim().isEmpty() || gia.trim().equals("0")) {
            Toast.makeText(this, "Vui lòng chọn mệnh gói cước", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void initViews() {
        int spacing = getResources().getDimensionPixelSize(R.dimen.grid_spacing);
        boolean includeEdge = true;
        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return 1;
            }
        });

        rvNetwork = findViewById(R.id.rvNetwork);
        rvNetwork.setLayoutManager(layoutManager);
        rvNetwork.addItemDecoration(new GridSpacingItemDecoration(3, spacing, includeEdge));
        networkAdapter = new NetworkAdapter(this, networks, this);
        rvNetwork.setAdapter(networkAdapter);


        GridLayoutManager layoutManager1 = new GridLayoutManager(this, 3);
        layoutManager1.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return 1;
            }
        });
        rvCard = findViewById(R.id.rvCard);
        rvCard.setLayoutManager(layoutManager1);
        rvCard.addItemDecoration(new GridSpacingItemDecoration(3, spacing, includeEdge));
        cardAdapter = new CardAdapter(this, cardList, this);
        rvCard.setAdapter(cardAdapter);

        ivBack = findViewById(R.id.ivBack);
        btnThanhToan = findViewById(R.id.btnThanhToan);
        tvLoaiThe = findViewById(R.id.tvLoaiThe);
        tvMenhGia = findViewById(R.id.tvMenhGia);
        tvGoiCuoc = findViewById(R.id.tvGoiCuoc);
        edtPhoneNumber = findViewById(R.id.edtPhoneNumber);
    }

    @SuppressLint("SetTextI18n")
    private void initViewListener() {
        ivBack.setOnClickListener(view -> finish());
        Date currentTime = Calendar.getInstance().getTime();
        btnThanhToan.setOnClickListener(view -> {
            String loaiThe = tvLoaiThe.getText().toString();
            String goicuoc = tvGoiCuoc.getText().toString();
            String gia = tvMenhGia.getText().toString();
            String sdt = edtPhoneNumber.getText().toString();


           /* Intent intent = new Intent(getApplicationContext(), CheckFace_NapdataActivity.class);
            intent.putExtra("goi", goicuoc);
            intent.putExtra("gia", gia);
            startActivity(intent);*/
            Intent intent = new Intent(getApplicationContext(), CheckFacePaymentActivity.class);
            intent.putExtra("goi", goicuoc);
            intent.putExtra("gia", gia);
            intent.putExtra("sdt", sdt);
            startActivity(intent);
/*
            if(!validateConditions(goicuoc,loaiThe,gia)) {
                return;
            }
            updateUserAccount(tvMenhGia.getText().toString().trim());
            alertBuilder = new AlertDialog.Builder(this);
            alertBuilder.setTitle("Thông tin");
            alertBuilder.setMessage("Nhà mạng: " + tvLoaiThe.getText() + "\n" +
                    "Gói cước: " + tvGoiCuoc.getText() + "\n" +
                    "Giá: " + tvMenhGia.getText() + "\n" +
                    "Số điện thoại: " + edtPhoneNumber.getText().toString().trim() + "\n" +
                    "Ngày mua: " + currentTime

            );
            alertBuilder.setPositiveButton("Ok", (dialogInterface, i) -> {
                dialogInterface.dismiss();
                finish();
                Toast.makeText(this, "Cảm ơn đã sự dụng dịch vụ", Toast.LENGTH_LONG).show();
            });
            alertBuilder.setNegativeButton("Cancel", (dialogInterface, i) -> dialogInterface.dismiss());
            AlertDialog alertDialog = alertBuilder.create();
            alertDialog.show();*/
        });
    }

    private void updateUserAccount(String strMenhGia) {
        int totalMoney = Integer.parseInt(strMenhGia.replace(".", ""));

        Long userBalance = user.getAccount();
        if (userBalance < totalMoney) {
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
    }

    @SuppressLint("SimpleDateFormat")
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        tvLoaiThe.setText("");
        tvMenhGia.setText("");
        edtPhoneNumber.setText("");
    }
}