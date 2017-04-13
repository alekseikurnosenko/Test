package com.lekz112.test.service.network;

import com.lekz112.test.service.Customer;
import com.lekz112.test.service.Table;

import java.util.List;

import io.reactivex.Single;

public interface NetworkService {

    Single<List<Customer>> getCustomers();

    Single<List<Table>> getTables();

}
