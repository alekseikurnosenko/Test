package com.lekz112.test.service.repository;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.lekz112.test.service.Table;
import com.squareup.sqldelight.SqlDelightStatement;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class SQLiteReservationsRepository implements ReservationsRepository {

    private final SQLiteDatabase database;

    @Inject
    public SQLiteReservationsRepository(ReservationsSQLiteOpenHelper helper) {
        database = helper.getWritableDatabase();
    }

    @SuppressLint("NewApi")
    @Override
    public List<Table> getTables() {
        List<Table> results = new ArrayList<>();
        SqlDelightStatement query = RestaurantTableDao.FACTORY.select_all();
        try (Cursor cursor = database.rawQuery(query.statement, query.args)) {
            while (cursor.moveToNext()) {
                results.add(RestaurantTableDao.SELECT_ALL_MAPPER.map(cursor).toTable());
            }
        }

        return results;
    }

    @Override
    public void setTables(List<Table> tables) {

    }
}
