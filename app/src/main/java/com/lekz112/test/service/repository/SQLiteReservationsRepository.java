package com.lekz112.test.service.repository;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.lekz112.test.service.Customer;
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
    public List<Customer> getCustomers() {
        List<Customer> results = new ArrayList<>();
        SqlDelightStatement query = CustomerDao.FACTORY.select_all();
        try (Cursor cursor = database.rawQuery(query.statement, query.args)) {
            while (cursor.moveToNext()) {
                results.add(CustomerDao.SELECT_ALL_MAPPER.map(cursor).toCustomer());
            }
        }
        return results;
    }

    @Override
    public void setCustomer(List<Customer> customers) {
        database.beginTransaction();
        try {
            // Clear all tables
            database.execSQL(CustomerDao.DELETE_ALL);
            CustomersModel.Insert_customer insertCustomer = new CustomerDao.Insert_customer(database);
            // Add new ones
            for (Customer customer : customers) {
                insertCustomer.bind(customer.id(), customer.firstName(), customer.lastName());
                insertCustomer.program.executeInsert();
            }
            database.setTransactionSuccessful();
        } finally {
            database.endTransaction();
        }
    }

    @SuppressLint("NewApi")
    @Override
    public List<Table> getTables() {
        List<Table> results = new ArrayList<>();
        SqlDelightStatement query = RestaurantTableDao.FACTORY.select_all();

        try (Cursor cursor = database.rawQuery(query.statement, query.args)) {
            while (cursor.moveToNext()) {
                RestaurantTableDao.TableWithReservations map = RestaurantTableDao.SELECT_ALL_MAPPER.map(cursor);
                RestaurantTableDao tableDao = map.a();

                List<CustomerDao> customerDaos = new ArrayList<>();
                customerDaos.add(map.c());

                while (cursor.moveToNext()) {
                    map = RestaurantTableDao.SELECT_ALL_MAPPER.map(cursor);

                    if (map.a().table_id() == tableDao.table_id()) {
                        customerDaos.add(map.c());
                    } else {
                        // Key changed, rollback
                        cursor.moveToPrevious();
                    }
                }

                List<Customer> customers = Stream.of(customerDaos)
                        .filter(dao -> dao != null)
                        .map(CustomerDao::toCustomer)
                        .collect(Collectors.toList());

                results.add(Table.create(tableDao.table_id(), tableDao.available(), customers));
            }
        }

        return results;
    }

    @Override
    public void setTables(List<Table> tables) {
        database.beginTransaction();
        try {
            // Clear all tables
            database.execSQL(RestaurantTableDao.DELETE_ALL);
            RestaurantTablesModel.Insert_table insertTable = new RestaurantTableDao.Insert_table(database);
            // Add new ones
            for (Table table : tables) {
                insertTable.bind(table.id(), table.available());
                insertTable.program.executeInsert();
            }
            database.setTransactionSuccessful();
        } finally {
            database.endTransaction();
        }
    }
}
