package com.lekz112.test.di;

import com.lekz112.test.ui.main.MainActivity;

import android.app.Activity;

import dagger.Binds;
import dagger.Module;
import dagger.android.ActivityKey;
import dagger.android.AndroidInjector;
import dagger.multibindings.IntoMap;

@Module(subcomponents = {MainActivitySubcomponent.class})
public abstract class MainActivityModule {

    @Binds
    @IntoMap
    @ActivityKey(MainActivity.class)
    abstract AndroidInjector.Factory<? extends Activity> bindYourActivityInjectorFactory(
            MainActivitySubcomponent.Builder builder);

}
