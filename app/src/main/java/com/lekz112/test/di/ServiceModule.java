package com.lekz112.test.di;

import com.lekz112.test.di.util.ApplicationScope;
import com.lekz112.test.service.network.NetworkService;
import com.lekz112.test.service.network.RestNetworkService;

import dagger.Module;
import dagger.Provides;

@Module
public class ServiceModule {

    @ApplicationScope
    @Provides
    public NetworkService networkService(RestNetworkService restNetworkService) {
        return restNetworkService;
    }
}
