package com.lekz112.test.ui.main.tables;

import com.bluelinelabs.conductor.Controller;
import com.lekz112.test.R;
import com.lekz112.test.di.util.ControllerInjection;
import com.lekz112.test.service.network.NetworkService;
import com.lekz112.test.ui.OnItemClickListener;
import com.lekz112.test.ui.view.ViewFlipper;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.disposables.Disposables;


public class TablesController extends Controller implements OnItemClickListener {

    private static final int PROGRESS = 0;
    private static final int CONTENT = 1;
    private static final int ERROR = 2;

    @Bind(R.id.tables_flipper)
    ViewFlipper flipper;
    @Bind(R.id.tables_content)
    RecyclerView recyclerView;
    @Inject
    NetworkService networkService;

    private Disposable tablesSubscription = Disposables.disposed();
    private TablesAdapter tablesAdapter;

    @NonNull
    @Override
    protected View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
        ControllerInjection.inject(this);
        View view = inflater.inflate(R.layout.view_tables, container, false);
        ButterKnife.bind(this, view);

        recyclerView.setLayoutManager(new TablesGridLayoutManager(container.getContext(), R.dimen.item_table_size,
                R.dimen.activity_vertical_margin));
        recyclerView.addItemDecoration(new TableItemDecoration(view.getContext(), R.dimen.item_table_size));

        tablesAdapter = new TablesAdapter();
        tablesAdapter.setOnItemClickListener(this);
        recyclerView.setAdapter(tablesAdapter);

        loadTables();

        return view;
    }

    private void loadTables() {
        tablesSubscription.dispose();
        tablesSubscription = networkService.getTables()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(tables -> {
                    flipper.setDisplayedChild(CONTENT);
                    tablesAdapter.setTables(tables);
                }, error -> flipper.setDisplayedChild(ERROR));
    }

    @Override
    protected void onDestroyView(@NonNull View view) {
        ButterKnife.unbind(this);
        tablesSubscription.dispose();
        super.onDestroyView(view);
    }

    @Override
    public void onItemClick(int position) {
        Toast.makeText(getActivity(), "Position " + position, Toast.LENGTH_SHORT).show();
    }
}
