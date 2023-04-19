package com.example.holaimedia.adapter.view_pager;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.holaimedia.ui.order.OrderFragment;
import com.example.holaimedia.ui.order.OrderSuccessFragment;

public class OrderPagerAdapter extends FragmentStateAdapter {

    public OrderPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if(position == 0) {
            return new OrderFragment();
        }
        else if(position == 1) {
            return new OrderSuccessFragment();
        }
        return new OrderFragment();
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
