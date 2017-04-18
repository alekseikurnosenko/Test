package com.lekz112.test.service.network;

import com.lekz112.test.service.Customer;
import com.lekz112.test.service.Table;

import java.util.List;

import io.reactivex.Observable;

public interface NetworkService {

    Observable<List<Customer>> getCustomers();

    Observable<List<Table>> getTables();

}
