package com.lekz112.test.service.network;

import com.annimon.stream.Optional;
import com.lekz112.test.di.util.ApplicationScope;
import com.lekz112.test.service.Customer;
import com.lekz112.test.service.Table;
import com.lekz112.test.service.repository.ReservationsRepository;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

@ApplicationScope
public class RestNetworkService implements NetworkService {

    private final APIEndpoint endpoint;
    private final ReservationsRepository repository;

    @Inject
    public RestNetworkService(APIEndpoint endpoint, ReservationsRepository repository) {
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
                .doOnNext(repository::setCustomers)
                .startWith(repository.getCustomers())
                .filter(list -> list.size() > 0);
    }

    @Override
    public Observable<List<Table>> getTables() {
        // NOTE: normally we would expect every table to have some unique index
        // we don't have any, so use their index as unqiue id

        Observable<List<Table>> networkObservable = endpoint.tables()
                .flattenAsObservable(tablesList -> tablesList)
                .filter(table -> table != null)
                .zipWith(Observable.rangeLong(0, Long.MAX_VALUE), (available, index) -> Table.create(index, available))
                .toList()
                .toObservable()
                .doOnNext(repository::setTables);
                // we merge two observables - network and local, but stop local as soon as network emitted
        //
        Observable<List<Table>> localObservable = repository.getTables()
                .filter(list -> list.size() > 0);

        List<Observable<List<Table>>> observables = Arrays.asList(networkObservable, localObservable);

        return Observable.concatEager(observables);
    }
}
