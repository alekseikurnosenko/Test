package com.lekz112.test.ui.main.tables;

import com.lekz112.test.R;

import android.content.Context;
import android.support.annotation.DimenRes;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;


public class TablesGridLayoutManager extends GridLayoutManager {

    private final int itemSize;
    private final int minItemSpacing;
    private boolean layoutPending = true;

    public TablesGridLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        // For preview purposes
        itemSize = context.getResources().getDimensionPixelSize(R.dimen.item_table_size);
        minItemSpacing = context.getResources().getDimensionPixelSize(R.dimen.activity_vertical_margin);
    }

    public TablesGridLayoutManager(Context context, @DimenRes int itemSize, @DimenRes int minItemSpacing) {
        super(context, 1);
        this.itemSize = context.getResources().getDimensionPixelSize(itemSize);
        this.minItemSpacing = context.getResources().getDimensionPixelSize(minItemSpacing);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        int width = getWidth();
        int height = getHeight();
        if (layoutPending && width > 0 && height > 0) {
            layoutPending = false;

            // Vertical orientation only
            int actualWidth = width - getPaddingRight() - getPaddingLeft();
            int spanCount = 1;
            // Try to fit as much items as possible
            // We assume that each item should have some spacing to the left and to the right it
            while (actualWidth > itemSize * spanCount + minItemSpacing * (spanCount + 1)) {
                spanCount++;
            }

            setSpanCount(spanCount - 1);
        }
        super.onLayoutChildren(recycler, state);
    }
}
