package com.example.holaimedia.utils;

import android.content.Context;
import android.util.AttributeSet;

import androidx.recyclerview.widget.RecyclerView;

public class CenteredRecyclerView extends RecyclerView {
    public CenteredRecyclerView(Context context) {
        super(context);
    }

    public CenteredRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CenteredRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {
        super.onMeasure(widthSpec, heightSpec);

        int height = getMeasuredHeight();
        int center = getHeight() / 2;
        int totalHeight = 0;

        // Calculate the total height of all child views
        for (int i = 0; i < getChildCount(); i++) {
            totalHeight += getChildAt(i).getHeight();
        }

        // Calculate the padding needed to center the child views vertically
        int padding = (center - totalHeight / 2) - getPaddingTop();
        if (padding > 0) {
            setPadding(0, padding, 0, 0);
            height -= padding * 2;
            setMeasuredDimension(getMeasuredWidth(), height);
        }
    }
}
