package com.lekz112.test.service.repository;

import com.google.auto.value.AutoValue;
import com.lekz112.test.service.Customer;
import com.squareup.sqldelight.RowMapper;

@AutoValue
public abstract class CustomerDao implements CustomersModel {

    public static final Factory<CustomerDao> FACTORY = new Factory<>(null);

    public static final RowMapper<CustomerDao> SELECT_ALL_MAPPER = FACTORY.select_allMapper();

    public Customer toCustomer() {
        return Customer.create(first_name(), last_name(), customer_id());
    }
}
