package com.example.holaimedia.activity.food;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.viewpager2.widget.ViewPager2;

import com.example.holaimedia.R;
import com.example.holaimedia.adapter.view_pager.ProductPagerAdapter;
import com.example.holaimedia.base.BaseFoodActivity;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class ProductActivity extends BaseFoodActivity {

    private ImageView ivBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        ViewPager2 viewPager = findViewById(R.id.viewPagerProduct);
        TabLayout tabLayout = findViewById(R.id.tabLayout);
        ProductPagerAdapter adapter = new ProductPagerAdapter(this);
        viewPager.setAdapter(adapter);
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText(getString(R.string.fruit));
                    break;
                case 1:
                    tab.setText(getString(R.string.vegetable));
                    break;
                case 2:
                    tab.setText(getString(R.string.fast_food));
                    break;
                case 3:
                    tab.setText(getString(R.string.milk));
                    break;
            }

        }).attach();

        initViews();
        initViewListener();
    }

    private void initViews() {
        ivBack = findViewById(R.id.ivBack);
    }

    private void initViewListener() {
        ivBack.setOnClickListener(view -> finish());
    }

}