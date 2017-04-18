package com.lekz112.test.ui.main.list;

import com.lekz112.test.R;
import com.lekz112.test.service.Customer;
import com.lekz112.test.ui.OnItemClickListener;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import javax.inject.Inject;

public class CustomersAdapter extends RecyclerView.Adapter<CustomerViewHolder> {

    private List<Customer> customersList;
    private OnItemClickListener clickListener = (position) -> {
    };

    @Inject
    public CustomersAdapter() {

    }

    public void setCustomersList(List<Customer> customersList) {
        this.customersList = customersList;
        notifyDataSetChanged();
    }

    @Override
    public CustomerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_customer, parent, false);
        return new CustomerViewHolder(view, clickListener);
    }

    @Override
    public void onBindViewHolder(CustomerViewHolder holder, int position) {
        Customer customer = getCustomer(position);
        holder.show(customer);
    }

    @Override
    public int getItemCount() {
        return customersList.size();
    }

    public Customer getCustomer(int position) {
        return customersList.get(position);
    }

    public void setOnItemClickListener(OnItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

}
