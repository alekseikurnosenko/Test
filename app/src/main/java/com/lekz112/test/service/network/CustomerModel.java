package com.lekz112.test.service.network;

import com.google.gson.annotations.SerializedName;

import com.annimon.stream.Optional;
import com.lekz112.test.service.Customer;

public class CustomerModel {

    @SerializedName("customerFirstName")
    public String firstName;
    @SerializedName("customerLastName")
    public String lastName;
    @SerializedName("id")
    public Long id;

    public Optional<Customer> convert() {
        return Optional.of(Customer.create(firstName, lastName, id));
    }
}
