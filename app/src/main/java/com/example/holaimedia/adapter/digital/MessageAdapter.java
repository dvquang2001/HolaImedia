package com.example.holaimedia.adapter.digital;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.holaimedia.R;
import com.example.holaimedia.model.digital.Message;

import java.util.ArrayList;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {
    private final Context context;
    private final ArrayList<Message> list;
    private final Activity activity;
    private final String currentUserEmail;


    public MessageAdapter(Context context, ArrayList<Message> list, Activity activity, String currentUserEmail) {
        this.context = context;
        this.list = list;
        this.currentUserEmail = currentUserEmail;
        this.activity = activity;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void addMessage(Message msg) {
        list.add(msg);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_message, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Message currentMessage = list.get(position);
        holder.bind(currentMessage);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvUserEmail, tvMessageContent, tvMessageDate;
        private final ConstraintLayout layoutMessage, rootView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvUserEmail = itemView.findViewById(R.id.tvUserEmail);
            tvMessageContent = itemView.findViewById(R.id.tvMessageContent);
            tvMessageDate = itemView.findViewById(R.id.tvMessageDate);
            layoutMessage = itemView.findViewById(R.id.layoutMessage);
            rootView = itemView.findViewById(R.id.rootView);
        }

        @SuppressLint("UseCompatLoadingForDrawables")
        void bind(Message message) {
            tvUserEmail.setText(message.getUserEmail());
            tvMessageContent.setText(message.getMessage());
            tvMessageDate.setText(message.getDateTime());

            if (currentUserEmail.equals(message.getUserEmail())) {
                layoutMessage.setBackground(context.getDrawable(R.drawable.button_selected_background));

                DisplayMetrics displayMetrics = new DisplayMetrics();
                activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                int screenWidth = displayMetrics.widthPixels;

                // Calculate 65% of screen width
                int viewWidth = (int) (screenWidth * 0.65);

                // Set layout params for view
                ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams(viewWidth, ViewGroup.LayoutParams.WRAP_CONTENT);
                layoutParams.topToTop = ConstraintLayout.LayoutParams.PARENT_ID;
                layoutParams.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
                layoutParams.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID;
                layoutMessage.setLayoutParams(layoutParams);
            } else {
                layoutMessage.setBackground(context.getDrawable(R.drawable.message_backgroud));

                DisplayMetrics displayMetrics = new DisplayMetrics();
                activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                int screenWidth = displayMetrics.widthPixels;

                // Calculate 65% of screen width
                int viewWidth = (int) (screenWidth * 0.65);

                // Set layout params for view
                ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams(viewWidth, ViewGroup.LayoutParams.WRAP_CONTENT);
                layoutParams.topToTop = ConstraintLayout.LayoutParams.PARENT_ID;
                layoutParams.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
                layoutParams.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID;
                layoutMessage.setLayoutParams(layoutParams);
            }
        }
    }
}
