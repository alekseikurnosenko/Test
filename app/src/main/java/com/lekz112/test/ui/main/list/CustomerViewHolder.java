package com.lekz112.test.ui.main.list;

import com.lekz112.test.R;
import com.lekz112.test.service.Customer;
import com.lekz112.test.ui.OnItemClickListener;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CustomerViewHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.item_customer_name)
    TextView name;
    @Bind(R.id.item_customer_container)
    ViewGroup container;

    public CustomerViewHolder(View itemView, OnItemClickListener clickListener) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        container.setOnClickListener(v -> clickListener.onItemClick(getLayoutPosition()));
    }

    public void show(Customer customer) {
        String fullName = itemView.getResources()
                .getString(R.string.customer_fullname, customer.firstName(), customer.lastName());
        name.setText(fullName);
    }
}