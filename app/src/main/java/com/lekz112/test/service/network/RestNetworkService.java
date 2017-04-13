package com.lekz112.test.service.network;

import com.annimon.stream.Optional;
import com.lekz112.test.di.util.ApplicationScope;
import com.lekz112.test.service.Customer;
import com.lekz112.test.service.Table;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

@ApplicationScope
public class RestNetworkService implements NetworkService {

    private final APIEndpoint endpoint;

    @Inject
    public RestNetworkService(APIEndpoint endpoint) {
        this.endpoint = endpoint;
    }

    @Override
    public Single<List<Customer>> getCustomers() {
        return endpoint.customers()
                .flattenAsObservable(customersList -> customersList)
                .map(CustomerModel::convert)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
    }

    @Override
    public Single<List<Table>> getTables() {
        return endpoint.tables()
                .flattenAsObservable(tablesList -> tablesList)
                .filter(table -> table != null)
                .map(Table::create)
                .toList();
    }
}