package com.lekz112.test.service.network;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import io.reactivex.Single;

public class StubAPIEndpoint implements APIEndpoint {
    @Override
    public Single<List<CustomerModel>> customers() {
        CustomerModel model = new CustomerModel();
        model.firstName = model.lastName = "fake";
        model.id = 1L;
        return Single.just(Collections.singletonList(model));
    }

    @Override
    public Single<List<Boolean>> tables() {
        return Single.just(Arrays.asList(true, false, true, false, false, true));
    }
}
