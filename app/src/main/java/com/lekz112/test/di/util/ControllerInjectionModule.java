package com.lekz112.test.di.util;


import com.bluelinelabs.conductor.Controller;

import java.util.Map;

import dagger.Module;
import dagger.android.AndroidInjector;
import dagger.multibindings.Multibinds;

@Module
public abstract class ControllerInjectionModule {

    @Multibinds
    abstract Map<Class<? extends Controller>, AndroidInjector.Factory<? extends Controller>>
    controllerInjectorFactories();

}
