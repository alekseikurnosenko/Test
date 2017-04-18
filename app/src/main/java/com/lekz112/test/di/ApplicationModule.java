package com.lekz112.test.di;

import com.lekz112.test.di.util.ApplicationScope;

import android.app.Application;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {

    private final Application application;

    public ApplicationModule(Application application) {
        this.application = application;
    }

    @ApplicationScope
    @Provides
    public Application application() {
        return application;
    }
}
