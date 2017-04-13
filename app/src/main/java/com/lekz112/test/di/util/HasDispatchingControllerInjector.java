package com.lekz112.test.di.util;

import com.bluelinelabs.conductor.Controller;

import dagger.android.DispatchingAndroidInjector;

public interface HasDispatchingControllerInjector {

    DispatchingAndroidInjector<Controller> controllerInjector();
}
