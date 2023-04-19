package com.example.holaimedia.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.holaimedia.R;
import com.example.holaimedia.adapter.view_pager.MainPagerAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager2 viewPager = findViewById(R.id.viewPagerMain);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        MainPagerAdapter adapter = new MainPagerAdapter(this);

        viewPager.setAdapter(adapter);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.menuHome:
                    viewPager.setCurrentItem(0);
                    break;
                case R.id.menuUserInfo:
                    viewPager.setCurrentItem(1);
                    break;
            }
            return true;
        });

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                switch (position) {
                    case 0:
                        bottomNavigationView.getMenu().findItem(R.id.menuHome).setChecked(true);
                        break;
                    case 1:
                        bottomNavigationView.getMenu().findItem(R.id.menuUserInfo).setChecked(true);
                        break;

                }
            }
        });
    }

}