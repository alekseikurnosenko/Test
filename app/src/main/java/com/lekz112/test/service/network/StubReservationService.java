package com.lekz112.test.service.network;

import com.lekz112.test.di.util.ApplicationScope;
import com.lekz112.test.service.Customer;
import com.lekz112.test.service.Table;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Observable;

@ApplicationScope
public class StubReservationService implements ReservationService {

    @Inject
    public StubReservationService() {
    }

    @Override
    public Observable<List<Customer>> getCustomers() {
        return Observable.just(Arrays.asList(
                Customer.create("fake1", "fake1", 1),
                Customer.create("fake2", "fake2", 2)));
    }

    @Override
    public Observable<List<Table>> getTables() {
        return Observable.just(Arrays.asList(
                Table.create(1, true),
                Table.create(2, false)));
    }

    @Override
    public Completable placeReservation(Table table, Customer customer) {
        return Completable.complete();
    }

    @Override
    public Completable clearReservations() {
        return null;
    }
}
