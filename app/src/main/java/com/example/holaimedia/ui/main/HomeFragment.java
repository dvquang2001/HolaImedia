package com.example.holaimedia.ui.main;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.holaimedia.R;
import com.example.holaimedia.activity.analysis.ChartDeliveryActivity;
import com.example.holaimedia.activity.analysis.ChartFoodActivity;
import com.example.holaimedia.activity.food.CartActivity;
import com.example.holaimedia.activity.food.OrderActivity;
import com.example.holaimedia.activity.food.ProductActivity;
import com.example.holaimedia.activity.digital.NapdtActivity;
import com.example.holaimedia.activity.digital.bill_payment.BillpaymentMainActivity;
import com.example.holaimedia.activity.digital.data_3g.Napdata4GActivity;
import com.example.holaimedia.activity.digital.message.MessageActivity;
import com.example.holaimedia.activity.delivery.ShipActivity;

public class HomeFragment extends Fragment {

    private View root;
    private View layoutDatDonGiaoHang, layoutDanhSachDonHang, layoutDonChoXacNhan, layoutThongKe;
    private View layoutNapDienThoai, layoutNapData, layoutThanhToanHoaDon, layoutNhanTin;
    private View layoutDoAn,layoutGioHang, layoutDonMua, layoutThongKeDoAn;

    public HomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_home, container, false);

        initViews();
        initViewListener();
        return root;
    }

    private void initViews() {
        layoutDatDonGiaoHang = root.findViewById(R.id.layoutDatDonGiaoHang);
        layoutDanhSachDonHang = root.findViewById(R.id.layoutDanhSachDonHang);
        layoutDonChoXacNhan = root.findViewById(R.id.layoutDonChoXacNhan);
        layoutThongKe = root.findViewById(R.id.layoutThongKe);
        layoutNapDienThoai = root.findViewById(R.id.layoutNapDienThoai);
        layoutNapData = root.findViewById(R.id.layoutNapData);
        layoutThanhToanHoaDon = root.findViewById(R.id.layoutThanhToanHoaDon);
        layoutNhanTin = root.findViewById(R.id.layoutNhanTin);
        layoutDoAn = root.findViewById(R.id.layoutDoAn);
        layoutGioHang = root.findViewById(R.id.layoutGioHang);
        layoutDonMua = root.findViewById(R.id.layoutDonMua);
        layoutThongKeDoAn = root.findViewById(R.id.layoutThongKeDoAn);
    }


    private void initViewListener() {
        layoutDatDonGiaoHang.setOnClickListener(view -> startActivity(new Intent(requireContext(), ShipActivity.class)));
        layoutDanhSachDonHang.setOnClickListener(view -> {
            //todo danhsachdonhang
        });
        layoutDonChoXacNhan.setOnClickListener(view -> {
            //todo donchoxacnhan
        });
        layoutThongKe.setOnClickListener(view -> {
            Intent intent = new Intent(requireContext(), ChartDeliveryActivity.class);
            startActivity(intent);
        });
        layoutNapDienThoai.setOnClickListener(view -> {
            Intent intent = new Intent(requireContext(), NapdtActivity.class);
            startActivity(intent);
        });
        layoutNapData.setOnClickListener(view -> {
            Intent intent = new Intent(requireContext(), Napdata4GActivity.class);
            startActivity(intent);
        });
        layoutNhanTin.setOnClickListener(view -> {
            Intent intent = new Intent(requireContext(), MessageActivity.class);
            startActivity(intent);
        });
        layoutThanhToanHoaDon.setOnClickListener(view -> {
            Intent intent = new Intent(requireContext(), BillpaymentMainActivity.class);
            startActivity(intent);
        });
        layoutDoAn.setOnClickListener(view -> {
            Intent intent = new Intent(requireContext(), ProductActivity.class);
            startActivity(intent);
        });
        layoutGioHang.setOnClickListener(view -> {
            Intent intent = new Intent(requireContext(), CartActivity.class);
            startActivity(intent);
        });
        layoutDonMua.setOnClickListener(view -> {
            Intent intent = new Intent(requireContext(), OrderActivity.class);
            startActivity(intent);
        });
        layoutThongKeDoAn.setOnClickListener(view -> {
            Intent intent = new Intent(requireContext(), ChartFoodActivity.class);
            startActivity(intent);
        });

    }
}