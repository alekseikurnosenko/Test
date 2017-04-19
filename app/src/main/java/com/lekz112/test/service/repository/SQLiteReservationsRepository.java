package com.lekz112.test.service.repository;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.lekz112.test.di.util.ApplicationScope;
import com.lekz112.test.service.Customer;
import com.lekz112.test.service.Table;
import com.squareup.sqldelight.SqlDelightStatement;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.BehaviorSubject;

@ApplicationScope
public class SQLiteReservationsRepository implements ReservationsRepository {

    private final SQLiteDatabase database;
    private final BehaviorSubject<Object> tablesSubject = BehaviorSubject.create();

    @Inject
    public SQLiteReservationsRepository(ReservationsSQLiteOpenHelper helper) {
        database = helper.getWritableDatabase();
    }

    @SuppressLint("NewApi")
    @Override
    public Observable<List<Customer>> getCustomers() {
        return Observable.fromCallable(() -> {
            List<Customer> results = new ArrayList<>();
            SqlDelightStatement query = CustomerDao.FACTORY.select_all();
            try (Cursor cursor = database.rawQuery(query.statement, query.args)) {
                while (cursor.moveToNext()) {
                    results.add(CustomerDao.SELECT_ALL_MAPPER.map(cursor).toCustomer());
                }
            }
            return results;
        }).subscribeOn(Schedulers.io());
    }

    @Override
    public void setCustomers(List<Customer> customers) {
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


    @Override
    public Observable<List<Table>> getTables() {
        // NOTE: we we want to subscribe on io scheduler, so when clients subscribe, map runs on io scheduler
        // We observe on io scheduler, so that when we invoke onNext it also run on io scheduler
        return tablesSubject
                .startWith(new Object())
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .map(ignored -> getTablesList());
    }

    @SuppressLint("NewApi")
    private List<Table> getTablesList() {
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
                        break;
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

    @Override
    public Completable placeReservation(Table table, Customer customer) {
        return Completable.fromAction(() -> {
            database.beginTransaction();
            try {
                ReservationsModel.Add_reservation addReservation = new ReservationsModel.Add_reservation(database);
                addReservation.bind(table.id(), customer.id());
                addReservation.program.executeInsert();
                database.setTransactionSuccessful();
                tablesSubject.onNext(new Object());
            } finally {
                database.endTransaction();
            }
        });
    }

    @Override
    public Completable clearReservations() {
        return Completable.fromAction(() -> {
            database.beginTransaction();
            try {
                database.execSQL(ReservationsModel.DELETE_ALL);
                database.setTransactionSuccessful();
                tablesSubject.onNext(new Object());
            } finally {
                database.endTransaction();
            }
        });
    }
}
