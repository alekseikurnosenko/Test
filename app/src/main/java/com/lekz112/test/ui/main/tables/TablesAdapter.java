package com.lekz112.test.ui.main.tables;

import com.lekz112.test.R;
import com.lekz112.test.service.Table;
import com.lekz112.test.ui.OnItemClickListener;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;


public class TablesAdapter extends RecyclerView.Adapter<TableViewHolder> {

    private List<Table> tableList;
    private OnItemClickListener clickListener = (position) -> {
    };

    public void setTables(List<Table> tableList) {
        this.tableList = tableList;
        notifyDataSetChanged();
    }

    @Override
    public TableViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_table, parent, false);
        return new TableViewHolder(view, clickListener);
    }

    @Override
    public void onBindViewHolder(TableViewHolder holder, int position) {
        Table table = getTable(position);
        holder.show(table);
    }

    @Override
    public int getItemCount() {
        return tableList.size();
    }

    public Table getTable(int position) {
        return tableList.get(position);
    }

    public void setOnItemClickListener(OnItemClickListener clickListener) {
        this.clickListener = clickListener;
    }
}
