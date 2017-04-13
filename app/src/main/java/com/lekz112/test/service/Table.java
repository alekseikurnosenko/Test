package com.lekz112.test.service;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class Table {

    public abstract boolean available();

    public static Table create(boolean available) {
        return null;
    }
}
