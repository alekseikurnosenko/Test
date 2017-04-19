package com.lekz112.test.di;

import com.lekz112.test.di.util.ApplicationScope;
import com.lekz112.test.service.network.ReservationService;
import com.lekz112.test.service.network.RestReservationService;

import dagger.Module;
import dagger.Provides;

@Module
public class ServiceModule {

    @ApplicationScope
    @Provides
    public ReservationService networkService(RestReservationService restNetworkService) {
        return restNetworkService;
    }
}
