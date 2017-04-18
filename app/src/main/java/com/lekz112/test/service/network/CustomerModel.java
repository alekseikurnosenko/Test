package com.lekz112.test.service.network;

import android.text.TextUtils;

import com.annimon.stream.Optional;
import com.google.gson.annotations.SerializedName;
import com.lekz112.test.service.Customer;

/*package*/ class CustomerModel {

    @SerializedName("customerFirstName")
    String firstName;
    @SerializedName("customerLastName")
    String lastName;
    @SerializedName("id")
    public Long id;

    Optional<Customer> convert() {
        if (TextUtils.isEmpty(firstName) && TextUtils.isEmpty(lastName)) {
            // We assume there are no such people
            return Optional.empty();
        }
        // We allow first name or last name to be null
        // We use empty string in that case, as we really don't want to deal with nulls
        return Optional.of(Customer.create(
                firstName != null ? firstName : "",
                lastName != null ? lastName : "",
                id));
    }
}
