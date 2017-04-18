package com.lekz112.test.service;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class Table {

    public abstract long id();

    public abstract boolean available();

    public static Table create(long id, boolean available) {
        return new AutoValue_Table(id, available);
    }
}
