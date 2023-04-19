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
        int orderDay = Integer.parseInt(order.getCurrentDate().substring(0,2));
        int orderMonth = Integer.parseInt(order.getCurrentDate().substring(4,5));
        int orderYear = Integer.parseInt(order.getCurrentDate().substring(6,10));

        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        int currentDay = Integer.parseInt(currentDate.substring(0,2));
        int currentMonth = Integer.parseInt(currentDate.substring(4,5));
        int currentYear = Integer.parseInt(currentDate.substring(6,10));

        if(currentDay > orderYear) {
            return true;
        } else if(currentYear == orderYear) {
            if(currentMonth > orderMonth) {
                return true;
            } else if(currentMonth == orderMonth) {
                if(Math.abs(currentDay - orderDay) >= 2) {
                    return true;
                }
            }
        }

        return false;
    }
}
