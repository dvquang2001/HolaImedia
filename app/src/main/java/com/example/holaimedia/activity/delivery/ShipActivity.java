package com.example.holaimedia.activity.delivery;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.holaimedia.R;


public class ShipActivity extends AppCompatActivity {
    private final String[] quan = {"Quận Hoàng Mai", "Quận Cầu Giấy", "Quận Hai Bà Trưng", "Quận Tây Hồ", "Quận Hoàn Kiếm", "Quận Đống Đa", "Quận Thanh Xuân", "Quận Hoài Đức"};
    private final String[] phuong_hm = {"Phường Vĩnh Hưng", "Phường Thanh Trì", "Phường Tân Mai", "Phường Hoàng Văn Thụ", "Phường Giáp Bát", "Phường Định Công", "Phường Mai Động", "Phường Tương Mai", "Phường Đại Kim", "Phường Lĩnh Nam"};

    private final String[] phuong_dongda = {"Phường Cát Linh", "Phường Văn Miếu", "Phường Quốc Tử Giám", "Phường Láng Thượng", "Phường Ô Chợ Dừa", "Phường Láng Hạ", "Phường Khâm Thiên", "Phường Trung Liệt"};
    private final String[] phuong_caugiay = {"Phường Nghĩa Đô", "Phường Nghĩa Tân", "Phường Mai Dịch", "Phường Dịch Vọng Hậu", "Phường Quan Hoa", "Phường Yên Hòa"};
    private final String[] phuong_tayho = {"Phường Bưởi", "Phường Nhật Tân", "Phường Phú Thượng", "Phường Quảng An", "Phường Tứ Liên"};
    private final String[] phuong_hoankiem = {"Phường Phúc Tân", "Phường Đồng Xuân", "Phường Hàng Mã", "Phường Hàng Đào", "Phường Hàng Bồ"};
    private final String[] phuong_haibatrung = {"Phường Nguyễn Du", "Phường Bạch Đằng", "Phường Ngô Thì Nhậm", "Phố Huế", "Phường Thanh Nhàn"};
    private final String[] phuong_thanhxuan = {"Phường Thanh Xuân Bắc", "Phường Thanh Xuân Trung", "Phường Thanh Xuân Nam", "Phường Khương Trung", "Phường Khương Đình"};
    private ArrayAdapter<String> quanAdapter;
    private ArrayAdapter<String> hoangMaiAdapter;
    private ArrayAdapter<String> dongDaAdapter;
    private ArrayAdapter<String> cauGiayAdapter;
    private ArrayAdapter<String> tayHoAdapter;
    private ArrayAdapter<String> hoanKiemAdapter;
    private ArrayAdapter<String> haiBaTrungAdapter;
    private ArrayAdapter<String> thanhXuanAdapter;

    private ListView lvQuanGui, lvPhuongGui, lvQuanNhan, lvPhuongNhan;

