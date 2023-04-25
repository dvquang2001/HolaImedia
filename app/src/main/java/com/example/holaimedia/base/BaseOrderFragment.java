package com.example.holaimedia.base;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.holaimedia.model.food.Order;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public abstract class BaseOrderFragment extends Fragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected boolean isSuccess(Order order) {
        int orderHour = Integer.parseInt(order.getCurrentTime().substring(0,2));

        String currentTime = new SimpleDateFormat("HH:mm:ss a", Locale.getDefault()).format(new Date());
        int currentHour = Integer.parseInt(currentTime.substring(0,2));

        if(orderHour == 23 && (currentHour == 0 || currentHour == 24)) {
            return true;
        } else if(orderHour == 23 && currentHour == 1) {
            return true;
        } else if (currentHour - orderHour >= 1) {
            return true;
        }

        return false;
    }
}
