package com.lekz112.test.service.network;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;

public interface APIEndpoint {

    @GET("customer-list.json")
    Single<List<CustomerModel>> customers();

    @GET("table-map.json ")
    Single<List<Boolean>> tables();


}
