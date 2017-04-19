package com.lekz112.test.service.repository;

import com.google.auto.value.AutoValue;
import com.lekz112.test.service.Table;
import com.squareup.sqldelight.RowMapper;

@AutoValue
/*package*/ abstract class RestaurantTableDao implements RestaurantTablesModel {

    static final Factory<RestaurantTableDao> FACTORY = new Factory<>(AutoValue_RestaurantTableDao::new);

    static final RowMapper<TableWithReservations> SELECT_ALL_MAPPER =
            FACTORY.select_allMapper(AutoValue_RestaurantTableDao_TableWithReservations::new, CustomerDao.FACTORY);

    Table toTable() {
        return Table.create(table_id(), available());
    }

    @AutoValue
    abstract static class TableWithReservations implements Select_allModel<RestaurantTableDao, CustomerDao> {

    }

}