    private EditText etDiaChiNguoiGui, etDiaChiNguoiNhan;
    private Button btnNavigate;
    TextView tvDiaChiGui, tvPhuongGui, tvQuanGui, tvDiaChiNhan, tvPhuongNhan, tvQuanNhan;
    private String quanGui, quanNhan, phuongGui, phuongNhan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ship);

        initViews();
        initViewListener();
    }

    @SuppressLint("SetTextI18n")
    private void initViews() {
        lvQuanGui = findViewById(R.id.lvQuanGui);
        lvPhuongGui = findViewById(R.id.lvPhuongGui);
        lvQuanNhan = findViewById(R.id.lvQuanNhan);
        lvPhuongNhan = findViewById(R.id.lvPhuongNhan);
        etDiaChiNguoiGui = findViewById(R.id.etDiaChiNguoiGui);
        etDiaChiNguoiNhan = findViewById(R.id.etDiaChiNguoiNhan);
        btnNavigate = findViewById(R.id.btnNavigate);
        tvDiaChiGui = findViewById(R.id.tvDiaChiGui);
        tvPhuongGui = findViewById(R.id.tvPhuongGui);
        tvQuanGui = findViewById(R.id.tvQuanGui);
        tvDiaChiNhan = findViewById(R.id.tvDiaChiNhan);
        tvPhuongNhan = findViewById(R.id.tvPhuongNhan);
        tvQuanNhan = findViewById(R.id.tvQuanNhan);

        quanAdapter = new ArrayAdapter<>(ShipActivity.this, android.R.layout.simple_list_item_1, quan);
        lvQuanGui.setAdapter(quanAdapter);
        lvQuanNhan.setAdapter(quanAdapter);

        lvQuanGui.setOnItemClickListener((adapterView, view, quanPosition, l) -> {
            quanGui = quan[quanPosition];
            tvQuanGui.setText(quanGui);
            switch (quanGui.trim()) {
                case "Quận Cầu Giấy":
                    cauGiayAdapter = new ArrayAdapter<>(ShipActivity.this, android.R.layout.simple_list_item_1, phuong_caugiay);
                    lvPhuongGui.setAdapter(cauGiayAdapter);
                    lvPhuongGui.setOnItemClickListener((adapterView1, view12, phuongPosition, l1) -> {
                        phuongGui = phuong_caugiay[phuongPosition];
                        tvPhuongGui.setText(phuongGui + " - ");
                    });
                    break;
                case "Quận Hoàng Mai":
                    hoangMaiAdapter = new ArrayAdapter<>(ShipActivity.this, android.R.layout.simple_list_item_1, phuong_hm);
                    lvPhuongGui.setAdapter(hoangMaiAdapter);
                    lvPhuongGui.setOnItemClickListener((adapterView1, view12, phuongPosition, l1) -> {
                        phuongGui = phuong_hm[phuongPosition];
                        tvPhuongGui.setText(phuongGui + " - ");
                    });
                    break;
                case "Quận Hai Bà Trưng":
                    haiBaTrungAdapter = new ArrayAdapter<>(ShipActivity.this, android.R.layout.simple_list_item_1, phuong_haibatrung);
                    lvPhuongGui.setAdapter(haiBaTrungAdapter);
                    lvPhuongGui.setOnItemClickListener((adapterView1, view12, phuongPosition, l1) -> {
                        phuongGui = phuong_haibatrung[phuongPosition];
                        tvPhuongGui.setText(phuongGui + " - ");
                    });
                    break;
                case "Quận Đống Đa":
                    dongDaAdapter = new ArrayAdapter<>(ShipActivity.this, android.R.layout.simple_list_item_1, phuong_dongda);
                    lvPhuongGui.setAdapter(dongDaAdapter);
                    lvPhuongGui.setOnItemClickListener((adapterView1, view12, phuongPosition, l1) -> {
                        phuongGui = phuong_dongda[phuongPosition];
                        tvPhuongGui.setText(phuongGui + " - ");
                    });
                    break;
                case "Quận Hoàn Kiếm":
                    hoanKiemAdapter = new ArrayAdapter<>(ShipActivity.this, android.R.layout.simple_list_item_1, phuong_hoankiem);
                    lvPhuongGui.setAdapter(hoanKiemAdapter);
                    lvPhuongGui.setOnItemClickListener((adapterView1, view12, phuongPosition, l1) -> {
                        phuongGui = phuong_hoankiem[phuongPosition];
                        tvPhuongGui.setText(phuongGui + " - ");
                    });
                    break;
                case "Quận Thanh Xuân":
                    thanhXuanAdapter = new ArrayAdapter<>(ShipActivity.this, android.R.layout.simple_list_item_1, phuong_thanhxuan);
                    lvPhuongGui.setAdapter(thanhXuanAdapter);
                    lvPhuongGui.setOnItemClickListener((adapterView1, view12, phuongPosition, l1) -> {
                        phuongGui = phuong_thanhxuan[phuongPosition];
                        tvPhuongGui.setText(phuongGui + " - ");
                    });
                    break;
                case "Quận Tây Hồ":
                    tayHoAdapter = new ArrayAdapter<>(ShipActivity.this, android.R.layout.simple_list_item_1, phuong_tayho);
                    lvPhuongGui.setAdapter(tayHoAdapter);
                    lvPhuongGui.setOnItemClickListener((adapterView1, view12, phuongPosition, l1) -> {
                        phuongGui = phuong_tayho[phuongPosition];
                        tvPhuongGui.setText(phuongGui + " - ");
                    });
                    break;
            }
        });


        lvQuanNhan.setOnItemClickListener((adapterView, view, quanPosition, l) -> {
            quanNhan = quan[quanPosition];
            tvQuanNhan.setText(quanNhan);
            switch (quanNhan.trim()) {
                case "Quận Cầu Giấy":
                    cauGiayAdapter = new ArrayAdapter<>(ShipActivity.this, android.R.layout.simple_list_item_1, phuong_caugiay);
                    lvPhuongNhan.setAdapter(cauGiayAdapter);
                    lvPhuongNhan.setOnItemClickListener((adapterView1, view12, phuongPosition, l1) -> {
                        phuongNhan = phuong_caugiay[phuongPosition];
                        tvPhuongNhan.setText(phuongNhan + " - ");
                    });
                    break;
                case "Quận Hoàng Mai":
                    hoangMaiAdapter = new ArrayAdapter<>(ShipActivity.this, android.R.layout.simple_list_item_1, phuong_hm);
                    lvPhuongNhan.setAdapter(hoangMaiAdapter);
                    lvPhuongNhan.setOnItemClickListener((adapterView1, view12, phuongPosition, l1) -> {
                        phuongNhan = phuong_hm[phuongPosition];
                        tvPhuongNhan.setText(phuongNhan + " - ");
                    });
                    break;
                case "Quận Hai Bà Trưng":
                    haiBaTrungAdapter = new ArrayAdapter<>(ShipActivity.this, android.R.layout.simple_list_item_1, phuong_haibatrung);
                    lvPhuongNhan.setAdapter(haiBaTrungAdapter);
                    lvPhuongNhan.setOnItemClickListener((adapterView1, view12, phuongPosition, l1) -> {
                        phuongNhan = phuong_haibatrung[phuongPosition];
                        tvPhuongNhan.setText(phuongNhan + " - ");
                    });
                    break;
                case "Quận Đống Đa":
                    dongDaAdapter = new ArrayAdapter<>(ShipActivity.this, android.R.layout.simple_list_item_1, phuong_dongda);
                    lvPhuongNhan.setAdapter(dongDaAdapter);
                    lvPhuongNhan.setOnItemClickListener((adapterView1, view12, phuongPosition, l1) -> {
                        phuongNhan = phuong_dongda[phuongPosition];
                        tvPhuongNhan.setText(phuongNhan + " - ");
                    });
                    break;
                case "Quận Hoàn Kiếm":
                    hoanKiemAdapter = new ArrayAdapter<>(ShipActivity.this, android.R.layout.simple_list_item_1, phuong_hoankiem);
                    lvPhuongNhan.setAdapter(hoanKiemAdapter);
                    lvPhuongNhan.setOnItemClickListener((adapterView1, view12, phuongPosition, l1) -> {
                        phuongNhan = phuong_hoankiem[phuongPosition];
                        tvPhuongNhan.setText(phuongNhan + " - ");
                    });
                    break;
                case "Quận Thanh Xuân":
                    thanhXuanAdapter = new ArrayAdapter<>(ShipActivity.this, android.R.layout.simple_list_item_1, phuong_thanhxuan);
                    lvPhuongNhan.setAdapter(thanhXuanAdapter);
                    lvPhuongNhan.setOnItemClickListener((adapterView1, view12, phuongPosition, l1) -> {
                        phuongNhan = phuong_thanhxuan[phuongPosition];
                        tvPhuongNhan.setText(phuongNhan + " - ");
                    });
                    break;
                case "Quận Tây Hồ":
                    tayHoAdapter = new ArrayAdapter<>(ShipActivity.this, android.R.layout.simple_list_item_1, phuong_tayho);
                    lvPhuongNhan.setAdapter(tayHoAdapter);
                    lvPhuongNhan.setOnItemClickListener((adapterView1, view12, phuongPosition, l1) -> {
                        phuongNhan = phuong_tayho[phuongPosition];
                        tvPhuongNhan.setText(phuongNhan + " - ");
                    });
                    break;
            }
        });

    }

    private boolean isLocationValid() {
        if (tvDiaChiGui.getText().toString().trim().isEmpty() ||
                tvPhuongGui.getText().toString().trim().isEmpty() ||
                tvQuanGui.getText().toString().trim().isEmpty() ||
                tvDiaChiNhan.getText().toString().trim().isEmpty() ||
                tvPhuongNhan.getText().toString().trim().isEmpty() ||
                tvQuanNhan.getText().toString().trim().isEmpty()) {
            return false;
        }
        return true;
    }

    private void initViewListener() {
        btnNavigate.setOnClickListener(v -> {
            if (!isLocationValid()) {
                Toast.makeText(this, "Bạn chưa điền đầy đủ thông tin", Toast.LENGTH_LONG).show();
                return;
            }
            String etDiaChiGui = etDiaChiNguoiGui.getText().toString();
            String etDiaChiNhan = etDiaChiNguoiNhan.getText().toString();

            String diaChiNguoiGui = etDiaChiGui + " - " + phuongGui + " - " + quanGui;
            String diaChiNguoiNhan = etDiaChiNhan + " - " + phuongNhan + " - " + quanNhan;

            Intent i = new Intent(getApplicationContext(), Page2Activity.class);
            i.putExtra("quan_gui", quanGui);
            i.putExtra("quan_nhan", quanNhan);
            i.putExtra("dia_chi_gui", diaChiNguoiGui);
            i.putExtra("dia_chi_nhan", diaChiNguoiNhan);
            startActivity(i);
        });

        etDiaChiNguoiGui.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                tvDiaChiGui.setText(charSequence.toString() + " - ");
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        etDiaChiNguoiNhan.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                tvDiaChiNhan.setText(charSequence.toString() + " - ");
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
}