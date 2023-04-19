package com.example.holaimedia.adapter.digital;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.holaimedia.R;
import com.example.holaimedia.activity.digital.bill_payment.DetailpaymentActivity;
import com.example.holaimedia.model.digital.Watercustomer;

import java.util.ArrayList;
import java.util.List;

public class UserWaterAdapter extends RecyclerView.Adapter<UserWaterAdapter.UserViewHolder> implements Filterable {
    private List<Watercustomer> mListUsers;
    private List<Watercustomer> mListUsersOld;
    private Context mContext;

    public UserWaterAdapter(Context context, List<Watercustomer> mListUsers) {
        this.mListUsers = mListUsers;
        this.mListUsersOld = mListUsers;
        this.mContext = context;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user_water, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        final Watercustomer user = mListUsers.get(position);
        if (user == null) {
            return;
        }
        //   else{
        // holder.tvMa.setText("Mã:"+user.getMa());
        holder.tvMa.setText("Mã khách hàng:" + user.getMa());
        holder.tvName.setText("Họ tên:" + user.getTen());
        holder.tvSDt.setText("Số điện thoại:" + user.getSodienthoai());
        holder.tvDiachi.setText("Địa chỉ:" + user.getDiachi());
        holder.tvkyhan.setText("Kỳ hạn" + user.getKyhan());
        holder.tvTien.setText("Tiền thanh toán" + user.getTien());

        holder.layout_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickGotoDetail(user);
            }
        });
        // }
    }

    @Override
    public int getItemCount() {
        // if(mListUsers !=null){
        return mListUsers.size();
        // }
        //  return 0;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String etsearch = constraint.toString();
                if (etsearch.isEmpty()) {
                    mListUsers = mListUsersOld;
                } else {
                    List<Watercustomer> list = new ArrayList<>();
                    for (Watercustomer user : mListUsersOld) {
                        if (user.getMa().toLowerCase().contains(etsearch.toLowerCase())) {
                            list.add(user);
                        }
                    }
                    mListUsers = list;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = mListUsers;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                mListUsers = (List<Watercustomer>) results.values;
                notifyDataSetChanged();
            }
        };
    }


    public class UserViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout layout_item;
        private TextView tvDiachi, tvName, tvMa, tvTien, tvSDt, tvkyhan;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDiachi = itemView.findViewById(R.id.tv_diachi);
            layout_item = itemView.findViewById(R.id.layout_item);
            tvMa = itemView.findViewById(R.id.tvma);
            tvName = itemView.findViewById(R.id.tv_name);
            tvTien = itemView.findViewById(R.id.tv_tien);
            tvSDt = itemView.findViewById(R.id.tv_sdt);
            tvkyhan = itemView.findViewById(R.id.tv_kyhan);
        }
    }

    private void onClickGotoDetail(Watercustomer user) {
        Intent intent = new Intent(mContext, DetailpaymentActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("object_user", user);
        intent.putExtras(bundle);
        mContext.startActivity(intent);

    }
}


