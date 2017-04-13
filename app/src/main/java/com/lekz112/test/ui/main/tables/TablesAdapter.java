package com.lekz112.test.ui.main.tables;

import com.lekz112.test.R;
import com.lekz112.test.service.Table;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;


public class TablesAdapter extends RecyclerView.Adapter<TableViewHolder> {

    private List<Table> tableList;

    public void setTables(List<Table> tableList) {
        this.tableList = tableList;
        notifyDataSetChanged();
    }

    @Override
    public TableViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_table, parent, false);
        return new TableViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TableViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return tableList.size();
    }
}
