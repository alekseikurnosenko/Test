package com.lekz112.test.di;

import android.app.Application;

import com.lekz112.test.di.util.ApplicationScope;
import com.lekz112.test.service.repository.ReservationsRepository;
import com.lekz112.test.service.repository.ReservationsSQLiteOpenHelper;
import com.lekz112.test.service.repository.SQLiteReservationsRepository;

import dagger.Module;
import dagger.Provides;

@Module
public class RepositoryModule {

    @ApplicationScope
    @Provides
    public ReservationsRepository reservationsRepository(SQLiteReservationsRepository sqLiteReservationsRepository) {
        return sqLiteReservationsRepository;
    }

    @ApplicationScope
    @Provides
    public ReservationsSQLiteOpenHelper reservationsSQLiteOpenHelper(Application application) {
        return new ReservationsSQLiteOpenHelper(application);
    }
}
