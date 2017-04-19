package com.lekz112.test.di;

import com.lekz112.test.di.main.list.ListControllerModule;
import com.lekz112.test.di.main.tables.TablesControllerModule;
import com.lekz112.test.di.receiver.BootCompleteModule;
import com.lekz112.test.di.receiver.ClearReservationsModule;
import com.lekz112.test.di.util.ApplicationScope;
import com.lekz112.test.di.util.ControllerInjectionModule;
import com.lekz112.test.service.network.ReservationService;
import com.lekz112.test.ui.TestApplication;

import dagger.Component;
import dagger.android.AndroidInjectionModule;

@ApplicationScope
@Component(modules = {RetrofitModule.class, MainActivityModule.class, ListControllerModule.class,
        TablesControllerModule.class, AndroidInjectionModule.class, ControllerInjectionModule.class,
        EndPointModule.class, ServiceModule.class, ApplicationModule.class, RepositoryModule.class,
        BootCompleteModule.class, ClearReservationsModule.class})
public interface ApplicationComponent {

    ReservationService reservationService();

    void inject(TestApplication testApplication);
}
