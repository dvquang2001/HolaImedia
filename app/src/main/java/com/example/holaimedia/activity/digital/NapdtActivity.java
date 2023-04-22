package com.example.holaimedia.activity.digital;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.holaimedia.R;
import com.example.holaimedia.activity.auth.SplashActivity;
import com.example.holaimedia.adapter.digital.CardAdapter;
import com.example.holaimedia.adapter.digital.NetworkAdapter;
import com.example.holaimedia.adapter.digital.OnClickCardSelected;
import com.example.holaimedia.adapter.digital.OnClickNetworkSelected;
import com.example.holaimedia.model.digital.Card;
import com.example.holaimedia.utils.CenteredRecyclerView;
import com.example.holaimedia.utils.GridSpacingItemDecoration;

import java.util.ArrayList;
import java.util.List;

@SuppressLint("UseCompatLoadingForDrawables")
public class NapdtActivity extends AppCompatActivity implements OnClickCardSelected, OnClickNetworkSelected {
    private ImageView ivBack;
    private RecyclerView rvNetwork, rvCard;
    private Button btnThanhToanQuaVi, btnThanhToanNganHang;
    private TextView tvMenhGia, tvLoaiThe;

    private List<Card> networks = new ArrayList<>();
    private List<Card> cardList = new ArrayList<>();
    private NetworkAdapter networkAdapter;
    private CardAdapter cardAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_napdt);

        initData();
        initViews();
        initViewListener();
    }

    private void initData() {
        networks.add(new Card(getString(R.string.viettel_card), 0));
        networks.add(new Card(getString(R.string.mobiphone_card), 0));
        networks.add(new Card(getString(R.string.vinaphone_card), 0));

        cardList.add(new Card(getString(R.string.mobile_card_10), 10000));
        cardList.add(new Card(getString(R.string.mobile_card_20), 20000));
        cardList.add(new Card(getString(R.string.mobile_card_50), 50000));
        cardList.add(new Card(getString(R.string.mobile_card_100), 100000));
        cardList.add(new Card(getString(R.string.mobile_card_200), 200000));
        cardList.add(new Card(getString(R.string.mobile_card_500), 500000));
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
        btnThanhToanQuaVi = findViewById(R.id.btnThanhToanQuaVi);
        btnThanhToanNganHang = findViewById(R.id.btnThanhToanNganHang);
        tvMenhGia = findViewById(R.id.tvMenhGia);
        tvLoaiThe = findViewById(R.id.tvLoaiThe);
    }


    @SuppressLint("SetTextI18n")
    private void initViewListener() {
        ivBack.setOnClickListener(view -> finish());

        btnThanhToanNganHang.setOnClickListener(v -> {
            String loaiThe = tvLoaiThe.getText().toString();
            String gia = tvMenhGia.getText().toString();
            if(!validateConditions(loaiThe,gia)) {
               return;
            }

            Intent intent = new Intent(getApplicationContext(), PayViaBankActivity.class);
            intent.putExtra(SplashActivity.LOAI_THE, loaiThe);
            intent.putExtra(SplashActivity.MENH_GIA, gia);
            startActivity(intent);
        });

        btnThanhToanQuaVi.setOnClickListener(v -> {
            String loaiThe = tvLoaiThe.getText().toString();
            String gia = tvMenhGia.getText().toString();
            if(!validateConditions(loaiThe,gia)) {
                return;
            }

            Intent intent = new Intent(getApplicationContext(), PayViaWalletActivity.class);
            intent.putExtra(SplashActivity.LOAI_THE, loaiThe);
            intent.putExtra(SplashActivity.MENH_GIA, gia);
            startActivity(intent);
        });
    }

    private boolean validateConditions(String loaithe, String gia) {
        if(loaithe.trim().isEmpty()) {
            Toast.makeText(this, "Vui lòng chọn nhà mạng", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(gia.trim().isEmpty() || gia.trim().equals("0")) {
            Toast.makeText(this, "Vui lòng chọn mệnh giá thẻ", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @Override
    public void execute(int money,String name) {
        tvMenhGia.setText(String.valueOf(money));
    }

    @Override
    public void executeNetwork(String name) {
        tvLoaiThe.setText(name);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        tvMenhGia.setText("");
        tvLoaiThe.setText("");
    }
}