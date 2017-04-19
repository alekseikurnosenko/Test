package com.lekz112.test.ui.main.tables;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.lekz112.test.R;
import com.lekz112.test.service.Table;
import com.lekz112.test.ui.OnItemClickListener;

import butterknife.Bind;
import butterknife.ButterKnife;


public class TableViewHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.table_id)
    TextView tableIdView;
    @Bind(R.id.table_container)
    View containerView;

    public TableViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void show(Table table, OnItemClickListener clickListener) {
        tableIdView.setText(String.valueOf(table.id()));
        // We assume that if table is reserved, it's availability can't change
        // We assume that we can make reservation only for available,not-reserved table
        itemView.setOnClickListener(null);
        if (table.reservations().size() > 0) {
            containerView.setBackgroundResource(R.color.yellow);
        } else if (table.available()) {
            itemView.setOnClickListener(view -> clickListener.onItemClick(getLayoutPosition()));
            containerView.setBackgroundResource(R.color.green);
        } else {
            containerView.setBackgroundResource(R.color.red);
        }

    }
}
