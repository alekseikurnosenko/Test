package com.lekz112.test.service;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class Customer {

    public abstract String firstName();

    public abstract String lastName();

    public abstract long id();

    public static Customer create(String firstName, String lastName, long id) {
        return new AutoValue_Customer(firstName, lastName, id);
    }
}
