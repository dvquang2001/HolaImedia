package com.example.holaimedia.adapter.digital;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.holaimedia.R;
import com.example.holaimedia.activity.analysis.ChartDeliveryActivity;
import com.example.holaimedia.model.digital.Transaction;

import java.util.List;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.ViewHolder> {

    private List<Transaction> transactions;
    private boolean isAdmin;

    public TransactionAdapter(List<Transaction> transactions, boolean isAdmin) {
        this.transactions = transactions;
        this.isAdmin = isAdmin;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_transaction, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Transaction currentTransaction = transactions.get(position);
        holder.bind(currentTransaction, isAdmin);
    }

    @Override
    public int getItemCount() {
        return transactions.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvEmail, tvMoney, tvDateTransaction;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvEmail = itemView.findViewById(R.id.tvEmail);
            tvMoney = itemView.findViewById(R.id.tvMoney);
            tvDateTransaction = itemView.findViewById(R.id.tvDateTransaction);
        }

        @SuppressLint("SetTextI18n")
        void bind(Transaction transaction, boolean isAdmin) {
           tvEmail.setText("Tài khoản: " + transaction.getEmail());
           tvEmail.setVisibility(isAdmin ? View.VISIBLE : View.GONE);
           tvMoney.setText("Số tiền giao dịch: " + transaction.getMoney() + " VND");
           tvDateTransaction.setText("Thời gian giao dịch: " + transaction.getTransactionDate());

           itemView.setOnClickListener(view -> {
               itemView.getContext().startActivity(new Intent(itemView.getContext(), ChartDeliveryActivity.class));
           });
        }

    }
}
