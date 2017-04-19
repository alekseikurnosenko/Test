package com.lekz112.test.di;

import com.lekz112.test.di.util.ApplicationScope;

import org.threeten.bp.Duration;
import org.threeten.bp.temporal.ChronoUnit;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {

    public static final String CLEAR_RESERVATION = "clearReservation";

    private final Application application;

    public ApplicationModule(Application application) {
        this.application = application;
    }

    @ApplicationScope
    @Provides
    public Application application() {
        return application;
    }

    @ApplicationScope
    @Named(CLEAR_RESERVATION)
    @Provides
    public Duration clearReservationsTimeout() {
        return Duration.of(10, ChronoUnit.MINUTES);
    }

    @ApplicationScope
    @Provides
    public SharedPreferences sharedPreferences() {
        return PreferenceManager.getDefaultSharedPreferences(application);
    }
}
