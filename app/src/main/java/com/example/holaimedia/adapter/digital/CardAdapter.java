package com.example.holaimedia.adapter.digital;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.holaimedia.R;
import com.example.holaimedia.model.digital.Card;

import java.util.List;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {

    private final Context context;
    private final List<Card> cardList;
    private final OnClickCardSelected onClickCardSelected;
    private int selectedCardIndex = -1;

    public CardAdapter(Context context, List<Card> cardList, OnClickCardSelected onClickCardSelected) {
        this.context = context;
        this.cardList = cardList;
        this.onClickCardSelected = onClickCardSelected;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint({"UseCompatLoadingForDrawables", "NotifyDataSetChanged"})
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Card card = cardList.get(position);
        holder.btnCard.setText(card.getName());
        if (position == selectedCardIndex) {
            holder.btnCard.setBackground(context.getResources().getDrawable(R.drawable.mobile_card_selected));
            holder.btnCard.setTextColor(context.getResources().getColor(R.color.white));
        } else {
            holder.btnCard.setBackground(context.getResources().getDrawable(R.drawable.mobile_card_bg));
            holder.btnCard.setTextColor(context.getResources().getColor(R.color.black));
        }
        holder.btnCard.setOnClickListener(view -> {
            selectedCardIndex = position;
            notifyDataSetChanged();
            Log.d("Main",card.getMoney() + "");
            onClickCardSelected.execute(card.getMoney(), card.getName());
        });
    }

    @Override
    public int getItemCount() {
        return cardList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final Button btnCard;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            btnCard = itemView.findViewById(R.id.btnCard);
        }
    }
}

