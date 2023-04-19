package com.example.holaimedia.activity.digital;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.holaimedia.R;
import com.example.holaimedia.activity.auth.SplashActivity;

@SuppressLint("UseCompatLoadingForDrawables")
public class NapdtActivity extends AppCompatActivity {
    private Button btnViettel, btnVina, btnMobi, btnCard10,btnCard20, btnCard50, btnCard100,btnCard200, btnCard500;
    private Button btnThanhToanQuaVi, btnThanhToanNganHang;
    private TextView tvMenhGia, tvLoaiThe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_napdt);

        initViews();
        initViewListener();
    }

    private void initViews() {
        btnViettel = findViewById(R.id.btnViettel);
        btnVina = findViewById(R.id.btnVina);
        btnMobi = findViewById(R.id.btnMobi);
        btnCard10 = findViewById(R.id.btnCard10);
        btnCard20 = findViewById(R.id.btnCard20);
        btnCard50 = findViewById(R.id.btnCard50);
        btnCard100 = findViewById(R.id.btnCard100);
        btnCard200 = findViewById(R.id.btnCard200);
        btnCard500 = findViewById(R.id.btnCard500);
        btnThanhToanQuaVi = findViewById(R.id.btnThanhToanQuaVi);
        btnThanhToanNganHang = findViewById(R.id.btnThanhToanNganHang);
        tvMenhGia = findViewById(R.id.tvMenhGia);
        tvLoaiThe = findViewById(R.id.tvLoaiThe);
    }


    @SuppressLint("SetTextI18n")
    private void initViewListener() {
        btnViettel.setOnClickListener(view -> {
            setBackgroundSelected(btnViettel);
            setBackgroundUnselected(btnVina);
            setBackgroundUnselected(btnMobi);
            tvLoaiThe.setText(getResources().getString(R.string.viettel_card));
        });

        btnVina.setOnClickListener(view -> {
            setBackgroundSelected(btnVina);
            setBackgroundUnselected(btnViettel);
            setBackgroundUnselected(btnMobi);
            tvLoaiThe.setText(getResources().getString(R.string.vinaphone_card));
        });

        btnMobi.setOnClickListener(view -> {
            setBackgroundSelected(btnMobi);
            setBackgroundUnselected(btnVina);
            setBackgroundUnselected(btnViettel);
            tvLoaiThe.setText(getResources().getString(R.string.mobiphone_card));
        });

        btnCard10.setOnClickListener(view -> {
            setBackgroundSelected(btnCard10);
            setBackgroundUnselected(btnCard20);
            setBackgroundUnselected(btnCard50);
            setBackgroundUnselected(btnCard100);
            setBackgroundUnselected(btnCard200);
            setBackgroundUnselected(btnCard500);
            tvMenhGia.setText(btnCard10.getText());
        });

        btnCard20.setOnClickListener(view -> {
            setBackgroundSelected(btnCard20);
            setBackgroundUnselected(btnCard10);
            setBackgroundUnselected(btnCard50);
            setBackgroundUnselected(btnCard100);
            setBackgroundUnselected(btnCard200);
            setBackgroundUnselected(btnCard500);
            tvMenhGia.setText(btnCard20.getText());
        });

        btnCard50.setOnClickListener(view -> {
            setBackgroundSelected(btnCard50);
            setBackgroundUnselected(btnCard20);
            setBackgroundUnselected(btnCard10);
            setBackgroundUnselected(btnCard100);
            setBackgroundUnselected(btnCard200);
            setBackgroundUnselected(btnCard500);
            tvMenhGia.setText(btnCard50.getText());
        });

        btnCard100.setOnClickListener(view -> {
            setBackgroundSelected(btnCard100);
            setBackgroundUnselected(btnCard20);
            setBackgroundUnselected(btnCard50);
            setBackgroundUnselected(btnCard10);
            setBackgroundUnselected(btnCard200);
            setBackgroundUnselected(btnCard500);
            tvMenhGia.setText(btnCard100.getText());
        });

        btnCard200.setOnClickListener(view -> {
            setBackgroundSelected(btnCard200);
            setBackgroundUnselected(btnCard20);
            setBackgroundUnselected(btnCard50);
            setBackgroundUnselected(btnCard100);
            setBackgroundUnselected(btnCard10);
            setBackgroundUnselected(btnCard500);
            tvMenhGia.setText(btnCard200.getText());
        });

        btnCard500.setOnClickListener(view -> {
            setBackgroundSelected(btnCard500);
            setBackgroundUnselected(btnCard20);
            setBackgroundUnselected(btnCard50);
            setBackgroundUnselected(btnCard100);
            setBackgroundUnselected(btnCard200);
            setBackgroundUnselected(btnCard10);
            tvMenhGia.setText(btnCard500.getText());
        });

        btnThanhToanNganHang.setOnClickListener(v -> {
            String loaiThe = tvLoaiThe.getText().toString();
            String gia = tvMenhGia.getText().toString();

            Intent intent = new Intent(getApplicationContext(), PayViaBankActivity.class);
            intent.putExtra(SplashActivity.LOAI_THE, loaiThe);
            intent.putExtra(SplashActivity.MENH_GIA, gia);
            startActivity(intent);
        });

        btnThanhToanQuaVi.setOnClickListener(v -> {
            String loaiThe = tvLoaiThe.getText().toString();
            String gia = tvMenhGia.getText().toString();

            Intent intent = new Intent(getApplicationContext(), PayViaWalletActivity.class);
            intent.putExtra(SplashActivity.LOAI_THE, loaiThe);
            intent.putExtra(SplashActivity.MENH_GIA, gia);
            startActivity(intent);
        });
    }



    private void setBackgroundUnselected(Button button) {
        button.setBackground(getResources().getDrawable(R.drawable.button_backgroud));
        button.setTextColor(getResources().getColor(R.color.black));
    }

    private void setBackgroundSelected(Button button) {
        button.setBackground(getResources().getDrawable(R.drawable.button_selected_background));
        button.setTextColor(getResources().getColor(R.color.white));
    }
}