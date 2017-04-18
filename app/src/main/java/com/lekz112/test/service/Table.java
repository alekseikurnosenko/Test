package com.lekz112.test.service;

import com.google.auto.value.AutoValue;

import java.util.Collections;
import java.util.List;

@AutoValue
public abstract class Table {

    public abstract long id();

    public abstract boolean available();

    public abstract List<Customer> reservations();

    public static Table create(long id, boolean available) {
        return Table.create(id, available, Collections.emptyList());
    }

    public static Table create(long id, boolean available, List<Customer> reservations) {
        return new AutoValue_Table(id, available, reservations);
    }
}
