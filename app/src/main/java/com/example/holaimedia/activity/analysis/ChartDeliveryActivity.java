package com.example.holaimedia.activity.analysis;

import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.holaimedia.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class ChartDeliveryActivity extends AppCompatActivity {

    List<BarEntry> barArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);

        BarChart barChart = findViewById(R.id.barChart);
        getData();
        BarDataSet barDataSet = new BarDataSet(barArrayList,"Nothing");
        BarData barData = new BarData(barDataSet);
        barChart.setData(barData);
        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        barDataSet.setValueTextColor(Color.BLACK);

        barDataSet.setValueTextSize(16f);
        barChart.getDescription().setEnabled(true);
    }

    private void getData() {
        barArrayList  = new ArrayList<BarEntry>();
        barArrayList.add(new BarEntry(1f,10));
        barArrayList.add(new BarEntry(2f,20));
        barArrayList.add(new BarEntry(3f,30));
        barArrayList.add(new BarEntry(4f,40));
        barArrayList.add(new BarEntry(5f,50));
        barArrayList.add(new BarEntry(6f,60));
        barArrayList.add(new BarEntry(7f,70));
        barArrayList.add(new BarEntry(8f,80));
        barArrayList.add(new BarEntry(9f,90));
        barArrayList.add(new BarEntry(10f,100));
        barArrayList.add(new BarEntry(11f,110));
        barArrayList.add(new BarEntry(12f,120));
        barArrayList.add(new BarEntry(13f,130));
        barArrayList.add(new BarEntry(14f,140));
        barArrayList.add(new BarEntry(15f,150));
        barArrayList.add(new BarEntry(16f,160));
        barArrayList.add(new BarEntry(17f,170));
        barArrayList.add(new BarEntry(18f,180));
        barArrayList.add(new BarEntry(19f,190));
        barArrayList.add(new BarEntry(20f,200));
        barArrayList.add(new BarEntry(21f,210));
        barArrayList.add(new BarEntry(22f,220));
        barArrayList.add(new BarEntry(23f,230));
        barArrayList.add(new BarEntry(24f,240));
        barArrayList.add(new BarEntry(25f,250));
        barArrayList.add(new BarEntry(26f,260));
        barArrayList.add(new BarEntry(27f,270));
        barArrayList.add(new BarEntry(28f,280));
        barArrayList.add(new BarEntry(29f,290));
        barArrayList.add(new BarEntry(30f,300));
        barArrayList.add(new BarEntry(31f,310));
    }
}