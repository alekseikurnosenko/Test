package com.lekz112.test.service.repository;

import com.google.auto.value.AutoValue;
import com.lekz112.test.service.Table;
import com.squareup.sqldelight.RowMapper;

@AutoValue
public abstract class RestaurantTableDao implements RestaurantTableModel {

    public static final Factory<RestaurantTableDao> FACTORY = new Factory<>(null);

    public static final RowMapper<RestaurantTableDao> SELECT_ALL_MAPPER = FACTORY.select_allMapper();

    public Table toTable() {
        return Table.create(table_id(), available());
    }

}
