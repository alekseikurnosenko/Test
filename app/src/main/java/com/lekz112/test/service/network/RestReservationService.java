package com.lekz112.test.service.network;

import com.annimon.stream.Optional;
import com.lekz112.test.di.util.ApplicationScope;
import com.lekz112.test.service.Customer;
import com.lekz112.test.service.Table;
import com.lekz112.test.service.repository.ReservationsRepository;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

@ApplicationScope
public class RestReservationService implements ReservationService {

    private final APIEndpoint endpoint;
    private final ReservationsRepository repository;

    @Inject
    public RestReservationService(APIEndpoint endpoint, ReservationsRepository repository) {
        this.endpoint = endpoint;
        this.repository = repository;
    }

    @Override
    public Observable<List<Customer>> getCustomers() {

        return endpoint.customers()
                .flattenAsObservable(customersList -> customersList)
                .map(CustomerModel::convert)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList()
                .toObservable()
                .observeOn(Schedulers.io())
                .doOnNext(repository::setCustomers)
                // we merge two observables - network and local, but stop local as soon as network emitted
                // On error try to return cached data
                .publish(network -> Observable.merge(network,
                        repository.getCustomers().takeUntil(network)))
                .onErrorResumeNext(repository.getCustomers());

    }

    @Override
    public Observable<List<Table>> getTables() {
        // NOTE: normally we would expect every table to have some unique index
        // we don't have any, so use their index as unique id

        Observable<List<Table>> networkObservable = endpoint.tables()
                .flattenAsObservable(tablesList -> tablesList)
                .filter(table -> table != null)
                .zipWith(Observable.rangeLong(0, Long.MAX_VALUE), (available, index) -> Table.create(index, available))
                .toList()
                .toObservable()
                .observeOn(Schedulers.io())
                .doOnNext(repository::setTables);

        Observable<List<Table>> localObservable = repository.getTables()
                .filter(list -> list.size() > 0);

        List<Observable<List<Table>>> observables = Arrays.asList(networkObservable, localObservable);

        return Observable.merge(observables).onErrorResumeNext(localObservable);
    }

    @Override
    public Completable placeReservation(Table table, Customer customer) {
        return repository.placeReservation(table, customer);
    }

    @Override
    public Completable clearReservations() {
        return repository.clearReservations();
    }
}
