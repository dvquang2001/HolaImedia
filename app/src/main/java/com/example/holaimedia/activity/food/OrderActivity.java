package com.example.holaimedia.activity.food;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.holaimedia.R;
import com.example.holaimedia.adapter.view_pager.OrderPagerAdapter;
import com.example.holaimedia.base.BaseFoodActivity;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class OrderActivity extends BaseFoodActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        ViewPager2 viewPager = findViewById(R.id.viewPagerOrder);
        TabLayout tabLayout = findViewById(R.id.tabLayoutOrder);
        OrderPagerAdapter adapter = new OrderPagerAdapter(this);
        viewPager.setAdapter(adapter);
        new TabLayoutMediator(tabLayout,viewPager,(tab, position)-> {
            switch (position) {
                case 0:
                    tab.setText(getString(R.string.shipping_goods));
                    break;
                case 1:
                    tab.setText(getString(R.string.purchased_goods));
                    break;
            }
        }).attach();

        ImageView ivBack = findViewById(R.id.ivBack);
        ivBack.setOnClickListener(view -> finish());

    }
}