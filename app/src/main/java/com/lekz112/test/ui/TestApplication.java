package com.lekz112.test.ui;

import com.jakewharton.threetenabp.AndroidThreeTen;
import com.lekz112.test.di.ApplicationComponent;
import com.lekz112.test.di.ApplicationModule;
import com.lekz112.test.di.DaggerApplicationComponent;
import com.lekz112.test.di.ServiceModule;
import com.lekz112.test.service.network.NetworkService;
import com.lekz112.test.service.network.RestNetworkService;
import com.lekz112.test.service.network.StubNetworkService;

import android.app.Activity;
import android.app.Application;

import javax.inject.Inject;

import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasDispatchingActivityInjector;

public class TestApplication extends Application implements HasDispatchingActivityInjector {

    @Inject
    DispatchingAndroidInjector<Activity> dispatchingActivityInjector;

    @Override
    public void onCreate() {
        super.onCreate();
        AndroidThreeTen.init(this);
        buildComponent().inject(this);
        //buildStubComponent().inject(this);
    }

    @Override
    public DispatchingAndroidInjector<Activity> activityInjector() {
        return dispatchingActivityInjector;
    }

    // TODO: Override during instrumentation testing
    protected ApplicationComponent buildComponent() {
        return DaggerApplicationComponent.builder().applicationModule(new ApplicationModule(this)).build();
    }

    private ApplicationComponent buildStubComponent() {
        return DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .serviceModule(new ServiceModule() {
                    @Override
                    public NetworkService networkService(RestNetworkService restNetworkService) {
                        return new StubNetworkService();
                    }
                })
                .build();
    }
}
