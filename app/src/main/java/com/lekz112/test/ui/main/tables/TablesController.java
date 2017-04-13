package com.lekz112.test.ui.main.tables;

import com.bluelinelabs.conductor.Controller;
import com.lekz112.test.R;
import com.lekz112.test.service.Table;
import com.lekz112.test.ui.view.ViewFlipper;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


public class TablesController extends Controller {

    private static final int PROGRESS = 0;
    private static final int CONTENT = 1;
    private static final int ERROR = 2;

    @Bind(R.id.tables_flipper)
    ViewFlipper flipper;
    @Bind(R.id.tables_content)
    RecyclerView recyclerView;

    @NonNull
    @Override
    protected View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
        View view = inflater.inflate(R.layout.view_tables, container, false);
        ButterKnife.bind(this, view);

        recyclerView.setLayoutManager(new TablesGridLayoutManager(container.getContext(), R.dimen.item_table_size,
                R.dimen.activity_vertical_margin));

        TablesAdapter adapter = new TablesAdapter();
        List<Table> list = new ArrayList<>();
        for (int i = 0; i < 40; i++) {
            list.add(null);
        }
        adapter.setTables(list);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new TableItemDecoration(view.getContext(), R.dimen.item_table_size));

        flipper.setDisplayedChild(CONTENT);

        return view;
    }

    @Override
    protected void onDestroyView(@NonNull View view) {
        ButterKnife.unbind(this);
        super.onDestroyView(view);
    }
}
