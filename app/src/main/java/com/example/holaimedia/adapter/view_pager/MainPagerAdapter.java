package com.example.holaimedia.adapter.view_pager;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.holaimedia.ui.main.HomeFragment;
import com.example.holaimedia.ui.main.UserInfoFragment;

public class MainPagerAdapter extends FragmentStateAdapter {

    public MainPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
         if(position == 0) {
             return new HomeFragment();
         } else if(position == 1) {
             return new UserInfoFragment();
         }
         return new HomeFragment();
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
