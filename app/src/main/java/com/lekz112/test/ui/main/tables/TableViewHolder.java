package com.lekz112.test.ui.main.tables;

import com.lekz112.test.R;
import com.lekz112.test.service.Table;
import com.lekz112.test.ui.OnItemClickListener;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;


public class TableViewHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.table_id)
    TextView tableIdView;
    @Bind(R.id.table_container)
    View containerView;

    public TableViewHolder(View itemView, OnItemClickListener clickListener) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        itemView.setOnClickListener(view -> clickListener.onItemClick(getLayoutPosition()));
    }

    public void show(Table table) {
        tableIdView.setText(String.valueOf(table.id()));
        containerView.setBackgroundResource(table.available() ? R.color.green : R.color.red);
    }
}
