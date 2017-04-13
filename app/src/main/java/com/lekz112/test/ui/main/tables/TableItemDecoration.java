package com.lekz112.test.ui.main.tables;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.DimenRes;
import android.support.v7.widget.RecyclerView;
import android.view.View;

// https://gist.github.com/liangzhitao/e57df3c3232ee446d464
public class TableItemDecoration extends RecyclerView.ItemDecoration {

    private final int itemSize;

    public TableItemDecoration(Context context, @DimenRes int itemSize) {
        this.itemSize = context.getResources().getDimensionPixelSize(itemSize);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view);
        TablesGridLayoutManager layoutManager = (TablesGridLayoutManager) parent.getLayoutManager();
        int spanCount = layoutManager.getSpanCount();
        int totalWidth = layoutManager.getWidth() - layoutManager.getPaddingLeft() - layoutManager.getPaddingRight();
        int spacing = (totalWidth - spanCount * itemSize) / (spanCount + 1);

        if (position >= 0) {
            int column = position % spanCount;

            outRect.left = spacing - column * spacing / spanCount;
            outRect.right = (column + 1) * spacing / spanCount;
            if (position < spanCount) {
                outRect.top = spacing;
            }
            outRect.bottom = spacing;
        }
    }
}
