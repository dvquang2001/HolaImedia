package com.example.holaimedia.adapter.delivery;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.holaimedia.R;
import com.example.holaimedia.model.delivery.OrderManagement;

import java.util.List;

public class OrderShipAdapter extends RecyclerView.Adapter<OrderShipAdapter.UserViewHolder> {
    private final List<OrderManagement> mListUsers;
    private final Context mContext;

    public OrderShipAdapter(Context context, List<OrderManagement> mListUsers) {
        this.mListUsers = mListUsers;
        this.mContext = context;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ordership, parent, false);
        return new UserViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        final OrderManagement user = mListUsers.get(position);
        if (user == null) {
            return;
        }
        holder.tvSDT.setText("Số điện thoại:" + user.getSdt());
        holder.tvHoten.setText("Họ tên:" + user.getHoten());
        holder.tvKhoiluong.setText("Khối lượng:" + user.getKhoiluong() + " kg");
        holder.tvdiachigui.setText("Địa chỉ gửi:" + user.getDiachigui());
        holder.tvdiachinhan.setText("Địa chỉ nhận:" + user.getDiachinhan());
        holder.tvTien.setText(":" + user.getTien() + " VNĐ");
        holder.tvNgay.setText("Ngày:" + user.getNgay());
        holder.tvGio.setText("Thời gian:" + user.getGio());
        holder.layout_item.setOnClickListener(v -> {

        });

    }

    @Override
    public int getItemCount() {
        return mListUsers.size();
    }


    public static class UserViewHolder extends RecyclerView.ViewHolder {
        private final LinearLayout layout_item;
        private final TextView tvHoten, tvGio, tvKhoiluong, tvNgay, tvSDT, tvTien, tvdiachigui, tvdiachinhan;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            tvHoten = itemView.findViewById(R.id.tv_name);
            layout_item = itemView.findViewById(R.id.layout_item1);
            tvGio = itemView.findViewById(R.id.tv_gio);
            tvKhoiluong = itemView.findViewById(R.id.tvkhoiluong);
            tvNgay = itemView.findViewById(R.id.tv_ngay);
            tvSDT = itemView.findViewById(R.id.tvsdt);
            tvTien = itemView.findViewById(R.id.tv_tien);
            tvdiachigui = itemView.findViewById(R.id.tv_diachi1);
            tvdiachinhan = itemView.findViewById(R.id.tv_diachi2);
        }
    }

}


