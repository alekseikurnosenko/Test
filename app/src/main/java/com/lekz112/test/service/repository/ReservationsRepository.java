package com.lekz112.test.service.repository;

import com.lekz112.test.service.Table;

import java.util.List;

public interface ReservationsRepository {

    List<Table> getTables();

    void setTables(List<Table> tables);


}
