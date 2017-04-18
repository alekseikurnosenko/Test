package com.lekz112.test.di;

import com.lekz112.test.di.util.ApplicationScope;
import com.lekz112.test.service.repository.ReservationsRepository;
import com.lekz112.test.service.repository.ReservationsSQLiteOpenHelper;
import com.lekz112.test.service.repository.SQLiteReservationsRepository;

import android.app.Application;

import dagger.Module;
import dagger.Provides;

@Module
public class RepositoryModule {

    @ApplicationScope
    @Provides
    ReservationsRepository reservationsRepository(SQLiteReservationsRepository sqLiteReservationsRepository) {
        return sqLiteReservationsRepository;
    }

    @ApplicationScope
    @Provides
    ReservationsSQLiteOpenHelper reservationsSQLiteOpenHelper(Application application) {
        return new ReservationsSQLiteOpenHelper(application);
    }
}
