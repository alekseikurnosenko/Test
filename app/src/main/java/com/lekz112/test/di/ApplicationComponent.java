package com.lekz112.test.di;

import com.lekz112.test.di.main.list.ListControllerModule;
import com.lekz112.test.di.util.ApplicationScope;
import com.lekz112.test.di.util.ControllerInjectionModule;
import com.lekz112.test.service.network.NetworkService;
import com.lekz112.test.ui.TestApplication;

import dagger.Component;
import dagger.android.AndroidInjectionModule;

@ApplicationScope
@Component(modules = {RetrofitModule.class, MainActivityModule.class, ListControllerModule.class,
        AndroidInjectionModule.class, ControllerInjectionModule.class,
        EndPointModule.class, ServiceModule.class})
public interface ApplicationComponent {

    NetworkService networkModule();

    void inject(TestApplication testApplication);
}
