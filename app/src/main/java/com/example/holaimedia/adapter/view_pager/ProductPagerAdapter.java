package com.example.holaimedia.adapter.view_pager;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.holaimedia.ui.food.FastFoodFragment;
import com.example.holaimedia.ui.food.FruitFragment;
import com.example.holaimedia.ui.main.HomeFragment;
import com.example.holaimedia.ui.food.MilkFragment;
import com.example.holaimedia.ui.food.VegetableFragment;

public class ProductPagerAdapter extends FragmentStateAdapter {

    public ProductPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if(position == 0) {
            return new FruitFragment();
        } else if(position == 1) {
            return new VegetableFragment();
        }
        else if(position == 2) {
            return new FastFoodFragment();
        }
        else if(position == 3) {
            return new MilkFragment();
        }
        return new HomeFragment();
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
