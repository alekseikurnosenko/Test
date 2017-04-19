package com.lekz112.test.service.repository;

import com.lekz112.test.service.Customer;
import com.lekz112.test.service.Table;

import java.util.List;

import io.reactivex.Observable;

public interface ReservationsRepository {

    List<Customer> getCustomers();

    void setCustomers(List<Customer> customer);

    Observable<List<Table>> getTables();

    void setTables(List<Table> tables);


}
