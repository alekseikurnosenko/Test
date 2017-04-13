package com.lekz112.test.service.network;

import com.lekz112.test.di.util.ApplicationScope;
import com.lekz112.test.service.Customer;
import com.lekz112.test.service.Table;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

@ApplicationScope
public class StubNetworkService implements NetworkService {

    @Inject
    public StubNetworkService() {
    }

    @Override
    public Single<List<Customer>> getCustomers() {
        return null;
    }

    @Override
    public Single<List<Table>> getTables() {
        return null;
    }
}
